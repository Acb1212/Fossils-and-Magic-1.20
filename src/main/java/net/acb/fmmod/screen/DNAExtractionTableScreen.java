package net.acb.fmmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.acb.fmmod.FMMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DNAExtractionTableScreen extends AbstractContainerScreen<DNAExtractionTableMenu> {

   private static final ResourceLocation TEXTURE =
           new ResourceLocation(FMMod.MODID, "textures/gui/dna_extractor_gui.png");



    public DNAExtractionTableScreen(DNAExtractionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height-imageHeight)/2;
        pGuiGraphics.blit(TEXTURE,x,y,0,0,imageWidth,imageHeight);
        renderProgressArrow(pGuiGraphics,x,y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.is_crafting()) {
            guiGraphics.blit(TEXTURE, x + 79, y + 34, 176, 0, menu.getScaledProgress(), 16 );
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics,pMouseX,pMouseY);
    }
}
