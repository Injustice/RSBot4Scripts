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
public class B_E_RunUpWall extends Util implements Obstacle {
    private final int wall = 43533;
    private final Area wallArea = new Area(new Tile(2536, 3542, 0), new Tile(2542, 3551, 0));
    @Override
    public boolean isValid() {
        return in(wallArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Run", wall)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state() {
        return "Running up wall";
    }
}

