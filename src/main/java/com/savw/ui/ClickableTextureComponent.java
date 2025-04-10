package com.savw.ui;

import com.savw.networking.ChangeShoutPayload;
import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Sizing;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import static com.savw.SkyAboveVoiceWithinClient.clientPlayerData;

/// A clickable texture icon that
/// is hardcoded to represent a shout.
/// It displays the shout's icon and name, and when clicked,
/// it sets the current shout for the player.
/// It also shows the words of the shout and its description in a tooltip.
public class ClickableTextureComponent extends TextureComponent {

    private final AbstractShout shout;
    public boolean isSelected = false;

    protected ClickableTextureComponent(AbstractShout shout) {
        super(shout.getIconLocation(), 0, 0, 256, 256, 256, 256);
        this.shout = shout;
        this.sizing(Sizing.fixed(48));
        this.tooltip(Component.literal(shout.getName() + "\n"
                + formatWord(shout.getFirstWord()) + "\n"
                + formatWord(shout.getSecondWord()) + "\n"
                + formatWord(shout.getThirdWord()) + "§r\n"
                + shout.getDescription()));
        this.isSelected = clientPlayerData.currentShout == shout;
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {

        Minecraft client = Minecraft.getInstance();

        if (button == 0 && client.level != null && client.player != null) {

            int unlockedWordsCount = shout.getUnlockedWordsCount(clientPlayerData.unlockedWords);
            if (unlockedWordsCount == 0) {
                client.getSoundManager().play(new SimpleSoundInstance(SoundEvents.NOTE_BLOCK_BASS.value(),
                        SoundSource.PLAYERS, 1f, 0.8f, client.level.getRandom(), client.player.blockPosition()));
                return false;
            }
            clientPlayerData.currentShout = shout;
            ClientPlayNetworking.send(new ChangeShoutPayload(clientPlayerData.currentShout));
            client.getSoundManager().play(new SimpleSoundInstance(SoundEvents.UI_BUTTON_CLICK.value(),
                    SoundSource.PLAYERS, 1f, 1f, client.level.getRandom(), client.player.blockPosition()));
            client.setScreen(null);

        }
        return super.onMouseDown(mouseX, mouseY, button);
    }

    private String getColorBasedOnUnlockedStatus(boolean unlocked) {
        return unlocked ? "f" : "8";
    }

    private String formatWord(ShoutWord word) {
        boolean unlocked = clientPlayerData.unlockedWords.contains(word);
        return "§" + getColorBasedOnUnlockedStatus(unlocked) + word.getName();
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
        super.draw(context, mouseX, mouseY, partialTicks, delta);
        if (hovered) {
            context.drawRectOutline(this.x, this.y,
                    this.width(), this.height(),
                    0xFFFFFF00);
        }
    }
}
