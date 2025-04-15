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
import wtg.std.task.ServerEvents;
import wtg.std.task.ServerTickTask;

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

    /// Holy shit. <br>
    /// STDLib has enabled me to apply continuous Shout effects in a mob effect-like manner.
    /// I'm applying the freeze effect from powder snow here. <br>
    /// <small>I made that fucking library for this mod and I didn't even anticipate how much it could do for me.</small>
    @Override
    protected void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel) {
        ServerTickTask freezeTickTask = new ServerTickTask(90 * getWordsUsedToSummon(), server -> {
            target.setIsInPowderSnow(true);
            target.setTicksFrozen(200 + 50 * getWordsUsedToSummon()); //IMPORTANT: This is the time the entity has SPENT in the snow.
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 * getWordsUsedToSummon(), (int) (1 + 0.5 * getWordsUsedToSummon()), false, false));
        });
        target.hurtServer(serverLevel, damageSources().freeze(), 2f * getWordsUsedToSummon());
        ServerEvents.registerAt(ServerEvents.END_SERVER_TICK, freezeTickTask);
    }
}
