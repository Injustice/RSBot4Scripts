package org.injustice.agility.methods.misc;

import org.injustice.agility.util.Obstacle;
import org.powerbot.game.api.methods.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class ClientChecker implements Obstacle {
    @Override
    public boolean isValid() {
        return Game.getClientState() != Game.INDEX_MAP_LOADED;
    }

    @Override
    public int loop() {
        return 1000;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String state() {
        return "[IDLE] Waiting for client";
    }
}

