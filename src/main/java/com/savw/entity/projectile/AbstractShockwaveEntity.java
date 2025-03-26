package com.savw.entity.projectile;

import com.savw.entity.ShoutEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public abstract class AbstractShockwaveEntity extends Projectile implements ShoutEffect {

    public AbstractShockwaveEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            // Client-side logic for the shockwave effect
            // For example, you can add particle effects or sound effects here
        } else {
            // Server-side logic for the shockwave effect
            if (this.tickCount > 60) { // Example condition to remove the entity after a certain time
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
    }

    @Override
    public void shoot(double d, double e, double f, float g, float h) {
        super.shoot(d, e, f, g, h);
    }
}
