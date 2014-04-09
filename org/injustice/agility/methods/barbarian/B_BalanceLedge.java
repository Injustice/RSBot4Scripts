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
public class B_BalanceLedge extends Util implements Obstacle {
    private final int balanceLedge = 2302;
    private final Area balanceArea = new Area(new Tile(2536, 3545, 0), new Tile(2537, 3547, 0));

    @Override
    public boolean isValid() {
        return in(balanceArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Walk", balanceLedge)) {
            return 1000;
        }
        return 0;
    }

    @Override
    public String state() {
        return "Balance ledge";
    }
}

