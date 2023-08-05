package net.acb.fmmod.items;

import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.acb.fmmod.items.advancedItems.BucketFoodItem;
import net.acb.fmmod.items.advancedItems.DNAItem;
import net.acb.fmmod.items.advancedItems.ModFoodProperties;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;



public class ModItems {

 //Create a Registry for Modded Items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FMMod.MODID);
 //Modded Items here
 public static final RegistryObject<Item> AMBER_FOSSIL = ITEMS.register("amber_fossil", () -> new Item(new Item.Properties()));
 public static final RegistryObject<Item> DNA_DEBUG = ITEMS.register("dna_debug", () -> new DNAItem(new Item.Properties()));
 public static final RegistryObject<Item> TAR_BUCKET = ITEMS.register("tar_bucket", () -> new SolidBucketItem(ModBlocks.TAR_BLOCK.get(), SoundEvents.MUD_PLACE, (new Item.Properties().stacksTo(1))));
 public static final RegistryObject<Item> RAW_GRAVEL_FOSSIL = ITEMS.register("raw_gravel_fossil", () -> new Item(new Item.Properties()));
 public static final RegistryObject<Item> GRAVEL_FOSSIL = ITEMS.register("gravel_fossil", () -> new Item(new Item.Properties()));
 public static final RegistryObject<Item> NUTRIENT_SOUP = ITEMS.register("nutrient_soup", () -> new BucketFoodItem(new Item.Properties().stacksTo(1).food(ModFoodProperties.NUTRIENT_SOUP).craftRemainder(Items.BUCKET)));


    //add Item to Registry
    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}

}
