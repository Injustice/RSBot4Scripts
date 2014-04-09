package net.nunnsy.teloschopper.strategy.skill;

import java.util.ArrayList;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.utility.DeadTree;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Dynamics;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class StratTreeTrack extends Strategy implements Job {
	
	private SceneObject[] aliveTrees;
	private static ArrayList<DeadTree> choppedTrees = new ArrayList<DeadTree>();

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void run() {
		checkTrees();
	}
	
	private void checkTrees() {
		
		for (int t = 0; t < choppedTrees.size(); t++) {
			SceneObject loadedObject = SceneEntities.getAt(choppedTrees.get(t).getEntity());
			
			if (choppedTrees.get(t).getDeadTime() > 120000) {
				choppedTrees.remove(t);
				break;
			}
			
			for (int id : Dynamics.getTree().getSceneID()) {
				if (loadedObject.getId() == id) {
					choppedTrees.remove(t);
					break;
				}
			}
		}
		
		if (aliveTrees != null) {
			for (SceneObject tree : aliveTrees) {
				if (!tree.validate()) {
					choppedTrees.add(new DeadTree(tree, System.currentTimeMillis()));
				}
			}
		}
		
		aliveTrees = getLoadedTrees();
	}
	
	private SceneObject[] getLoadedTrees() {
		SceneObject[] trees;
		
		trees = SceneEntities.getLoaded(new Filter<SceneObject>() {
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
		
		return trees;
	}
	
	public static SceneObject getNextRespawn() {
		DeadTree nextSpawn = choppedTrees.get(0);
		
		for (DeadTree tree : choppedTrees) {
			if (nextSpawn.getTimeChopped() > tree.getTimeChopped()) {
				nextSpawn = tree;
			}
		}
		
		return nextSpawn.getEntity();
	}
	
	public static ArrayList<DeadTree> getDeadTrees() {
		return choppedTrees;
	}
}
