package com.savw;


import com.savw.block.SkyAboveVoiceWithinBlocks;
import com.savw.block.blocks.SkyAboveVoiceWithinBlockEntityTypes;
import com.savw.command.SkyAboveVoiceWithinCommands;
import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import com.savw.entity.projectile.DrainingShockwave;
import com.savw.entity.projectile.FireShockwave;
import com.savw.entity.projectile.ForceShockwave;
import com.savw.entity.projectile.FrostShockwave;
import com.savw.loot_table.SAVWLootTables;
import com.savw.networking.*;
import com.savw.registry.SkyAboveVoiceWithinRegistries;
import com.savw.shout.Shouts;
import com.savw.sound.SkyAboveVoiceWithinSounds;
import com.savw.word.Words;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SkyAboveVoiceWithin implements ModInitializer {
	public static final String MOD_ID = "savw";

	public static ResourceLocation withModId(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<ForceShockwave> FORCE_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "force_shockwave"),
			EntityType.Builder.<ForceShockwave>of(ForceShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "force_shockwave")
					))
	);

	public static final EntityType<FireShockwave> FIRE_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "fire_shockwave"),
			EntityType.Builder.<FireShockwave>of(FireShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "fire_shockwave")
					))
	);

	public static final EntityType<FrostShockwave> FROST_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_shockwave"),
			EntityType.Builder.<FrostShockwave>of(FrostShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_shockwave")
					))
	);

	public static final EntityType<DrainingShockwave> DRAINING_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			withModId("draining_shockwave"),
			EntityType.Builder.<DrainingShockwave>of(DrainingShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "draining_shockwave")
					))
	);

    @Override
	public void onInitialize() {

		SkyAboveVoiceWithinRegistries.initialize();

		SkyAboveVoiceWithinNetworking.initialize();
        SkyAboveVoiceWithinBlocks.initialize();
		SkyAboveVoiceWithinMobEffects.initialize();
		SkyAboveVoiceWithinBlockEntityTypes.initialize();
		Words.initialize();
		Shouts.initialize();
		SkyAboveVoiceWithinSounds.initialize();
		SkyAboveVoiceWithinCommands.initialize();
		SAVWLootTables.initialize();

		LOGGER.info("Sky Above, Voice Within mod initialized!");

	}
}