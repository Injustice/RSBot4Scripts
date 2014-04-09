package org.injustice.agility.methods.barbarian;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.wrappers.node.SceneObject;


/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class B_Pit extends Util implements Obstacle {
    private final int failedLadder = 32015;

    private boolean inPit() {
        SceneObject ladder = getObject(null, failedLadder);
        return ladder != null;
    }

    @Override
    public boolean isValid() {
        return inPit() && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Climb", failedLadder)) {
            return 1000;
        }
        return 250;
    }

    @Override
    public String state() {
        return "Climbing ladder";
    }
}
