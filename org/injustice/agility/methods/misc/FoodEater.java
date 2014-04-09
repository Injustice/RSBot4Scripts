package org.injustice.agility.methods.misc;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Var;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class FoodEater implements Obstacle {
    @Override
    public boolean isValid() {
        return Var.Settings.eat && Players.getLocal().getHealthPercent() <= 50;
    }

    @Override
    public int loop() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String state() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

