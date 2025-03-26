package com.savw.block.blocks;

import com.savw.SkyAboveVoiceWithin;
import com.savw.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {

    public static <T extends BlockEntityType<?>> T register(String name, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, name), blockEntityType);
    }

    public static final BlockEntityType<WordWallBlockEntity> WORD_WALL_BLOCK_ENTITY_TYPE = register("word_wall_block_entity",
            FabricBlockEntityTypeBuilder.create(WordWallBlockEntity::new,
                    ModBlocks.ETCHED_DEEPSLATE,
                    ModBlocks.ETCHED_BLACKSTONE,
                    ModBlocks.ETCHED_END_STONE).build()
    );

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within block entity types initialized!");
    }

}
