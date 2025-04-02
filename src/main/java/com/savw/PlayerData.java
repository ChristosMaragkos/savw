package com.savw;

import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;

import java.util.ArrayList;
import java.util.List;

import static com.savw.shout.Shouts.DUMMY_INITIAL_SHOUT;

public class PlayerData {

    public List<ShoutWord> unlockedWords = new ArrayList<>();

    public AbstractShout currentShout = DUMMY_INITIAL_SHOUT;

    public int shoutCooldown = 0;

}
