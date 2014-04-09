package org.injustice.wolfkiller.strat;

import org.injustice.wolfkiller.util.Constants;
import org.injustice.wolfkiller.util.Methods;
import org.injustice.wolfkiller.util.Variables;
import org.powerbot.core.script.job.state.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class WalkMountain extends Node {
    @Override
    public boolean activate() {
        return Methods.inventoryEmpty() && Constants.BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        Variables.status = Methods.walkPath(Constants.WOLF_TILE) + " wolves";
    }
}
