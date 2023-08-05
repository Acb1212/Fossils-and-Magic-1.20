package net.acb.fmmod.blocks.entity;

import net.acb.fmmod.FMMod;
import net.acb.fmmod.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FMMod.MODID);

    public static final RegistryObject<BlockEntityType<FossilExtractionTableBlockEntity>> FOSSIL_EXTRACTION_TABLE_BE =
            BLOCK_ENTITIES.register("fossil_extraction_table_block_entity", () ->
                    BlockEntityType.Builder.of(FossilExtractionTableBlockEntity::new, ModBlocks.FOSSIL_EXTRACTION_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<DNAExtractionTableBlockEntity>> DNA_EXTRACTION_TABLE_BE =
            BLOCK_ENTITIES.register("dna_extraction_table_block_entity", () ->
                    BlockEntityType.Builder.of(DNAExtractionTableBlockEntity::new, ModBlocks.DNA_EXTRACTION_TABLE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
