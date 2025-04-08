package com.savw;


import com.savw.block.SkyAboveVoiceWithinBlocks;
import com.savw.block.blocks.SkyAboveVoiceWithinBlockEntityTypes;
import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import com.savw.entity.projectile.FireShockwave;
import com.savw.entity.projectile.ForceShockwave;
import com.savw.entity.projectile.FrostShockwave;
import com.savw.networking.*;
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

	@Deprecated
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

    @Override
	public void onInitialize() {
		LOGGER.info("Sky Above, Voice Within mod initialized!");

		SkyAboveVoiceWithinNetworking.initialize();
        SkyAboveVoiceWithinBlocks.initialize();
		SkyAboveVoiceWithinMobEffects.initialize();
		SkyAboveVoiceWithinBlockEntityTypes.initialize();
		Words.initialize();
		Shouts.initialize();
		SkyAboveVoiceWithinSounds.initialize();

	}
}