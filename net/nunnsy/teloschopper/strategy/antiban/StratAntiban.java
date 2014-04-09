package net.nunnsy.teloschopper.strategy.antiban;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.antiban.tasks.CameraMove;
import net.nunnsy.teloschopper.strategy.antiban.tasks.MouseMove;
import net.nunnsy.teloschopper.strategy.antiban.tasks.StatCheck;

import org.powerbot.game.api.util.Random;

public class StratAntiban extends Strategy implements Job {
	
	private static CameraMove cameraMove = new CameraMove();
	private static MouseMove mouseMove = new MouseMove();
	private static StatCheck statCheck = new StatCheck();
	
	private static boolean useAntiban;

	@Override
	public boolean validate() {
		if (useAntiban) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		if (Random.nextInt(0, 500) == 27) {
			cameraMove.run();
		}
		
		if (Random.nextInt(0, 200) == 27) {
			mouseMove.run();
		}
		
		if (Random.nextInt(0, 1000) == 27) {
			statCheck.run();
		}
	}
	
	public static void setAntiban(boolean AB) {
		useAntiban = AB;
	}
}
