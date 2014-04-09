package net.nunnsy.teloschopper.strategy.antiban.tasks;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.utility.game.Controls;

public class MouseMove implements Job {

	@Override
	public void run() {
		Controls.setMouseUsed(true);
		Mouse.move(Random.nextInt(20, 730), Random.nextInt(20, 500));
		Controls.setMouseUsed(false);
	}
	
}
