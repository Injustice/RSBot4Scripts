package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class ChickenCounter extends LoopTask {
    int count = Inventory.getCount(Var.RAW_CHICKEN_ID);
    @Override
    public int loop() {
        if (count != Inventory.getCount(Var.RAW_CHICKEN_ID)) {
            Var.chickensPicked++;
            count = Inventory.getCount(Var.RAW_CHICKEN_ID);
        }
        return 100;
    }
}
