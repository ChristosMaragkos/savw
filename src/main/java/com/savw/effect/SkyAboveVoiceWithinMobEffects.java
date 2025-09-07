package com.savw.effect;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static com.savw.SkyAboveVoiceWithin.withModId;

public class SkyAboveVoiceWithinMobEffects {

    public static final Holder<MobEffect> ETHEREAL =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("ethereal"),
                    new EtherealMobEffect());

    public static final Holder<MobEffect> SHOUT_COOLDOWN =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("shout_cooldown"),
                    new ShoutCooldownMobEffect());

    public static final Holder<MobEffect> STAMINA_REPLENISHED =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("stamina_replenished"),
                    new StaminaReplenishedMobEffect());

    public static final Holder<MobEffect> STAMINA_DRAINED =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("stamina_drained"),
                    new StaminaDrainedMobEffect());
    
    public static final Holder<MobEffect> MARKED_FOR_DEATH =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("marked_for_death"),
                    new MarkedForDeathMobEffect()
                            .addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.withDefaultNamespace("effect.health_boost"), -1f, AttributeModifier.Operation.ADD_VALUE)
                            .addAttributeModifier(Attributes.ARMOR, withModId("effect.armor"), -1f, AttributeModifier.Operation.ADD_VALUE));

    public static final Holder<MobEffect> ANIMAL_ALLEGIANCE_ENRAGED =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    withModId("animal_allegiance_enraged"),
                    new AnimalAllegianceEnragedMobEffect()
                            .addAttributeModifier(Attributes.MOVEMENT_SPEED, withModId("effect.movement_speed"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                            .addAttributeModifier(Attributes.FLYING_SPEED, withModId("effect.flying_speed"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                            .addAttributeModifier(Attributes.ATTACK_DAMAGE, withModId("effect.attack_damage"), 2f, AttributeModifier.Operation.ADD_VALUE));

    public static void initialize(){
        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within effects initialized!");
    }

}
