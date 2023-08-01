package net.acb.fmmod.event;


import net.acb.fmmod.FMMod;
import net.acb.fmmod.client.ModOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = FMMod.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = FMMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {


        @SubscribeEvent
        public static void registerGuidOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelowAll("tar_overlay", ModOverlay.TAR_OVERLAY);
        }
    }
}