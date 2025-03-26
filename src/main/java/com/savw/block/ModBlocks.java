package com.savw.block;

import com.savw.SkyAboveVoiceWithin;
import com.savw.block.blocks.WordWallBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block ETCHED_DEEPSLATE = registerBlock("etched_deepslate",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REINFORCED_DEEPSLATE)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "etched_deepslate")))));

    public static final Block ETCHED_BLACKSTONE = registerBlock("etched_blackstone",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "etched_blackstone")))));

    public static final Block ETCHED_END_STONE = registerBlock("etched_end_stone",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "etched_end_stone")))));
    
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, name),
                new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, name)))));
    }

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within blocks initialized!");
    }

}