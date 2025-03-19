package com.savw;


import com.savw.block.SkyAboveVoiceWithinBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyAboveVoiceWithin implements ModInitializer {
	public static final String MOD_ID = "savw";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		System.out.println("Sky Above, Voice Within mod initialized!");
		SkyAboveVoiceWithinBlocks.init();
	}
}