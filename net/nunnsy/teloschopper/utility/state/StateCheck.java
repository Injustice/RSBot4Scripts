package net.nunnsy.teloschopper.utility.state;

import net.nunnsy.teloschopper.strategy.pickup.StratPickup;
import net.nunnsy.teloschopper.strategy.unload.StratBank;
import net.nunnsy.teloschopper.strategy.utility.WalkDestination;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.game.Controls;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

public class StateCheck {
	
	private static boolean unloading = false;

	public static void run() {
		if (Game.isLoggedIn()) {
			if (Controls.getCameraUse() || Controls.getMouseUse()) {
				Dynamics.setState(State.ANTIBAN);
			} else {
				checkUnloading();
				checkChopping();
				checkWalking();
				checkPicking();
			}
		} else {
			Dynamics.setState(State.WAITING);
		}
	}

	private static void checkPicking() {
		if (StratWalk.isInChoppingArea(Players.getLocal()) && StratPickup.itemsToBePicked()) {
			Dynamics.setState(State.PICKING);
		} else if (!unloading && StratWalk.isInChoppingArea(Players.getLocal())) {
			Dynamics.setState(State.CHOPPING);
		}
	}

	private static void checkWalking() {
		if (unloading && Dynamics.getMethod() == UnloadMethod.BANK && !StratBank.isAtBank()) {
			Dynamics.setState(State.WALKING);
			StratWalk.setDestination(WalkDestination.BANK);
		} else if (!StratWalk.isInChoppingArea(Players.getLocal()) && !unloading) {
			Dynamics.setState(State.WALKING);
			StratWalk.setDestination(WalkDestination.TREE);
		} else {
			StratWalk.setDestination(WalkDestination.NONE);
		}
	}

	private static void checkChopping() {
		if (!unloading && StratWalk.isInChoppingArea(Players.getLocal()) && Dynamics.getState() != State.PICKING) {
			Dynamics.setState(State.CHOPPING);
		}
	}

	private static void checkUnloading() {
		if (Inventory.isFull() && !unloading) {
			Dynamics.setState(State.UNLOADING);
			unloading = true;
		} else if (unloading && Dynamics.inventoryClear()) {
			Dynamics.setState(State.CHOPPING);
			unloading = false;
		} else if (unloading) {
			Dynamics.setState(State.UNLOADING);
		}
		
		if (StratBank.isAtBank() && Dynamics.getMethod() == UnloadMethod.BANK && unloading && !Dynamics.inventoryClear()) {
			Dynamics.setState(State.UNLOADING);
		}
	}
}
