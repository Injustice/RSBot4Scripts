package org.injustice.ironminer.nodes.dropping;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class MouseKeys extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() && Dynamics.mousekeysDrop && Game.isLoggedIn();
    }

    @Override
    public void execute() {
        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                return i.getId() == Constants.IRON_ORE_ID;
            }
        });
        Methods.s("[DROPPING] Mousekeys");
        for (int col = 0; col < 4; col++) {
            int mouseX = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().x + Random.nextInt(-10, 10));
            int rowOffset = Random.nextInt(-10, 10);
            int mouseY = (Inventory.getItemAt(col).getWidgetChild().getCentralPoint().y + rowOffset);
            Mouse.move(mouseX, mouseY);
            for (int row = 0; row < 7; row++) {
                if (items[row * 4 + col].getId() != -1) {
                    Mouse.hop(mouseX, Inventory.getItemAt(row * 4 + col).getWidgetChild().getCentralPoint().y + rowOffset);
                    sleep(85, 100);
                    Mouse.click(false);
                    while (Menu.isOpen() == false) {
                        sleep(85, 100);
                    }
                    String[] acts = Menu.getActions();
                    for (int a = 0; a < acts.length; a++) {
                        if (acts[a].contains("Drop")) {
                            Mouse.hop(mouseX, Menu.getLocation().y + 20 + a * 20);
                            sleep(80, 100);
                            Mouse.click(true);
                        }
                    }
                }
            }
        }
    }
}