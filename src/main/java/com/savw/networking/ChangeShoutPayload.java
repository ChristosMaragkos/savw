package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import com.savw.shout.AbstractShout;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ChangeShoutPayload(AbstractShout shoutToSwitchTo) implements CustomPacketPayload {

    public static final ResourceLocation CHANGE_SHOUT_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "change_shout_payload");
    public static final Type<ChangeShoutPayload> CHANGE_SHOUT_PAYLOAD_TYPE = new Type<>(CHANGE_SHOUT_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeShoutPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(AbstractShout.SHOUT_NAME_CODEC),
            ChangeShoutPayload::shoutToSwitchTo,
            ChangeShoutPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return CHANGE_SHOUT_PAYLOAD_TYPE;
    }

}
