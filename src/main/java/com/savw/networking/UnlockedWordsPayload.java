package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import com.savw.word.ShoutWord;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record UnlockedWordsPayload(List<ShoutWord> unlockedWords) implements CustomPacketPayload {

    public static final ResourceLocation UNLOCK_WORD_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "unlocked_words_payload");
    public static final Type<UnlockedWordsPayload> UNLOCK_WORD_PAYLOAD_TYPE = new Type<>(UNLOCK_WORD_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, UnlockedWordsPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ShoutWord.CODEC.listOf()),
            UnlockedWordsPayload::unlockedWords,
            UnlockedWordsPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return UNLOCK_WORD_PAYLOAD_TYPE;
    }

}
