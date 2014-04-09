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
public class B_LadderDown extends Util implements Obstacle {

    private final int ladderDown = 3205;
    private final Area ladderArea = new Area(new Tile(2532, 3546, 0), new Tile(2532, 2547, 0));

    @Override
    public boolean isValid() {
        return in(ladderArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Climb", ladderDown)) {
            return 1000;
        }
        return 0;
    }

    @Override
    public String state() {
        return "Ladder";
    }
}

