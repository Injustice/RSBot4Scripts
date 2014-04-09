package net.nunnsy.teloschopper.strategy.antiban.tasks;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.utility.game.Controls;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;

public class StatCheck implements Job {

	@Override
	public void run() {
		if (Widgets.get(1213, 13).isOnScreen()) {
			Controls.setMouseUsed(true);
			Mouse.move(Widgets.get(1213, 13).getCentralPoint(), 15, 15);
			Controls.setMouseUsed(false);
		}
	}

}
