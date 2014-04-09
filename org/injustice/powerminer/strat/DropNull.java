package org.injustice.powerminer.strat;

import org.injustice.framework.Actionbar;
import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.UserSettings;
import org.injustice.powerminer.util.Var;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 07/06/13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class DropNull {
    public static void dropNull() {
        boolean actiondrop = false;
        Methods.debug("[DROP NR] Dropping", true);
        Timer t = new Timer(7500);
        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item.getId() == UserSettings.chosenRock.getInvId();
            }
        });
        while (Inventory.contains(UserSettings.chosenRock.getInvId()) &&
                SceneEntities.getNearest(Var.ROCK_FILTER) == null) {
            if (Widgets.get(640, 32).getChildId() == UserSettings.chosenRock.getInvId()) {
                actiondrop = true;
                Keyboard.sendKey('1');
                Task.sleep(150, 250);
                if (!t.isRunning()) {
                    Methods.debug("[DROP NR] Failed, retrying");
                    Actionbar.close();
                    Actionbar.open();
                    t.reset();
                    break;
                }
            } else {
                if (Inventory.getCount(UserSettings.chosenRock.getInvId()) == 28) {
                    Methods.debug("[DROP NR] Mousekeys", true);
                    useMousekeys(new Timer(15000), items);
                } else {
                    Methods.debug("[DROP NR] Regular", true);
                    for (Item i : items) {
                        i.getWidgetChild().interact("Drop");
                    }
                }
            }
        }
        if (Inventory.getCount(UserSettings.chosenRock.getInvId()) == 0 && actiondrop) {
            String s = "" + t.getRemaining() / 1000 + "." + (t.getRemaining() - t.getRemaining() / 1000);
            Methods.debug("[DROP NR] Done with " + s + " secs left");
        }
    }

    private static void useMousekeys(Timer t, Item[] items) {
        for (int col = 0; col < 4; col++) {
            int mouseX = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().x + Random.nextInt(-10, 10));
            int rowOffset = Random.nextInt(-10, 10);
            int mouseY = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().y + rowOffset);
            Mouse.move(mouseX, mouseY);
            for (int row = 0; row < 7; row++) {
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
