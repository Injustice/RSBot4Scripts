package org.injustice.agility.methods.barbarian.extended;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class B_E_BalanceBeam extends Util implements Obstacle {
    private final int balancingBeam = 43527;
    private final Area balancingBeamArea = new Area(new Tile(2530, 3553, 3), new Tile(2533, 3554, 3));
    @Override
    public boolean isValid() {
        return in(balancingBeamArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Cross", balancingBeam)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state() {
        return "Balance beam";
    }
}

