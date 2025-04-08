package com.savw.effect;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class SkyAboveVoiceWithinMobEffects {

    public static final Holder<MobEffect> ETHEREAL =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "ethereal"),
                    new EtherealMobEffect());

    public static final Holder<MobEffect> SHOUT_COOLDOWN =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "shout_cooldown"),
                    new ShoutCooldownMobEffect());

    public static void initialize(){
        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within effects initialized!");
    }

}
