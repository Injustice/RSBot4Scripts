package org.injustice.powerminer.strat;

import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.Var;
import org.injustice.powerminer.util.enums.Rock;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 30/05/13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class DropGems {
    public static void dropGems() {
        final Item gem = Inventory.getItem(Var.gems);
        final Item limestone = Inventory.getItem(Rock.LIMESTONE.getInvId());
        if (gem != null) {
            Methods.debug(Var.debug ? "[GEM] Dropping " + gem.getName() + " - ID - " + gem.getId() : "[GEM] Dropping");
            if (gem.getWidgetChild().interact("Drop")) {
                Task.sleep(1000, 1200);
                Var.gemsDropped++;
            }
        }
    }
}
