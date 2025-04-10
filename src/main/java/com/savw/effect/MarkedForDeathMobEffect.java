package com.savw.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MarkedForDeathMobEffect extends MobEffect {

    protected MarkedForDeathMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFAA00FF);
    }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        return entity.getMaxHealth() > 1f;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 30 >> amplifier;
        return i == 0 || duration % i == 0;
    }


}
