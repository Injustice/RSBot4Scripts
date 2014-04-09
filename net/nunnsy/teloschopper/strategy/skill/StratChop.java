package net.nunnsy.teloschopper.strategy.skill;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Constants;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class StratChop extends Strategy implements Job {
	
	private static SceneObject currentTree;
	private static SceneObject nextTree;

	@Override
	public boolean validate() {
		if (Dynamics.getState() == State.CHOPPING) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		
		currentTree = findCurrentTree();
		
		if (currentTree != null) {
			
			nextTree = findNextTree();
			
			chopCurrentTree();
			
			if (nextTree != null) {
				chopNextTree();
				
				moveCursor();
			}
		} else {
			if (StratTreeTrack.getDeadTrees().size() > 0) {
				if (Calculations.distanceTo(StratTreeTrack.getNextRespawn().getLocation()) > 5 && !Players.getLocal().isMoving()) {
					Walking.walk(StratWalk.getClosestRandomTile(StratTreeTrack.getNextRespawn(), 2));
					Task.sleep(Random.nextInt(500, 1000));
				}
			}
		}
	}

	private void chopCurrentTree() {
		if (!isChopping() && !Players.getLocal().isMoving()) {
			if (currentTree.validate()) {
				if (!currentTree.isOnScreen()) {
					turnTo(currentTree);
					if (!currentTree.isOnScreen() && !Players.getLocal().isMoving()) {
						Walking.walk(StratWalk.getClosestRandomTile(currentTree, 2));
					}
				}
				currentTree.interact("Chop");
				Task.sleep(Random.nextInt(1000, 2000));
			}
		}
	}
	
	private boolean isChopping() {
		for (int animation : Constants.CHOPPING_ANIMATIONS) {
			if (Players.getLocal().getAnimation() == animation) {
				return true;
			}
		}
		return false;
	}

	private void chopNextTree() {
		if (!currentTree.validate() && nextTree.validate() && !Players.getLocal().isMoving()) {
			if (!nextTree.isOnScreen()) {
				turnTo(nextTree);
			}
			nextTree.interact("Chop");
			Task.sleep(Random.nextInt(1000, 2000));
		}
	}

	private void moveCursor() {
		if (nextTree.validate()) {
			if (!nextTree.isOnScreen()) {
				turnTo(nextTree);
			}
			
			if (!nextTree.contains(Mouse.getLocation())) {
				Mouse.move(nextTree.getCentralPoint(), 5, 10);
			}
		} else if (currentTree.validate()) {
			if (!currentTree.contains(Mouse.getLocation())) {
				Mouse.move(currentTree.getCentralPoint(), 5, 10);
			}
		}
	}

	private SceneObject findNextTree() {
		SceneObject tree;
		
		tree = SceneEntities.getNearest(new Filter<SceneObject>() {
			@Override
			public boolean accept(SceneObject object) {
				for (int ID : Dynamics.getTree().getSceneID()) {
					if (object.getId() == ID && !object.getLocation().equals(currentTree.getLocation()) && StratWalk.isInChoppingArea(object)) {
						return true;
					}
				}
				return false;
			}
		});
		
		return tree;
	}
	
	private SceneObject findCurrentTree() {
		SceneObject tree;
		
		tree = SceneEntities.getNearest(new Filter<SceneObject>() {
			@Override
			public boolean accept(SceneObject object) {
				for (int ID : Dynamics.getTree().getSceneID()) {
					if (object.getId() == ID && StratWalk.isInChoppingArea(object)) {
						return true;
					}
				}
				return false;
			}
		});
		
		return tree;
	}
	
	private void turnTo(SceneObject s) {
		Camera.turnTo(s, 150);
	}
	
	public static SceneObject getCurrentTree() {
		return currentTree;
	}
	
	public static SceneObject getNextTree() {
		return nextTree;
	}
}
