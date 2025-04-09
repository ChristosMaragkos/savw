package com.savw.ui;

import com.savw.shout.AbstractShout;

public final class CustomUiComponents {

    public static ClickableTextureComponent clickableTexture(AbstractShout shout){
        return new ClickableTextureComponent(shout);
    }

    public static ArrowButton arrowButton(ShoutSelectionScreen parent, boolean previous){
        return new ArrowButton(parent, previous);
    }
}
