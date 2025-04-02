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

public record SendShoutAndWordSyncPayload(AbstractShout currentShout, List<ShoutWord> unlockedWords) implements CustomPacketPayload {

    public static final ResourceLocation SEND_SHOUT_AND_WORD_SYNC_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "send_shout_and_word_sync_payload");
    public static final Type<SendShoutAndWordSyncPayload> SEND_SHOUT_AND_WORD_SYNC_TYPE = new Type<>(SEND_SHOUT_AND_WORD_SYNC_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return SEND_SHOUT_AND_WORD_SYNC_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SendShoutAndWordSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ShoutWord.CODEC.listOf()),
            SendShoutAndWordSyncPayload::unlockedWords,
            ByteBufCodecs.fromCodec(AbstractShout.SHOUT_NAME_CODEC),
            SendShoutAndWordSyncPayload::currentShout,
            (unlockedWords, currentShout) -> new SendShoutAndWordSyncPayload(currentShout, unlockedWords)
    );

}
