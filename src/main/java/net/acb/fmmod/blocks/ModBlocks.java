package net.acb.fmmod.blocks;



import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.advancedBlocks.DNAExtractionTable;
import net.acb.fmmod.blocks.advancedBlocks.FossilExtractionTable;
import net.acb.fmmod.blocks.advancedBlocks.SinkingBlock;
import net.acb.fmmod.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    //Create Registry for Modded Blocks
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FMMod.MODID);

    //Modded Blocks Here
    public static final RegistryObject<Block> AMBER_ORE = registerBlock("amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> TAR_BLOCK = registerSimpleBlock("tar_block", () -> new SinkingBlock(BlockBehaviour.Properties.copy(Blocks.MUD).destroyTime(-1)));
    public static final RegistryObject<Block> CULTURE_VAT = registerBlock("culture_vat", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));
    public static final RegistryObject<Block> FOSSIL_EXTRACTION_TABLE = registerBlock("fossil_extraction_table", () -> new FossilExtractionTable(BlockBehaviour.Properties.copy(Blocks.SMITHING_TABLE)));
    public static final RegistryObject<Block> DNA_EXTRACTION_TABLE = registerBlock("dna_extraction_table", () -> new DNAExtractionTable(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE)));

    //Initialize Block Creation
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    //Initialize Block Creation
    private static <T extends Block> RegistryObject<T> registerSimpleBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    //Initialize Item Creation
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    //Add Block & Item to Registry
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
