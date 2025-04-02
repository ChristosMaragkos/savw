package com.savw.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;

import static com.savw.SkyAboveVoiceWithin.FORCE_SHOCKWAVE;

public class ForceShockwave extends AbstractShockwaveProjectile {

    public ForceShockwave(Level level, LivingEntity owner, int wordsUsedToSummon) {
        super(FORCE_SHOCKWAVE, level, owner, (2f * wordsUsedToSummon)/(1f + wordsUsedToSummon), wordsUsedToSummon);
    }

    public ForceShockwave(EntityType<ForceShockwave> forceShockwaveEntityType, Level level) {
        super(forceShockwaveEntityType, level);
    }

    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        Vec3 knockbackDirection = this.getDeltaMovement().normalize().scale(0.05 * getWordsUsedToSummon() ).add(0.0, 0.05 * getWordsUsedToSummon(), 0.0);

        target.hurtServer(serverLevel, damageSources().indirectMagic(Objects.requireNonNull(getOwner()), target),
                1.5f * getWordsUsedToSummon());
        target.knockback(getWordsUsedToSummon() * 0.2f, -knockbackDirection.x, -knockbackDirection.z);
        target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.02 * getWordsUsedToSummon(), 0.0));
    }

}
