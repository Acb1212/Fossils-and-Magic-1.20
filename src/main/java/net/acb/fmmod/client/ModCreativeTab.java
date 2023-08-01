package net.acb.fmmod.client;

import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.acb.fmmod.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

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

                        //Items
                        pOutput.accept(ModItems.AMBER_FOSSIL.get());
                        pOutput.accept(ModItems.DNA_DEBUG.get());
                        pOutput.accept(ModItems.TAR_BUCKET.get());

                    }).build());



    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
