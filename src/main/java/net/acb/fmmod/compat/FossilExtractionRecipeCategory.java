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
import net.acb.fmmod.recipe.FossilExtractionRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FossilExtractionRecipeCategory implements IRecipeCategory<FossilExtractionRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(FMMod.MODID, "fossil_extraction");
    public static final ResourceLocation TEXTURE = new ResourceLocation(FMMod.MODID, "textures/gui/fossil_extraction_table_gui.png");


    public static final RecipeType<FossilExtractionRecipe> FOSSIL_EXTRACTION_TYPE =
            new RecipeType<>(UID, FossilExtractionRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FossilExtractionRecipeCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(TEXTURE, 30, 14, 131, 57);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FOSSIL_EXTRACTION_TABLE.get()));
    }


    @Override
    public RecipeType<FossilExtractionRecipe> getRecipeType() {
        return FOSSIL_EXTRACTION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Fossil Extraction Table");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FossilExtractionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,26, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT,76, 2).addItemStack(recipe.getResultItem(null));
    }


}
