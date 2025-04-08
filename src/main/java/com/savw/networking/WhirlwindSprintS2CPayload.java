package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/// # Whirlwind Sprint S2C Payload
/// Since Shouting is handled ***server-side*** and modifying player velocity is handled
/// ***purely client-side***, use of a packet is necessary to communicate the use
/// of the shout between the two sides.
/// This packet is sent within {@link com.savw.shout.WhirlwindSprintShout#useShout(Player, Level, int) WhirlwindSprintShout#useShout}
/// and is then handled
public record WhirlwindSprintS2CPayload(int wordsUsed) implements CustomPacketPayload{

    public static final ResourceLocation WHIRLWIND_SPRINT_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "use_shout_payload");
    public static final CustomPacketPayload.Type<WhirlwindSprintS2CPayload> WHIRLWIND_SPRINT_PAYLOAD_TYPE = new CustomPacketPayload.Type<>(WHIRLWIND_SPRINT_PAYLOAD_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return WHIRLWIND_SPRINT_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, WhirlwindSprintS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            WhirlwindSprintS2CPayload::wordsUsed,
            WhirlwindSprintS2CPayload::new
    );

}
