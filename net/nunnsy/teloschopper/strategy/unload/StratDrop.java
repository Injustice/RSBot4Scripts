package net.nunnsy.teloschopper.strategy.unload;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.utility.Constants;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

public class StratDrop extends Strategy implements Job {
	
	@Override
	public boolean validate() {
		
		if (Dynamics.getState() == State.UNLOADING && Dynamics.getMethod() == UnloadMethod.DROP) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		
		Item[] items = Inventory.getItems(new Filter<Item>() {
			@Override
			public boolean accept(Item item) {
				for (int id : Constants.keepIDs()) {
					if (item.getId() == id) {
						return false;
					}
				}
				return true;
			}
		});
		
		if (items.length > 0) {
			items[0].getWidgetChild().interact("Drop");
			Task.sleep(Random.nextInt(100, 200));
		}
	}

}
