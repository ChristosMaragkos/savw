package com.savw.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.savw.SkyAboveVoiceWithin.DRAINING_SHOCKWAVE;
import static com.savw.effect.SkyAboveVoiceWithinMobEffects.STAMINA_DRAINED;
import static com.savw.effect.SkyAboveVoiceWithinMobEffects.STAMINA_REPLENISHED;
import static net.minecraft.world.effect.MobEffects.*;

public class DrainingShockwave extends AbstractShockwaveProjectile{

    //todo add entity renderer and shout for this
    public DrainingShockwave(ServerLevel level, LivingEntity owner, int wordsUsedToSummon) {
        super(DRAINING_SHOCKWAVE, level, owner, (2f * wordsUsedToSummon)/(1f + wordsUsedToSummon), wordsUsedToSummon, true);
    }

    public DrainingShockwave(EntityType<DrainingShockwave> drainingShockwaveEntityType, Level level) {
        super(drainingShockwaveEntityType, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return ParticleTypes.FALLING_OBSIDIAN_TEAR;
    }

    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        switch (getWordsUsedToSummon()) {
            case 1 -> {
                if (getOwner() instanceof ServerPlayer serverPlayer) {
                    target.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 80, 1, false, false));
                    target.addEffect(new MobEffectInstance(DIG_SLOWDOWN, 80, 1, false, false));
                    target.addEffect(new MobEffectInstance(STAMINA_DRAINED, 80, 1, false, true));

                    serverPlayer.addEffect(new MobEffectInstance(MOVEMENT_SPEED, 80, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(DIG_SPEED, 80, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(STAMINA_REPLENISHED, 80, 1, false, true));
                }
            }
            case 2 -> {
                if (getOwner() instanceof ServerPlayer serverPlayer) {
                    target.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 120, 1, false, false));
                    target.addEffect(new MobEffectInstance(DIG_SLOWDOWN, 120, 1, false, false));
                    target.addEffect(new MobEffectInstance(STAMINA_DRAINED, 120, 2, false, true));
                    target.addEffect(new MobEffectInstance(WEAKNESS, 120, 1, false, false));

                    serverPlayer.addEffect(new MobEffectInstance(MOVEMENT_SPEED, 120, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(DIG_SPEED, 120, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(DAMAGE_BOOST, 120, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(STAMINA_REPLENISHED, 120, 2, false, true));
                }
            }
            case 3 -> {
                if (getOwner() instanceof ServerPlayer serverPlayer) {
                    target.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 160, 1, false, false));
                    target.addEffect(new MobEffectInstance(DIG_SLOWDOWN, 160, 1, false, false));
                    target.addEffect(new MobEffectInstance(STAMINA_DRAINED, 160, 3, false, true));
                    target.addEffect(new MobEffectInstance(WEAKNESS, 160, 1, false, false));
                    target.addEffect(new MobEffectInstance(WITHER, 160, 2, false, false));

                    serverPlayer.addEffect(new MobEffectInstance(MOVEMENT_SPEED, 160, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(DIG_SPEED, 160, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(DAMAGE_BOOST, 160, 1, false, false));
                    serverPlayer.addEffect(new MobEffectInstance(REGENERATION, 160, 1, false, true));
                    serverPlayer.addEffect(new MobEffectInstance(STAMINA_REPLENISHED, 160, 3, false, true));
                }
            }
        }
    }
}
