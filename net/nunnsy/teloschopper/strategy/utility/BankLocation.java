package net.nunnsy.teloschopper.strategy.utility;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public enum BankLocation {

	EDGEVILLE("Edgeville", new Area(new Tile(3091, 3497, 0), new Tile(3095, 3488, 0))),
	VARROCK_EAST("Varrock East", new Area(new Tile(3250, 3423, 0), new Tile(3257, 3419, 0))),
	VARROCK_WEST("Varrock West", new Area(new Tile(3181, 3446, 0), new Tile(3190, 3434, 0))),
	FALADOR_EAST("Falador East", new Area(new Tile(3010, 3357, 0), new Tile(3015, 3354, 0))),
	FALADOR_WEST("Falador West", new Area(new Tile(2945, 3372, 0), new Tile(2949, 3387, 0))),
	DRAYNOR_VILLAGE("Draynor Village", new Area(new Tile(3091, 3246, 0), new Tile(3095, 3240, 0))),
	CATHERBY("Catherby", new Area(new Tile(2807, 3442, 0), new Tile(2811, 3439, 0))),
	SEERS_VILLAGE("Seers' Village", new Area(new Tile(2722, 3494, 0), new Tile(2729, 3490, 0))),
	TAVERLY("Taverly", new Area(new Tile(2874, 3418, 0), new Tile(2877, 3415, 0)));
	
	String name;
	Area area;
	
	BankLocation(String name, Area area) {
		this.name = name;
		this.area = area;
	}
	
	public String getName() {
		return name;
	}
	
	public Area getArea() {
		return area;
	}
}
