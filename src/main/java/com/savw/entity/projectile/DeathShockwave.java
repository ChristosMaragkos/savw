package com.savw.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import wtg.std.task.ServerEvents;
import wtg.std.task.ServerTickTask;

import java.util.concurrent.atomic.AtomicInteger;

import static com.savw.SkyAboveVoiceWithin.DRAINING_SHOCKWAVE;
import static com.savw.effect.SkyAboveVoiceWithinMobEffects.MARKED_FOR_DEATH;
import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public class DeathShockwave extends AbstractShockwaveProjectile{

    public DeathShockwave(ServerLevel level, LivingEntity owner, int wordsUsedToSummon) {
        super(DRAINING_SHOCKWAVE, level, owner, (2f * wordsUsedToSummon)/(1f + wordsUsedToSummon), wordsUsedToSummon, true);
    }

    public DeathShockwave(EntityType<DeathShockwave> deathShockwaveEntityType, Level level) {
        super(deathShockwaveEntityType, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return ParticleTypes.LANDING_OBSIDIAN_TEAR;
    }

    /// I'm actually pretty proud of this one.
    /// Since I couldn't get {@link com.savw.effect.MarkedForDeathMobEffect the Marked For Death effect} to dynamically decrease attributes,
    /// I depended upon my trusty STDLib to append the same tick task to itself with an increasing delay using a for loop.
    /// The tick task then applies the effect to the target with a linearly increasing amplifier, simulating the effect getting worse.
    ///
    /// Is this the best way to do this? Probably not.
    ///
    /// Do I care?
    /// <small>...not really.</small>
    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        AtomicInteger timesApplied = new AtomicInteger();
        final boolean[] hasDisplayedFatalMessage = {false};
        serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.WITHER_SPAWN, target.getSoundSource(), 1f, 0.75f);
        ServerTickTask effectApplication = new ServerTickTask(1, server -> {
            target.addEffect(new MobEffectInstance(MARKED_FOR_DEATH, 120, timesApplied.getAndIncrement(), false, true));
            target.hurtServer(serverLevel, serverLevel.damageSources().magic(), 0.1f);

            if (target.getHealth() <= 6f) {
                target.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 120, 2, true, false));
                if (target instanceof Player player && player.getHealth() <= 6f && !hasDisplayedFatalMessage[0]) {
                    hasDisplayedFatalMessage[0] = true;
                    player.displayClientMessage(Component.literal("You are at death's door..."), true);
                }
            }

        });

        int actualDelay = 0;
        ServerEvents.registerAt(ServerEvents.END_SERVER_TICK, effectApplication);
        if (target instanceof Player player) {
            player.displayClientMessage(Component.literal("You feel a sense of impending doom..."), true);
        }
        for (int i = 0; i < getWordsUsedToSummon() * 4; i++) {
            actualDelay += 50;
            effectApplication.appendDelayedAfterEnd(effectApplication, actualDelay, ServerEvents.END_SERVER_TICK);
        }
    }
}
