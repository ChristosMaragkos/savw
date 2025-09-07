package com.savw.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnimalAllegianceAttackGoal extends MeleeAttackGoal {

    private final PathfinderMob affectedMob;
    private final Player player;
    private final int shoutWordsUsed;
    private int chaseTicks;
    private int duration;

    public AnimalAllegianceAttackGoal(PathfinderMob affectedMob, double speedModifier, boolean followingTargetEvenIfNotSeen, Player player, int shoutWordsUsed) {
        super(affectedMob, speedModifier, followingTargetEvenIfNotSeen);
        this.affectedMob = affectedMob;
        this.player = player;
        this.shoutWordsUsed = shoutWordsUsed;
    }

    @Override
    public void start() {
        super.start();
        chaseTicks = 0;
        duration = 20 * 10 * shoutWordsUsed; // 10 seconds per word used
        this.affectedMob.setAggressive(true);
        this.affectedMob.setTarget(FindValidTarget());
    }

    @Override
    public void stop() {
        super.stop();
        this.affectedMob.setAggressive(false);
        this.affectedMob.setTarget(null);
        this.affectedMob.goalSelector.removeGoal(this);
    }

    @Override
    public boolean canContinueToUse() {
        // We want animals to continue looking for targets until the duration expires.
        return duration > 0;
    }

    @Nullable
    private LivingEntity FindValidTarget() {
        // Build a weighted list of possible targets for more organic selection.
        // Priority weights (higher = more likely):
        //  - Last entity that hurt the player: 3.0
        //  - Last entity the player hurt: 2.0
        //  - Nearby monsters: inverse distance weight with slight randomness.
        LivingEntity lastHurtBy = this.player.getLastHurtByMob();
        LivingEntity lastAttacked = this.player.getLastHurtMob();

        // Collect candidates
        var rand = this.affectedMob.getRandom();
        var nearby = getNearbyMonsters();
        int capacity = 2 + nearby.size();
        LivingEntity[] candidates = new LivingEntity[capacity];
        double[] weights = new double[capacity];
        int count = 0;
        double totalWeight = 0.0;

        if (lastHurtBy != null && lastHurtBy.isAlive() && lastHurtBy != this.affectedMob) {
            candidates[count] = lastHurtBy;
            weights[count] = 3.0;
            totalWeight += weights[count];
            count++;
        }

        if (lastAttacked != null && lastAttacked.isAlive() && lastAttacked != this.affectedMob && lastAttacked != lastHurtBy) {
            candidates[count] = lastAttacked;
            weights[count] = 2.0;
            totalWeight += weights[count];
            count++;
        }

        for (Monster monster : nearby) {
            if (!monster.isAlive() || monster == this.affectedMob || monster == lastHurtBy || monster == lastAttacked) continue;
            double distSq = monster.distanceToSqr(this.player);
            // Inverse distance weight (closer = higher), bounded; add mild randomness (0.8–1.2 multiplier)
            double base = 1.0 / (Math.sqrt(distSq) + 1.0);
            double noise = 0.8 + rand.nextDouble() * 0.4;
            double weight = base * noise;
            candidates[count] = monster;
            weights[count] = weight;
            totalWeight += weight;
            count++;
        }

        if (count == 0 || totalWeight <= 0.0) {
            return null;
        }

        double r = rand.nextDouble() * totalWeight;
        for (int i = 0; i < count; i++) {
            r -= weights[i];
            if (r <= 0.0) {
                return candidates[i];
            }
        }
        // Fallback (numerical edge)
        return candidates[count - 1];
    }

    private @NotNull List<Monster> getNearbyMonsters() {
        return this.player.level().getEntitiesOfClass(Monster.class, this.player.getBoundingBox().inflate(15 * shoutWordsUsed));
    }

    @Override
    public void tick() {
        // The mob targets one of the following, checking in order:
        // 1. The last entity that hurt the player.
        // 2. The last entity that the player attacked.
        // 3. The closest hostile entity to the player.
        // Note: The mob will only switch targets if it has been chasing its current target for more than 60 ticks (3 seconds).
        super.tick();
        chaseTicks++;
        if (duration-- < 0) {
            return;
        }

        LivingEntity currentTarget = this.affectedMob.getTarget();

        // If no target or target is dead -> reacquire
        if (currentTarget == null || !currentTarget.isAlive()) {
            LivingEntity newTarget = FindValidTarget();

            if (newTarget != null) {
                this.affectedMob.setTarget(newTarget);
                this.affectedMob.setAggressive(true);
                chaseTicks = 0;
            }
            else {
                // Bodyguard fallback: stay near the player
                double distSq = this.affectedMob.distanceToSqr(player);

                double MAX_DIST = 8.0D * 8.0D; // squared distance
                if (distSq > MAX_DIST) {
                    // Too far -> move closer
                    this.affectedMob.getNavigation().moveTo(player, 1.3D);
                } else {
                    // Close enough -> just face the player
                    this.affectedMob.getNavigation().stop();
                    this.affectedMob.getLookControl().setLookAt(player, 30.0F, 30.0F);
                }
            }
            return;
        }

        // Otherwise, switch targets if chase too long
        int MAX_CHASE_TICKS = 60;
        if (chaseTicks > MAX_CHASE_TICKS && !(this.getTicksUntilNextAttack() < this.getAttackInterval() / 2)) {
            LivingEntity newTarget = FindValidTarget();
            if (newTarget != null && newTarget != currentTarget) {
                this.affectedMob.setTarget(newTarget);
                chaseTicks = 0;
            }
        }
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity target) {
        super.checkAndPerformAttack(target);
        if (this.canPerformAttack(target)) {
            chaseTicks = 0; // Reset chase timer on attack
            affectedMob.addDeltaMovement(new Vec3(0, 0.15, 0)); // Small hop on attack
        }
    }
}
