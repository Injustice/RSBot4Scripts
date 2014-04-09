package org.injustice.powerminer.strat;

import org.injustice.powerminer.util.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/06/13
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */
public class DropGranite {
    public static void dropGranite() {
        final int[] invIds = {6979, 6981, 6983};
        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                for (int i : invIds) {
                    if (item.getId() == i) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (Inventory.isFull() && Inventory.contains(invIds)) {
            if (Inventory.getCount(invIds) == 28) {
                Methods.debug("[GRANITE] MouseKeys", true);
                Timer t = new Timer(20000);
                useMousekeys(t, items);
            }
            else {
                for (Item i : items) {
                    Methods.debug("[GRANITE] Dropping", true);
                    i.getWidgetChild().interact("Drop");
                }
            }
        }
    }

    private static void useMousekeys(Timer t, Item[] items) {
        for (int col = 0; col < 4; col++) {
            int mouseX = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().x + Random.nextInt(-10, 10));
            int rowOffset = Random.nextInt(-10, 10);
            int mouseY = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().y + rowOffset);
            Mouse.move(mouseX, mouseY);
            for (int row = 0; row < 7; row++) {
                if (t.getRemaining() == 0) {
                    Methods.debug("[LIMESTONE] Failed MouseKeys, retrying");
                    return;
                }
                if (items[row * 4 + col].getId() != -1) {
                    Mouse.hop(mouseX, Inventory.getItemAt(row * 4 + col).getWidgetChild().getCentralPoint().y + rowOffset);
                    Task.sleep(85, 100);
                    Mouse.click(false);
                    while (!Menu.isOpen()) {
                        Task.sleep(85, 100);
                    }
                    String[] acts = Menu.getActions();
                    for (int a = 0; a < acts.length; a++) {
                        if (acts[a].contains("Drop")) {
                            Mouse.hop(mouseX, Menu.getLocation().y + 20 + a * 20);
                            Task.sleep(80, 100);
                            Mouse.click(true);
                        }
                    }
                }
            }
        }
    }
}
