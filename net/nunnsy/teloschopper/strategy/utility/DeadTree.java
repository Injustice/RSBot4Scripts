package net.nunnsy.teloschopper.strategy.utility;

import org.powerbot.game.api.wrappers.node.SceneObject;

public class DeadTree {
	
	private SceneObject tree;
	private long timeChopped;

	public DeadTree(SceneObject entity, long time) {
		tree = entity;
		timeChopped = time;
	}
	
	public SceneObject getEntity() {
		return tree;
	}
	
	public long getTimeChopped() {
		return timeChopped;
	}
	
	public long getDeadTime() {
		return System.currentTimeMillis() - timeChopped;
	}
}
