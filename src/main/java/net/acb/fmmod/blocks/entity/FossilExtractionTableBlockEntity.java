package net.acb.fmmod.blocks.entity;


import net.acb.fmmod.recipe.FossilExtractionRecipe;
import net.acb.fmmod.screen.FossilExtractionTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class FossilExtractionTableBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == Items.BRUSH;
                case 1 -> true;
                case 2, 3, 4, 5, 6, 7, 8, 9, 10 -> false;
                default -> super.isItemValid(slot,stack);

            };
        }
    };


    private static final int BRUSH_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT1 = 2;
    private static final int OUTPUT_SLOT2 = 3;
    private static final int OUTPUT_SLOT3 = 4;
    private static final int OUTPUT_SLOT4 = 5;
    private static final int OUTPUT_SLOT5 = 6;
    private static final int OUTPUT_SLOT6 = 7;
    private static final int OUTPUT_SLOT7 = 8;
    private static final int OUTPUT_SLOT8 = 9;
    private static final int OUTPUT_SLOT9 = 10;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 50;


    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    public FossilExtractionTableBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOSSIL_EXTRACTION_TABLE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex)
                        {
                            case 0 -> FossilExtractionTableBlockEntity.this.progress;
                            case 1 -> FossilExtractionTableBlockEntity.this.maxProgress;
                            default -> 0;
                        };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FossilExtractionTableBlockEntity.this.progress = pValue;
                    case 1 -> FossilExtractionTableBlockEntity.this.maxProgress = pValue;

                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i=0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fossil Extraction Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FossilExtractionTableMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler= LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

//Controls Crafting Station

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {



                if (hasRecipe() && isBrushSlotFull() && isOutputSlotEmptyorReceivable(getOutputSlot())) {

                    setChanged(pLevel, pPos, pState);

                    if (hasProgressFinished()) {


                        craftItem(getOutputSlot());
                        resetProgress();


                    }

                    increaseCraftingProcess();

                }else {


                    resetProgress();


                }


    }



//Craft Functions



    private void craftItem(int outputSlot) {

        Optional<FossilExtractionRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        this.itemHandler.extractItem(INPUT_SLOT,1,false);

        this.itemHandler.setStackInSlot(outputSlot, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(outputSlot).getCount() + resultItem.getCount()));
        damageBrush();

    }


    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private void resetProgress() {
        this.progress = 0;
    }

//Recipe Functions

    private boolean hasRecipe() {
        Optional<FossilExtractionRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        for (int i=2; i<=10; i++) {
            if (canInsertAmountIntoOutputSlot(i, resultItem.getCount()) && canInsertItemIntoOutputSlot(i, resultItem.getItem()))
            {
                return canInsertAmountIntoOutputSlot(i, resultItem.getCount()) && canInsertItemIntoOutputSlot(i, resultItem.getItem());
            }
        }
        return false;
    }



    private Optional<FossilExtractionRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FossilExtractionRecipe.Type.INSTANCE, inventory, level);
    }

    private void damageBrush() {
        ItemStack brushItem = this.itemHandler.getStackInSlot(BRUSH_SLOT);

        Random rand = new Random();
        if (rand.nextFloat(1) <= 0.25) {
            brushItem.setDamageValue(brushItem.getDamageValue()+1);
        }

        if ((brushItem.getMaxDamage() <= brushItem.getDamageValue())) {
            this.itemHandler.extractItem(BRUSH_SLOT,1,false);
        }
    }

//Slot Test Functions

    private int getOutputSlot(){
        ItemStack resultItem = getCurrentRecipe().get().getResultItem(getLevel().registryAccess());

        for (int filledSlot = 2; filledSlot <=10; filledSlot++ ) {
            if ((this.itemHandler.getStackInSlot(filledSlot).is(resultItem.getItem()) ) && (this.itemHandler.getStackInSlot(filledSlot).getCount() < this.itemHandler.getStackInSlot(filledSlot).getMaxStackSize())) {
                return filledSlot;
            }
        }

        for (int emptySlot = 2; emptySlot <= 10; emptySlot++) {
            if (this.itemHandler.getStackInSlot(emptySlot).isEmpty()) {
                return emptySlot;
            }
        }

        return 2;
    }

    private boolean canInsertItemIntoOutputSlot(int slotId, Item item) {
        return this.itemHandler.getStackInSlot(slotId).is(item) || this.itemHandler.getStackInSlot(slotId).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(int slotId, int count) {
        return this.itemHandler.getStackInSlot(slotId).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(slotId).getCount() + count;
    }


    private boolean isOutputSlotEmptyorReceivable(int slotId) {
        return this.itemHandler.getStackInSlot(slotId).getCount() < this.itemHandler.getStackInSlot(slotId).getMaxStackSize()
                || this.itemHandler.getStackInSlot(slotId).isEmpty();
    }

    private boolean isBrushSlotFull() {
        return this.itemHandler.getStackInSlot(BRUSH_SLOT).is(Items.BRUSH);
    }

}
