package net.acb.fmmod.blocks.entity;

import net.acb.fmmod.recipe.DNAExtractionRecipe;
import net.acb.fmmod.screen.DNAExtractionTableMenu;
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


public class DNAExtractionTableBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {setChanged();}

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1 -> true;
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 70;
    private int fuelAmount = 0;


    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    public DNAExtractionTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_EXTRACTION_TABLE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex)
                        {
                            case 0 -> DNAExtractionTableBlockEntity.this.progress;
                            case 1 -> DNAExtractionTableBlockEntity.this.maxProgress;
                            default -> 0;
                        };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DNAExtractionTableBlockEntity.this.progress = pValue;
                    case 1 -> DNAExtractionTableBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {return 2;}
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i=0; i<itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {return Component.literal("DNA Extraction Table");}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DNAExtractionTableMenu(pContainerId, pPlayerInventory, this, this.data);
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
        lazyItemHandler=LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }





    //Controls Crafting Station

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {



        if (hasRecipe() && isOutputSlotEmptyorReceivable(OUTPUT_SLOT)) {

            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {


                craftItem(OUTPUT_SLOT);
                resetProgress();


            }

            increaseCraftingProcess();

        }else {


            resetProgress();


        }


    }


    //Craft Functions



    private void craftItem(int outputSlot) {

        Optional<DNAExtractionRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        this.itemHandler.extractItem(INPUT_SLOT,1,false);

        this.itemHandler.setStackInSlot(outputSlot, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(outputSlot).getCount() + resultItem.getCount()));

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
        Optional<DNAExtractionRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

            if (canInsertAmountIntoOutputSlot(OUTPUT_SLOT, resultItem.getCount()) && canInsertItemIntoOutputSlot(OUTPUT_SLOT, resultItem.getItem()))
            {
                return canInsertAmountIntoOutputSlot(OUTPUT_SLOT, resultItem.getCount()) && canInsertItemIntoOutputSlot(OUTPUT_SLOT, resultItem.getItem());
            }

        return false;
    }



    private Optional<DNAExtractionRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(DNAExtractionRecipe.Type.INSTANCE, inventory, level);
    }





    //Slot Test Functions


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


}
