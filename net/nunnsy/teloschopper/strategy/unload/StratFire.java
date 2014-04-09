package net.nunnsy.teloschopper.strategy.unload;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Constants;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class StratFire extends Strategy implements Job {
	
	private static SceneObject fire;

	@Override
	public boolean validate() {
		if (Dynamics.getState() == State.UNLOADING && Dynamics.getMethod() == UnloadMethod.BONFIRE) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		fire = SceneEntities.getNearest(Constants.FIRE_IDS);
		
		addLogs();
		
		lightFire();
	}
	
	private void lightFire() {
		if (!validFire() && !isAdding() && !Players.getLocal().isMoving()) {
			Inventory.getItem(Dynamics.getTree().getItemID()).getWidgetChild().interact("Light");
			Task.sleep(Random.nextInt(4000, 5000));
		}
	}

	private void addLogs() {
		if (validFire() && !isAdding()) {
			if (!fire.isOnScreen()) {
				Camera.turnTo(fire);
			}
			fire.interact("Add-logs");
			Task.sleep(Random.nextInt(3000, 4000));
		}
	}

	public static SceneObject getFire() {
		return fire;
	}
	
	private boolean isAdding() {
		return Players.getLocal().getPassiveAnimation() == Constants.BONFIRE_ANIMATION;
	}
	
	private boolean validFire() {
		if (fire != null) {
			return fire.validate() && StratWalk.isInChoppingArea(fire);
		}
		
		return false;
	}
}
