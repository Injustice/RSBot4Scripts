package org.injustice.powerminer.strat;

import org.injustice.framework.Actionbar;
import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.enums.Rock;
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
 * Date: 02/06/13
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class DropLimestone {
    public static void dropLimestone() {
        final Item limestone = Inventory.getItem(Rock.LIMESTONE.getInvId());
        final Item[] limestones = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item.getId() == Rock.LIMESTONE.getInvId();
            }
        });
        if (limestone != null) {
            if (Inventory.getCount(Rock.LIMESTONE.getInvId()) == 28) {
                Methods.debug("[LIMESTONE] MouseKeys", true);
                Timer t = new Timer(20000);
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
                        if (limestones[row * 4 + col].getId() != -1) {
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
            } else {
                Timer t = new Timer(35000);
                Methods.debug("[LIMESTONE] Dropping", true);
                top:
                while (Inventory.contains(Rock.LIMESTONE.getInvId())) {
                    Actionbar.getSlotWithItem(Rock.LIMESTONE.getInvId()).getAvailableWidget().interact("Drop");
                    Task.sleep(350, 500);
                    if (!t.isRunning()) {
                        Methods.debug("[LIMESTONE] Failed, retrying");
                        Actionbar.close();
                        Actionbar.open();
                        t.reset();
                        break top;
                    }
                }
                if (Inventory.getCount(Rock.LIMESTONE.getInvId()) == 0)  {
                    String s = "" + t.getRemaining() / 1000 + "." + (t.getRemaining() - t.getRemaining() / 1000);
                    Methods.debug("[LIMESTONE] Done with " + s + " secs left");
                }
            }
        }
    }
}
