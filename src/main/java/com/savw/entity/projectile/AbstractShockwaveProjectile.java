package com.savw.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/// # AbstractShockwaveProjectile
/// Represents the shockwave projectile used by most Shouts that act as ranged attacks.
/// This class is responsible for the projectile's behavior, including its movement, collision detection,
/// and the application of effects to entities it hits.
/// It also handles the projectile's lifetime and checks for block collisions.
/// @implSpec <br>For actual effects, subclasses should implement the `applyShockwaveEffect` method.
/// @see net.minecraft.world.entity.projectile.AbstractHurtingProjectile AbstractHurtingProjectile
/// @see ForceShockwave
/// @see FireShockwave
public abstract class AbstractShockwaveProjectile extends AbstractHurtingProjectile{

    private int lifetime;

    private final int wordsUsedToSummon;

    private final boolean stopUponImpact;

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    /// ### Constructor
    /// Creates a new instance of the AbstractShockwaveProjectile (duh).
    /// This constructor is used when the projectile is created by a player - for the one used in the actual entity registration, see the other constructor.
    /// @param entityType The type of the entity.
    /// @param level The level in which the entity is created.
    /// @param owner The entity that created this projectile.
    /// @param speed The speed of the projectile.
    /// @param wordsUsedToSummon The number of words used to summon this projectile.
    public AbstractShockwaveProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level,
                                       LivingEntity owner, float speed, int wordsUsedToSummon) {

        super(entityType, owner.getX(), owner.getY() + 1, owner.getZ(), owner.getEyePosition().normalize().scale(speed), level);
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
        this.setNoGravity(true);
        this.lifetime = 20;
        this.wordsUsedToSummon = wordsUsedToSummon;

        Vec3 lookingDirection = owner.getLookAngle().normalize().scale(speed);

        this.setDeltaMovement(lookingDirection);

        this.stopUponImpact = false;
    }

    public AbstractShockwaveProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level,
                                       LivingEntity owner, float speed, int wordsUsedToSummon, boolean stopUponImpact) {
        super(entityType, owner.getX(), owner.getY() + 1, owner.getZ(), owner.getEyePosition().normalize().scale(speed), level);
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
        this.setNoGravity(true);
        this.lifetime = 20;
        this.wordsUsedToSummon = wordsUsedToSummon;
        Vec3 lookingDirection = owner.getLookAngle().normalize().scale(speed);
        this.setDeltaMovement(lookingDirection);
        this.stopUponImpact = stopUponImpact;
    }

    /// ### Constructor
    /// DO NOT USE THIS CONSTRUCTOR DIRECTLY.
    /// It is not meant to be used by the player.
    /// @param entityType The type of the entity.
    /// @param level The level in which the entity is created.
    /// @implNote <br> This constructor is used by the game to create the entity when it is registered.
    public AbstractShockwaveProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        this(entityType, level, level.players().getFirst(), 0f, 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- == 0 || isBlockedByWall()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    /// Do not override this method in subclasses!
    /// Instead, override the `applyShockwaveEffect` method.
    /// @see #applyShockwaveEffect(LivingEntity, ServerLevel)
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();

        if (target instanceof LivingEntity livingEntity && target != this.getOwner() && this.level() instanceof ServerLevel serverLevel) {
            applyShockwaveEffect(livingEntity, serverLevel);

            if (shouldStopUponImpact()) {
                this.remove(RemovalReason.DISCARDED);
            }

        }
    }

    /// Override this method in subclasses to apply the desired effect to the target entity.
    /// Collision is already handled by the superclass method `onHitEntity`.
    /// All you need to do is code the effect you want to apply to the target entity.
    /// Convenient, eh?
    protected abstract void applyShockwaveEffect(LivingEntity target, ServerLevel serverLevel);

    /// Helper method to check if the projectile is blocked by a wall
    /// This method casts multiple rays in the direction of the projectile's movement
    /// and checks if they hit any blocks.
    /// If a significant number of rays hit blocks, we consider the projectile blocked.
    private boolean isBlockedByWall() {
        Vec3 direction = this.getDeltaMovement().normalize();
        double checkDistance = 0.1; // How far ahead we check
        int rayCount = 2; // Number of rays to cast across the front
        int hitCount = 0; // Count how many rays actually hit blocks
        double spread = 0.4; // How much to spread the rays horizontally

        for (int i = -rayCount / 2; i <= rayCount / 2; i++) {
            Vec3 offset = new Vec3(i * spread, 0, 0);
            BlockHitResult hitResult = this.level().clip(new ClipContext(
                    this.position().add(offset),
                    this.position().add(offset).add(direction.scale(checkDistance)),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            ));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                hitCount++;
            }
        }

        // Stop if at least 3 out of 4 rays hit something (adjust threshold as needed)
        return hitCount >= 1;
    }

    public final int getWordsUsedToSummon() {
        return wordsUsedToSummon;
    }

    public final boolean shouldStopUponImpact() {
        return this.stopUponImpact;
    }
}
