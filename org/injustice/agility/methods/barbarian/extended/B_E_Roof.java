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
public class B_E_Roof extends Util implements Obstacle {
    private final int roof = 43532;
    private final Area roofArea = new Area(new Tile(2538, 3552, 2), new Tile(2542, 3554, 2));
    @Override
    public boolean isValid() {
        return in(roofArea) && idle();
    }

    @Override
    public int loop() {
        if (doObstacle("Slide", roof)) {
            new camera('e');
            new pitch(false);
            return 1000;
        }
        return 0;
    }

    @Override
    public String state() {
        return "Roof";
    }
}

