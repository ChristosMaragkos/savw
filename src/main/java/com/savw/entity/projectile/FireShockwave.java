package com.savw.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.savw.SkyAboveVoiceWithin.FIRE_SHOCKWAVE;

public class FireShockwave extends AbstractShockwaveProjectile {

    public FireShockwave(Level level, LivingEntity owner, int wordsUsedToSummon) {
        super(FIRE_SHOCKWAVE, level, owner, (2f * wordsUsedToSummon)/(1f + wordsUsedToSummon), wordsUsedToSummon);
    }

    public FireShockwave(EntityType<FireShockwave> fireShockwaveEntityType, Level level) {
        super(fireShockwaveEntityType, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        target.hurtServer(serverLevel, damageSources().indirectMagic(Objects.requireNonNull(getOwner()), target),
                1.5f * getWordsUsedToSummon());

        target.setSharedFlagOnFire(true);
        target.setRemainingFireTicks(getWordsUsedToSummon() * 60);
    }

}
