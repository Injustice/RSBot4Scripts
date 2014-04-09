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
public class B_EntrancePipe extends Util implements Obstacle {
     private final int entrancePipe = 20210;
     private final Area entranceArea = new Area(new Tile(2546, 3561, 0), new Tile(2555, 3564, 0));

    @Override
    public boolean isValid() {
        return in(entranceArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Squeeze", entrancePipe)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state() {
        return "Entrance pipe";
    }
}
