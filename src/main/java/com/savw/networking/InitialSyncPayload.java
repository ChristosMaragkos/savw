package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record InitialSyncPayload(AbstractShout currentShout, List<ShoutWord> unlockedWords, Integer shoutCooldown) implements CustomPacketPayload {

    public static final ResourceLocation INITIAL_SYNC_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "initial_sync_payload");
    public static final CustomPacketPayload.Type<InitialSyncPayload> INITIAL_SYNC_PAYLOAD_TYPE = new CustomPacketPayload.Type<>(INITIAL_SYNC_PAYLOAD_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return INITIAL_SYNC_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, InitialSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ShoutWord.CODEC.listOf()),
            InitialSyncPayload::unlockedWords,
            ByteBufCodecs.fromCodec(AbstractShout.SHOUT_NAME_CODEC),
            InitialSyncPayload::currentShout,
            ByteBufCodecs.INT,
            InitialSyncPayload::shoutCooldown,
            (unlockedWords, currentShout, shoutCooldown) -> new InitialSyncPayload(currentShout, unlockedWords, shoutCooldown)
    );

}
