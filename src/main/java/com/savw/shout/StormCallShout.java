package com.savw.shout;

import com.savw.word.ShoutWord;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtg.std.task.ServerEvents;
import wtg.std.task.ServerTickTask;

import java.util.List;

public final class StormCallShout extends AbstractShout {

    StormCallHandler stormCallHandler;

    private StormCallShout(String name, String description,
                           ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
        stormCallHandler = null;
    }

    /**
     * Activates the storm if the level is a ServerLevel and no storm is currently active.
     *
     * @param player    the player using the shout
     * @param level     the level in which the shout is used
     * @param wordsUsed the number of words used in the shout
     */
    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level instanceof ServerLevel serverLevel){
            if (stormCallHandler != null) {
                player.displayClientMessage(Component.literal("There is already a Storm active!"), true);
                return;
            }
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER.value(), player.getSoundSource());
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    player.getX(), player.getY() + 1, player.getZ(), wordsUsed * 10,
                    0.5, 0.5, 0.5, 0.1);
            stormCallHandler = new StormCallHandler(serverLevel, player, 200 * wordsUsed,
                    getLightningInterval(wordsUsed), wordsUsed, this);
            stormCallHandler.startStorm();
        }
    }

    private int getLightningInterval(int wordsUsed) {
        return switch (wordsUsed) {
            case 1 -> 75;
            case 2 -> 65;
            case 3 -> 55;
            default -> 0;
        };
    }

    private static @Nullable List<Monster> getAllNearbyEnemies(Player player, Level level, int wordsUsed) {
        if (level.isClientSide()) {
            return null;
        }
        return level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(getCorrectBoundingBoxInflation(wordsUsed)));
    }

    private static double getCorrectBoundingBoxInflation(int wordsUsed) {
        switch (wordsUsed) {
            case 1 -> {
                return 15;
            }
            case 2 -> {
                return 20;
            }
            case 3 -> {
                return 25;
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Handles the storm call functionality, including starting and stopping the storm,
     * and striking lightning at nearby enemies.
     */
    protected static class StormCallHandler{
        private final ServerLevel level;
        private final Player player;
        private final int ticksRemaining;
        private final int lightningInterval;
        private int lightningCounter;
        private final int wordsUsed;
        public boolean isStormActive;
        private int ticksToStartThunder;
        private final StormCallShout parent;

        /// Constructs a new StormCallHandler.
        ///
        /// @param level the server level where the storm is called
        /// @param player the player who called the storm
        /// @param duration the duration of the storm in ticks
        /// @param interval the interval between lightning strikes in ticks
        /// @param wordsUsed the number of words used in the shout
        /// @param parent the parent StormCallShout instance (which is always {@link Shouts#STORM_CALL the singleton instance of this Shout}).
        public StormCallHandler(ServerLevel level, Player player, int duration, int interval, int wordsUsed,
                                @NotNull StormCallShout parent) {
            this.level = level;
            this.player = player;
            this.ticksRemaining = duration;
            this.lightningInterval = interval;
            this.lightningCounter = 0;
            this.wordsUsed = wordsUsed;
            this.isStormActive = true;
            this.ticksToStartThunder = 80; // Delay before any thunder strikes occur
            this.parent = parent;
        }


        public void startStorm() {

            this.level.setWeatherParameters(this.ticksRemaining + 280, 0, true, true); // Start storm

            ServerTickTask stormTickTask = new ServerTickTask(ticksRemaining + 80, server -> {
                if (ticksToStartThunder > 0) {
                    ticksToStartThunder--;
                    return; // Wait for the initial delay before starting thunder
                }

                this.lightningCounter++;

                if (this.lightningCounter >= this.lightningInterval && this.isStormActive) {
                    this.lightningCounter = 0;
                    strikeLightning();
                }
            });

            ServerTickTask afterStormTask = new ServerTickTask(1, server -> {
                this.level.setWeatherParameters(0, 6000, false, false); // Stop storm
                this.isStormActive = false;
                parent.stormCallHandler = null;
            });

            ServerEvents.registerAt(ServerEvents.END_SERVER_TICK, stormTickTask)
                    .appendAfterEnd(afterStormTask, ServerEvents.END_SERVER_TICK);

        }

        /**
         * Strikes lightning at a random nearby enemy.
         * The area of effect is determined by the number of words used in the shout
         * and is updated dynamically every tick.
         */
        private void strikeLightning() {
            List<Monster> mobs = getAllNearbyEnemies(this.player, this.level, this.wordsUsed);
            assert mobs != null;
            if (!mobs.isEmpty()) {
                Monster target = mobs.get(this.level.random.nextInt(mobs.size()));
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level);
                lightning.setPos(Vec3.atCenterOf(target.blockPosition()));
                this.level.addFreshEntity(lightning);
                target.thunderHit(this.level, lightning);
            }
        }
    }

}
