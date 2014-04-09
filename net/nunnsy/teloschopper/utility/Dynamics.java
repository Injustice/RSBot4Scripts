package net.nunnsy.teloschopper.utility;

import net.nunnsy.teloschopper.strategy.utility.TreeType;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.game.Controls;
import net.nunnsy.teloschopper.utility.state.State;
import net.nunnsy.teloschopper.utility.stats.Stats;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.node.Item;

public class Dynamics {
	
	private static boolean GUIComplete = false;
	
	private static TreeType tree;
	private static UnloadMethod unload;
	private static State state = State.STARTING;
	
	private static boolean pickNests;
	private static boolean useUrns;
	private static boolean pickOrt;
	
	public static boolean isGUICompleted() {
		return GUIComplete;
	}
	
	public static void GUICompleted() {
		GUIComplete = true;
	}
	
	public static boolean isScriptStarted() {
		return StratWalk.getStartTile() != null && GUIComplete;
	}
	
	public static void setMethod(UnloadMethod u) {
		unload = u;
	}
	
	public static UnloadMethod getMethod() {
		return unload;
	}
	
	public static void setTree(TreeType l) {
		tree = l;
		Stats.setLogPrice(GeItem.lookup(tree.getItemID()).getPrice());
	}
	
	public static TreeType getTree() {
		return tree;
	}
	
	public static void setState(State s) {
		state = s;
	}
	
	public static State getState() {
		return state;
	}
	
	public static boolean inventoryClear() {
		
		Item[] items = null;
		
		if (Tabs.getCurrent() == Tabs.INVENTORY) {
			items = Inventory.getItems(new Filter<Item>() {
				@Override
				public boolean accept(Item item) {
					for (int id : Constants.HATCHET_IDS) {
						if (item.getId() == id) {
							return false;
						}
					}
					return true;
				}
			});
		}
		
		if (items.length == 0) {
			return true;
		}
		
		return false;
	}
	
	public static void setOptions(boolean nests, boolean urns, boolean ort) {
		pickNests = nests;
		useUrns = urns;
		pickOrt = ort;
		
		Constants.findKeepIDs();
	}
	
	public static boolean isPickingNests() {
		return pickNests;
	}
	
	public static boolean isUsingUrns() {
		return useUrns;
	}
	
	public static boolean isPickingOrt() {
		return pickOrt;
	}
	
	public static boolean controlsBusy() {
		return Controls.getCameraUse() || Controls.getMouseUse();
	}
}
