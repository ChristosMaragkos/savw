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

    public static final Block SMART_ETCHED_DEEPSLATE = registerBlock("smart_etched_deepslate",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REINFORCED_DEEPSLATE)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "smart_etched_deepslate")))));

    public static final Block SMART_ETCHED_BLACKSTONE = registerBlock("smart_etched_blackstone",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "smart_etched_blackstone")))));

    public static final Block SMART_ETCHED_END_STONE = registerBlock("smart_etched_end_stone",
            new WordWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "smart_etched_end_stone")))));

    public static final Block DUMB_ETCHED_DEEPSLATE = registerBlock("dumb_etched_deepslate",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.REINFORCED_DEEPSLATE)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "dumb_etched_deepslate")))));

    public static final Block DUMB_ETCHED_BLACKSTONE = registerBlock("dumb_etched_blackstone",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "dumb_etched_blackstone")))));

    public static final Block DUMB_ETCHED_END_STONE = registerBlock("dumb_etched_end_stone",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "dumb_etched_end_stone")))));
    
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