package com.savw;

import com.savw.shout.Shouts;
import com.savw.word.Words;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

///# StateSaverAndLoader
/// A class responsible for saving and loading the state of the game.
public class StateSaverAndLoader extends SavedData {

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    /**
     * Saves the current state to a CompoundTag.
     *
     * @param compoundTag The tag to save the state to.
     * @param provider The provider for holder lookup.
     * @return The modified CompoundTag containing the saved state.
     */
    @Override
    public @NotNull CompoundTag save(CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {

        CompoundTag playersNbt = new CompoundTag();
        players.forEach(((uuid, playerData) -> {
            CompoundTag playerNbt = new CompoundTag();
            ListTag listTag = new ListTag();

            playerData.unlockedWords.forEach(shoutWord -> listTag.add(StringTag.valueOf(shoutWord.getName())));

            playerNbt.put("unlockedWords", listTag);

            playerNbt.putString("currentShout", playerData.currentShout != null ? playerData.currentShout.getName() : "");

            playerNbt.putInt("shoutCooldown", playerData.shoutCooldown);

            playersNbt.put(uuid.toString(), playerNbt);
        }));

        compoundTag.put("players", playersNbt);

        return compoundTag;
    }

    /**
     * Creates a new StateSaverAndLoader instance from a CompoundTag.
     *
     * @param compoundTag The tag to load the state from.
     * @param provider The provider for holder lookup.
     * @return A new StateSaverAndLoader instance with the loaded state.
     */
    public static StateSaverAndLoader createFromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        StateSaverAndLoader state = new StateSaverAndLoader();

        CompoundTag playersNbt = compoundTag.getCompound("players");
        playersNbt.getAllKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            playerData.unlockedWords = playersNbt.getCompound(key).getList("unlockedWords", StringTag.TAG_STRING)
                    .stream()
                    .map(Tag::getAsString)
                    .map(Words::getByName)
                    .filter(Objects::nonNull)
                    .toList();

            playerData.currentShout = playersNbt.getCompound(key).getString("currentShout").isEmpty() ? null
                    : Shouts.getShoutByName(playersNbt.getCompound(key).getString("currentShout"));

            playerData.shoutCooldown = playersNbt.getCompound(key).getInt("shoutCooldown");

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }

    /**
     * Creates a new StateSaverAndLoader instance with default values.
     *
     * @return A new StateSaverAndLoader instance.
     */
    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>(); // Initialize with an empty map
        return state;
    }

    @SuppressWarnings("DataFlowIssue")
    private static final Factory<StateSaverAndLoader> TYPE = new Factory<>(
            StateSaverAndLoader::createNew,
            StateSaverAndLoader::createFromNbt,
            null
    );

    /**
     * Retrieves the server state for the given Minecraft server.
     *
     * @param server The Minecraft server.
     * @return The StateSaverAndLoader instance for the server.
     */
    public static StateSaverAndLoader getServerState(MinecraftServer server) {

        ServerLevel level = server.getLevel(Level.OVERWORLD);

        assert level != null;
        StateSaverAndLoader state = level.getDataStorage().computeIfAbsent(TYPE, SkyAboveVoiceWithin.MOD_ID);

        state.setDirty();

        return state;

    }

    /**
     * Retrieves the player state for the given player.
     *
     * @param player The player entity.
     * @return The PlayerData instance for the player.
     */
    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(Objects.requireNonNull(player.level().getServer()));

        return serverState.players.computeIfAbsent(player.getUUID(), uuid -> new PlayerData());
    }

}
