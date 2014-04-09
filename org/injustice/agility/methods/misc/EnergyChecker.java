package org.injustice.agility.methods.misc;

import org.injustice.agility.util.Obstacle;
import org.powerbot.game.api.methods.Walking;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class EnergyChecker implements Obstacle {
    @Override
    public boolean isValid() {
        return !Walking.isRunEnabled();
    }

    @Override
    public int loop() {
        Walking.setRun(true);
        return 1000;
    }

    @Override
    public String state() {
        return "[RUN] Enabling";
    }
}

