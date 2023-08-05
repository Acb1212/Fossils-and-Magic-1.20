package net.acb.fmmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.acb.fmmod.recipe.DNAExtractionRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DNAExtractionRecipeCategory implements IRecipeCategory<DNAExtractionRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(FMMod.MODID, "dna_extraction");
    public static final ResourceLocation TEXTURE = new ResourceLocation(FMMod.MODID, "textures/gui/dna_extractor_gui.png");


    public static final RecipeType<DNAExtractionRecipe> DNA_EXTRACTION_TYPE =
            new RecipeType<>(UID, DNAExtractionRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DNAExtractionRecipeCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(TEXTURE, 30, 14, 131, 57);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_EXTRACTION_TABLE.get()));
    }


    @Override
    public RecipeType<DNAExtractionRecipe> getRecipeType() {
        return DNA_EXTRACTION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("DNA Extraction Table");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DNAExtractionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,26, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT,76, 2).addItemStack(recipe.getResultItem(null));
    }


}
