package org.injustice.ironminer.nodes.dropping;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class Regular extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() && Dynamics.regularDrop;
    }

    @Override
    public void execute() {
        Methods.s("[DROPPING] Regular");
        for (int i = 0; i <= Inventory.getCount(Constants.IRON_ROCK_ID); i++) {
            Item[] items = Inventory.getItems(new Filter<Item>() {
                @Override
                public boolean accept(Item i) {
                    return i.getId() == Constants.IRON_ORE_ID;
                }
            });
            for (Item item : items) {
                item.getWidgetChild().interact("Drop");
            }
        }
    }
}
