package org.injustice.agility.methods.barbarian;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;


/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class B_RopeSwing extends Util implements Obstacle {

    private final int ropeSwing = 43526;
    private final Area ropeArea = new Area(new Tile(2543, 3551, 0), new Tile(2555, 3559, 0));
    @Override
    public boolean isValid () {
        return in(ropeArea) && idle();
    }

    @Override
    public int loop () {
        if (doObstacle("Swing", ropeSwing)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state () {
        return "Rope swing";
    }
}
