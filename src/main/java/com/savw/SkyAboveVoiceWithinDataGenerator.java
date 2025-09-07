package com.savw;

import com.savw.datagen.SAVWLootTableProvider;
import com.savw.datagen.SavwEntityTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SkyAboveVoiceWithinDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(SAVWLootTableProvider::new);
        pack.addProvider(SavwEntityTagProvider::new);
    }
}