package com.savw.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class StaminaDrainedMobEffect extends MobEffect {

    protected StaminaDrainedMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if (player.getFoodData().getFoodLevel() < 20) {
                player.causeFoodExhaustion(1f * (amplifier + 1));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 50 >> amplifier;
        return i == 0 || duration % i == 0;
    }

}
