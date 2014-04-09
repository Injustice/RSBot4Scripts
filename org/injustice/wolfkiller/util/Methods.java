package org.injustice.wolfkiller.util;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class Methods {
    public static String walkPath(Tile tile) {
        walkPath(null, tile, false);
        return "Walking to ";
    }

    public static void walkPath(Tile[] tileArray, Tile tile, boolean reverse) {
        if (reverse) Walking.newTilePath(tileArray).reverse().traverse();
        else {
            Path p = Walking.findPath(tile);
            p.traverse();
        }
    }

    public static boolean inventoryEmpty() {
        return Inventory.getCount() == 0;
    }
}
