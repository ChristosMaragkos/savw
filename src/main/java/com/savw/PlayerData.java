package com.savw;

import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    public List<ShoutWord> unlockedWords = new ArrayList<>();

    public AbstractShout currentShout = null;

}
