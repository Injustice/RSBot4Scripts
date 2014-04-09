package net.nunnsy.teloschopper.strategy.walk;

import java.util.ArrayList;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.unload.StratBank;
import net.nunnsy.teloschopper.strategy.utility.WalkDestination;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class StratWalk extends Strategy implements Job {
	
	private static Tile startTile;
	private static int furthestTile;
	private static Tile[] boundingTiles;
	private static WalkDestination destination = WalkDestination.NONE;

	@Override
	public boolean validate() {
		
		if (Dynamics.getState() == State.WALKING && destination != WalkDestination.NONE) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		
		Path currentPath = null;
		
		switch (destination) {
		case TREE:
			currentPath = Walking.findPath(startTile);
			break;
			
		case BANK:
			currentPath = Walking.findPath(StratBank.getBankLocation().getArea().getCentralTile());
			break;
			
			
		case NONE:
			break;
		default:
			break;
		}
		
		if (!Walking.isRunEnabled() && Walking.getEnergy() > Random.nextInt(30, 50)) {
			Walking.setRun(true);
		}
		
		if (!Players.getLocal().isMoving()) {
			currentPath.traverse();
		
			Task.sleep(Random.nextInt(1000, 1500));
		}
	}
	
	public static void setStartTile(Tile t) {
		
		ArrayList<Tile> allTiles = new ArrayList<Tile>();
		ArrayList<Tile> boundary = new ArrayList<Tile>();
		
		for (int x = t.getX() - furthestTile; x < (furthestTile * 2) + t.getX(); x++) {
			for (int y = t.getY() - furthestTile; y < (furthestTile * 2) + t.getY(); y++) {
				allTiles.add(new Tile(x, y, 0));
			}
		}
		
		Tile[] possibleTiles = new Tile[allTiles.size()];
		
		for (int i = 0; i < allTiles.size(); i++) {
			possibleTiles[i] = allTiles.get(i);
		}
		
		for (Tile tile : possibleTiles) {
			if (Math.round(Calculations.distance(tile, t)) == furthestTile + 1) {
				boundary.add(tile);
			}
		}
		
		boundingTiles = new Tile[boundary.size()];
		
		for (int i = 0; i < boundary.size(); i++) {
			boundingTiles[i] = boundary.get(i);
		}
		
		startTile = t;
	}
	
	public static Tile getStartTile() {
		return startTile;
	}
	
	public static void setFurthestTile(int i) {
		furthestTile = i;
	}
	
	public static int getFurthestTile() {
		return furthestTile;
	}
	
	public static boolean isInChoppingArea(Locatable l) {
		return Calculations.distance(l, startTile) < furthestTile;
	}
	
	public static Tile[] getBoundingTiles() {
		return boundingTiles;
	}
	
	public static void setDestination(WalkDestination d) {
		destination = d;
	}
	
	public static Tile getClosestRandomTile(SceneObject se, int threshold) {
		Tile closestTile = null;
		
		ArrayList<Tile> allTiles = new ArrayList<Tile>();
		
		for (int x = se.getLocation().getX() - threshold; x < (threshold * 2) + se.getLocation().getX(); x++) {
			for (int y = se.getLocation().getY() - threshold; y < (threshold * 2) + se.getLocation().getY(); y++) {
				allTiles.add(new Tile(x, y, 0));
			}
		}
		
		closestTile = allTiles.get(0);
		
		for (int i = 0; i < allTiles.size(); i++) {
			if (Calculations.distanceTo(closestTile) > Calculations.distanceTo(allTiles.get(i))) {
				closestTile = allTiles.get(i);
			}
		}
		
		return closestTile;
	}
}
