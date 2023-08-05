package net.acb.fmmod.compat;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.acb.fmmod.FMMod;
import net.acb.fmmod.recipe.DNAExtractionRecipe;
import net.acb.fmmod.recipe.FossilExtractionRecipe;
import net.acb.fmmod.screen.FossilExtractionTableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIFMModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(FMMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FossilExtractionRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new DNAExtractionRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<FossilExtractionRecipe> fossil_extractionRecipes = recipeManager.getAllRecipesFor(FossilExtractionRecipe.Type.INSTANCE);

        registration.addRecipes(FossilExtractionRecipeCategory.FOSSIL_EXTRACTION_TYPE, fossil_extractionRecipes);



        List<DNAExtractionRecipe> dna_extractionRecipes = recipeManager.getAllRecipesFor(DNAExtractionRecipe.Type.INSTANCE);

        registration.addRecipes(DNAExtractionRecipeCategory.DNA_EXTRACTION_TYPE, dna_extractionRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FossilExtractionTableScreen.class,4,2,120,16,FossilExtractionRecipeCategory.FOSSIL_EXTRACTION_TYPE);
        registration.addRecipeClickArea(FossilExtractionTableScreen.class,4,2,120,16,DNAExtractionRecipeCategory.DNA_EXTRACTION_TYPE);
    }
}
