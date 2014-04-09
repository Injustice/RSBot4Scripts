package net.nunnsy.teloschopper.strategy.antiban.tasks;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.utility.game.Controls;

import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

public class CameraMove implements Job {

	@Override
	public void run() {
		Controls.setCameraUsed(true);
		Camera.setPitch(Random.nextInt(3, 87));
		Camera.setAngle(Random.nextInt(0, 360));
		Controls.setCameraUsed(false);
	}
}
