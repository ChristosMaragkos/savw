package com.savw.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static com.savw.SkyAboveVoiceWithin.FORCE_SHOCKWAVE;

public class ForceShockwave extends AbstractShockwaveEntity{

    public ForceShockwave(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public ForceShockwave(LivingEntity shooter, Vec3 vec3, Level level) {
        super(FORCE_SHOCKWAVE, level);
        this.setOwner(shooter);
    }

    public ForceShockwave(double x, double y, double z, Level level) {
        super(FORCE_SHOCKWAVE, level);
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        if (!this.level().isClientSide) {
            Entity entity = entityHitResult.getEntity();

            entity.hurtServer((ServerLevel) this.level(), this.level().damageSources().indirectMagic(this, entity), 5.0F);

            // Apply knockback or other effects to the entity hit by the shockwave
            Vec3 knockbackDirection = entity.position().subtract(this.position()).normalize();
            entity.push(knockbackDirection);

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);

            this.discard();
        }
    }

    @Override
    public void useShout(Player player, Level level) {
        ForceShockwave forceShockwave = new ForceShockwave(player, player.getLookAngle(), level);
        level.addFreshEntity(forceShockwave);
        forceShockwave.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 1.0F, 0.0F);
    }
}
