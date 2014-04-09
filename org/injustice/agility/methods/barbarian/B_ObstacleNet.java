package org.injustice.agility.methods.barbarian;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class B_ObstacleNet extends Util implements Obstacle {
    private final int obstacleNet = 20211;
    private final Area obstacleArea = new Area(new Tile(2539, 3542, 0), new Tile(2542, 3548, 0));
    @Override
    public boolean isValid() {
        return in (obstacleArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Climb", obstacleNet)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state() {
        return "Obstacle net";
    }
}
