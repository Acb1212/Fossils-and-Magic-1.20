package net.acb.fmmod.client;

import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.acb.fmmod.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FMMod.MODID);


    public static final RegistryObject<CreativeModeTab> FOSSILS_TAB = CREATIVE_MODE_TABS.register("fossils_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AMBER_FOSSIL.get()))
                    .title(Component.translatable("creativetab.fossils_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        //Blocks
                        pOutput.accept(ModBlocks.AMBER_BLOCK.get());
                        pOutput.accept(ModBlocks.AMBER_ORE.get());
                        pOutput.accept(ModBlocks.CULTURE_VAT.get());
                        pOutput.accept(ModBlocks.FOSSIL_EXTRACTION_TABLE.get());
                        pOutput.accept(ModBlocks.DNA_EXTRACTION_TABLE.get());

                        //Items
                        pOutput.accept(ModItems.AMBER_FOSSIL.get());
                        pOutput.accept(ModItems.RAW_GRAVEL_FOSSIL.get());
                        pOutput.accept(ModItems.GRAVEL_FOSSIL.get());
                        pOutput.accept(ModItems.DNA_DEBUG.get());
                        pOutput.accept(ModItems.TAR_BUCKET.get());
                        pOutput.accept(ModItems.NUTRIENT_SOUP.get());

                    }).build());






    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
