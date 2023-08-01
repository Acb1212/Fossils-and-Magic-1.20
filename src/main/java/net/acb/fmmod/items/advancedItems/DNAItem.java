package net.acb.fmmod.items.advancedItems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DNAItem extends Item {
    public DNAItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    private void changeNbtDNA(ItemStack pStack, String dino, int dinoPer) {
        CompoundTag nbtData = new CompoundTag();
        nbtData.putString("dinoname", dino);
        nbtData.putInt("dinoper", dinoPer);
        pStack.setTag(nbtData);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
        changeNbtDNA(pStack, "fmmod:entities/compy", 100);
    }


}
