package org.injustice.wolfkiller.strat;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

import static org.injustice.wolfkiller.util.Constants.BANK_TILE;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class Banking extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() && BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        Bank.open();
        do sleep(500);
        while (!Bank.isOpen());
        Bank.depositInventory();
        sleep(500, 1000);
        Bank.close();
    }
}
