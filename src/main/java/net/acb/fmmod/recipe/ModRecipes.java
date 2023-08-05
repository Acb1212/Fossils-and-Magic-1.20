package net.acb.fmmod.recipe;

import net.acb.fmmod.FMMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FMMod.MODID);

    public static final RegistryObject<RecipeSerializer<FossilExtractionRecipe>> FOSSIL_EXTRACTION_SERIALIZER =
            SERIALIZERS.register("fossil_extraction", () -> FossilExtractionRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<DNAExtractionRecipe>> DNA_EXTRACTION_SERIALIZER =
            SERIALIZERS.register("dna_extraction", () -> DNAExtractionRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
