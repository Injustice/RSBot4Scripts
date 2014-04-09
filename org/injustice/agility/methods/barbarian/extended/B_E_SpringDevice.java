package org.injustice.agility.methods.barbarian.extended;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class B_E_SpringDevice extends Util implements Obstacle {

    private final int springDevice = 43587;
    private final Area springArea = new Area(new Tile(2532, 3545, 3), new Tile(2536, 3547, 3));

    @Override
    public boolean isValid() {
        return in(springArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Fire", springDevice)) {
            return 1000;
        }
        return 0;
    }

    @Override
    public String state() {
        return "Spring device";
    }
}

