package net.acb.fmmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.jna.platform.win32.WinDef;
import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.extensions.IForgeBlockState;


public class ModOverlay {
    //Resources
    private static final ResourceLocation TAR =
            new ResourceLocation(FMMod.MODID, "textures/block/tar_block.png");


    //Helper Functions
    public static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, pGuiGraphics.guiWidth(), pGuiGraphics.guiHeight(), pGuiGraphics.guiWidth(), pGuiGraphics.guiHeight());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1F);
    }

    static void renderTarOverlay(GuiGraphics pGuiGraphics) {
            renderTextureOverlay(pGuiGraphics, TAR, 0.25f);
    }

    public static final IGuiOverlay TAR_OVERLAY = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        BlockPos posHead = gui.getMinecraft().player.getOnPos().above(2);
        Block blockHead = gui.getMinecraft().player.level().getBlockState(posHead).getBlock();

        if (blockHead == ModBlocks.TAR_BLOCK.get()) {
            renderTarOverlay(guiGraphics);
        }

    }));

}
