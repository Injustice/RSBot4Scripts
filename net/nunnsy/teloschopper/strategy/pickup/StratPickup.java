package net.nunnsy.teloschopper.strategy.pickup;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.utility.Constants;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class StratPickup extends Strategy implements Job {

	@Override
	public boolean validate() {
		if (Dynamics.getState() == State.PICKING) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		GroundItem item = getNextItem();
		
		if (item != null && !Players.getLocal().isMoving()) {
			item.interact("Take");
			Task.sleep(Random.nextInt(200, 400));
		}
	}
	
	private static GroundItem getNextItem() {
		GroundItem item = GroundItems.getNearest(new Filter<GroundItem>() {
			@Override
			public boolean accept(GroundItem i) {
				for (int ID : Constants.keepIDs()) {
					if (i.getId() == ID) {
						return true;
					}
				}
				return false;
			}
		});
		
		return item;
	}
	
	public static boolean itemsToBePicked() {
		if (getNextItem() != null) {
			return true;
		}
		
		return false;
	}
}
