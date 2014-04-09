package org.injustice.wolfkiller.strat;

import org.injustice.wolfkiller.util.Constants;
import org.injustice.wolfkiller.util.WalkClass;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

import org.injustice.wolfkiller.util.Variables;


/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class WalkBank extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() && !Constants.BANK_TILE.isOnMap();
    }

    @Override
    public void execute() {
        Variables.status = WalkClass.walkPath(Constants.BANK_TILE) + " bank";
    }
}
