package org.injustice.fighter.node.loot;

import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.framework.Job;
import org.injustice.framework.Strategy;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/05/13
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class CustomLoot extends Strategy implements Job {
    @Override
    public boolean activate() {
        return GroundItems.getNearest(convertIntegers(UserSettings.selectedLoot)) != null
                &&
                Players.getLocal().getInteracting() == null
                &&
                !Players.getLocal().isMoving()
                &&
                !Util.isUnderAttack();
    }

    @Override
    public void execute() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}
