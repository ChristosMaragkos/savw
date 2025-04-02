package com.savw.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.savw.SkyAboveVoiceWithin.FROST_SHOCKWAVE;

public class FrostShockwave extends AbstractShockwaveProjectile{

    public FrostShockwave(ServerLevel level, LivingEntity owner, int wordsUsedToSummon) {
        super(FROST_SHOCKWAVE, level, owner, (1.5f * wordsUsedToSummon)/(1f + wordsUsedToSummon), wordsUsedToSummon);
    }

    public FrostShockwave(EntityType<FrostShockwave> frostShockwaveEntityType, Level level) {
        super(frostShockwaveEntityType, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        target.hurtServer(serverLevel, damageSources().freeze(),
                1.5f * getWordsUsedToSummon());
        target.setIsInPowderSnow(true);
        target.setTicksFrozen(200 + 50 * getWordsUsedToSummon()); //IMPORTANT: This is the time the entity has SPENT in the snow.
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * getWordsUsedToSummon(), (int) (1 + 0.5 * getWordsUsedToSummon()), false, false));
    }
}
