package org.injustice.fighter.node;

import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Food;
import org.injustice.framework.Job;
import org.injustice.framework.Strategy;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class Eater extends Strategy implements Job {

    public boolean activate() {
        return Inventory.contains(Food.ALL.getIds())
                &&
                Util.getHpPercent() <= UserSettings.eatPercent;
    }

    public void execute() {
        Item food = Inventory.getItem(Food.ALL.getIds());
        if (food != null) {
            if (food.getWidgetChild().interact("Eat")) {
                Var.status = "[EAT] " + food.getName();
                Util.debug();
            }
        }
    }

}