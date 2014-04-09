package net.nunnsy.teloschopper.utility;

import org.powerbot.game.api.methods.interactive.Players;

public class Failsafe {
	
	private static long lastAction = 0;
	private static long timeOut = 120000;

	public static boolean run() {
		if (Players.getLocal().getAnimation() != -1) {
			lastAction = System.currentTimeMillis();
		}
		
		if (lastAction != 0 && System.currentTimeMillis() > lastAction + timeOut) {
			return true;
		}
		
		return false;
	}

}
