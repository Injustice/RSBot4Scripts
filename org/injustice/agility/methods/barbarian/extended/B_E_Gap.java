package org.injustice.agility.methods.barbarian.extended;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class B_E_Gap extends Util implements Obstacle {
    private final int gap = 43531;
    private boolean atGap() {
        return Players.getLocal().getLocation() == new Tile(2536, 3553, 3);
    }
    @Override
    public boolean isValid() {
        return atGap();
    }

    @Override
    public int loop() {
        if (doObstacle("Jump", gap)) {
            return 1000;
        }
        return 0;
    }

    @Override
    public String state() {
        return "Gap";
    }
}
