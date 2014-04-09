package net.nunnsy.teloschopper.utility.stats;

import net.nunnsy.teloschopper.utility.Dynamics;


public class Counter {
	public static void process(String s) {if (s.contains("You get some")) {
			Stats.addXP(Dynamics.getTree().getXP());
			Stats.logChopped();
		}
	}
}
