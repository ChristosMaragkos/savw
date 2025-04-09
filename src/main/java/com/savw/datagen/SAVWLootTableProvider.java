package com.savw.datagen;

import com.savw.loot_table.SAVWLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class SAVWLootTableProvider extends SimpleFabricLootTableProvider {

    public SAVWLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {

        output.accept(SAVWLootTables.WORD_WALL_POT,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))).setWeight(50))
                                        .add(LootItem.lootTableItem(Items.BONE_MEAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 4f))).setWeight(40))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))).setWeight(35))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).setWeight(30))
                                        .add(LootItem.lootTableItem(Items.IRON_SWORD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.083f, 0.45f))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_AXE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.083f, 0.45f))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_HELMET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.125f, 0.4f))).setWeight(12))
                                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.125f, 0.4f))).setWeight(12))
                                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.077f, 0.48f))).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.45f))).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.BOW).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.083f, 0.45f))).setWeight(6))
                                        .add(LootItem.lootTableItem(Items.CROSSBOW).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05f, 0.48f))).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).apply(SetNameFunction.setName(Component.literal("Crown"), SetNameFunction.Target.ITEM_NAME)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3f, 0.45f))).setWeight(1))                        ));

    }

    @Override
    public @NotNull String getName() {
        return "Sky Above, Voice Within Loot Tables";
    }
}
