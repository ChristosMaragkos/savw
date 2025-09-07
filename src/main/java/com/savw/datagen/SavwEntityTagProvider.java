package com.savw.datagen;

import com.savw.tag.SavwTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class SavwEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public SavwEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(SavwTags.ANIMAL_ALLEGIANCE_1_TARGETS)
                .add(EntityType.COW,
                        EntityType.SHEEP,
                        EntityType.PIG,
                        EntityType.CAT,
                        EntityType.CHICKEN,
                        EntityType.RABBIT);

        getOrCreateTagBuilder(SavwTags.ANIMAL_ALLEGIANCE_2_TARGETS)
                .addTag(SavwTags.ANIMAL_ALLEGIANCE_1_TARGETS)
                .add(EntityType.WOLF,
                        EntityType.FOX,
                        EntityType.BEE,
                        EntityType.PANDA,
                        EntityType.HORSE,
                        EntityType.DONKEY,
                        EntityType.MULE,
                        EntityType.LLAMA,
                        EntityType.TRADER_LLAMA,
                        EntityType.OCELOT,
                        EntityType.GOAT,
                        EntityType.CAMEL);

        getOrCreateTagBuilder(SavwTags.ANIMAL_ALLEGIANCE_3_TARGETS)
                .addTag(SavwTags.ANIMAL_ALLEGIANCE_2_TARGETS)
                .add(EntityType.POLAR_BEAR,
                        EntityType.MOOSHROOM,
                        EntityType.SPIDER,
                        EntityType.CAVE_SPIDER,
                        EntityType.AXOLOTL,
                        EntityType.HOGLIN,
                        EntityType.RAVAGER,
                        EntityType.SKELETON_HORSE,
                        EntityType.ZOMBIE_HORSE,
                        EntityType.STRIDER);
    }
}
