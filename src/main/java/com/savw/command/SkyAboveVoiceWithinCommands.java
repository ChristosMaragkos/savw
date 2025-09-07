package com.savw.command;

import com.savw.PlayerData;
import com.savw.StateSaverAndLoader;
import com.savw.networking.SendShoutAndWordSyncPayload;
import com.savw.registry.SkyAboveVoiceWithinRegistries;
import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.function.Supplier;

import static com.savw.effect.SkyAboveVoiceWithinMobEffects.SHOUT_COOLDOWN;
import static com.savw.shout.Shouts.DUMMY_INITIAL_SHOUT;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SkyAboveVoiceWithinCommands {

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher,
                                                    registryAccess,
                                                    environment) -> {

            dispatcher.register(literal("unlockWordFor")
                    .then(argument("shout", ResourceLocationArgument.id())
                            .suggests((context, builder) -> {
                                SkyAboveVoiceWithinRegistries.SHOUTS.keySet().forEach(id -> builder.suggest(id.toString()));
                                return builder.buildFuture();
                            })
                            .executes(context -> {
                                ServerPlayer player = context.getSource().getPlayer();

                                ResourceLocation shoutId = ResourceLocationArgument.getId(context, "shout");

                                AbstractShout shout = SkyAboveVoiceWithinRegistries.SHOUTS.getValue(shoutId);
                                if (shout == null || shout == DUMMY_INITIAL_SHOUT) {
                                    context.getSource().sendFailure(Component.literal("Unknown Shout: " + shoutId));
                                    return 0;
                                }

                                if (player == null) {
                                    context.getSource().sendFailure(Component.literal("This command can only be run by a player."));
                                    return 0;
                                }

                                PlayerData playerData = StateSaverAndLoader.getPlayerState(player);

                                List<ShoutWord> unlockedWords = playerData.unlockedWords;
                                ShoutWord wordToUnlock = shout.tryUnlockWord(unlockedWords);

                                if (wordToUnlock == null) {
                                    context.getSource().sendFailure(Component.literal("You have already unlocked all words for this shout."));
                                    return 1;
                                }

                                Supplier<Component> componentSupplier = () -> Component.literal("Unlocked Word " + wordToUnlock.getName() +
                                        " - " + wordToUnlock.getMeaning() +
                                        " for Shout " + shout.getName() + "!");

                                unlockedWords.add(wordToUnlock);
                                ServerPlayNetworking.send(player, new SendShoutAndWordSyncPayload(playerData.currentShout, unlockedWords));
                                context.getSource().sendSuccess(componentSupplier, true);
                                return 1;
                            })

                            .then(literal("all")
                                    .executes(context -> {
                                        ServerPlayer player = context.getSource().getPlayer();

                                        ResourceLocation shoutId = ResourceLocationArgument.getId(context, "shout");

                                        AbstractShout shout = SkyAboveVoiceWithinRegistries.SHOUTS.getValue(shoutId);
                                        if (shout == null || shout == DUMMY_INITIAL_SHOUT) {
                                            context.getSource().sendFailure(Component.literal("Unknown Shout: " + shoutId));
                                            return 0;
                                        }

                                        if (player == null) {
                                            context.getSource().sendFailure(Component.literal("This command can only be run by a player."));
                                            return 0;
                                        }

                                        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);

                                        List<ShoutWord> unlockedWords = playerData.unlockedWords;

                                        List<ShoutWord> wordsToUnlock = switch (shout.getUnlockedWordsCount(unlockedWords)){
                                            case 3 -> List.of();
                                            case 2 -> List.of(shout.getThirdWord());
                                            case 1 -> List.of(shout.getSecondWord(), shout.getThirdWord());
                                            case 0 -> List.of(shout.getFirstWord(), shout.getSecondWord(), shout.getThirdWord());
                                            default -> throw new IllegalStateException("Unexpected value: " + shout.getUnlockedWordsCount(unlockedWords));
                                        };
                                        if (wordsToUnlock.isEmpty()) {
                                            context.getSource().sendFailure(Component.literal("You have already unlocked all words for this shout."));
                                            return 1;
                                        }
                                        unlockedWords.addAll(wordsToUnlock);
                                        ServerPlayNetworking.send(player, new SendShoutAndWordSyncPayload(playerData.currentShout, unlockedWords));
                                        Supplier<Component> componentSupplier = () -> Component.literal("Unlocked all words for Shout " + shout.getName() + "!");
                                        context.getSource().sendSuccess(componentSupplier, true);
                                        return 1;
                                    }))
                    ));

            dispatcher.register(literal("removeShoutCooldown")
                    .executes(context -> {
                        if (context.getSource().isPlayer() && context.getSource().getPlayer() != null) {
                            PlayerData playerData = StateSaverAndLoader.getPlayerState(context.getSource().getPlayer());
                            if (playerData.shoutCooldown > 2){
                                playerData.shoutCooldown = 2;
                                context.getSource().sendSuccess(() -> Component.literal("Shout cooldown removed!"), true);
                                if (context.getSource().getPlayer().hasEffect(SHOUT_COOLDOWN)){
                                    context.getSource().getPlayer().removeEffect(SHOUT_COOLDOWN);
                                }
                                return 1;
                            } else {
                                context.getSource().sendFailure(Component.literal("Shout cooldown is already 0!"));
                                return 0;
                            }
                        }
                                return 0;
                            }
                    ));
        });

    }

}
