package org.injustice.ironminer.util.vars;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static int IRON_ORE_ID = 440; // iron ore
    public static int[] IRON_ROCK_ID = {2092, 2093, 5773, 5774, 5775, 9717, 9718, 9719, 11954, 11955, 11956, 14099, 14107, 14913, 14914,
            21281, 21282, 21283, 37307, 37308, 37309 };
    public static Area MINE_AREA = new Area(new Tile(0, 0, 0), new Tile(0, 0, 0));
    public static final int DAEMONHEIM_BANK_NPC = 9710;
    public static final Tile DAEMONHEIM_BANK_TILE = new Tile(3447, 3718, 0);
    public static final int WIZARDS_TOWER_CHEST =  70512;

    public static final int CLOSED_DOOR_ID = 1530; // closed
    public static final int OPEN_DOOR_ID = 1531;
}
