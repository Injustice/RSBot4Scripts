package org.injustice.powerchopper.strat;

import org.injustice.framework.Actionbar;
import org.injustice.powerchopper.util.Utilities;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;

import java.awt.*;

import static org.injustice.powerchopper.util.Utilities.waitForWidget;
import static org.injustice.powerchopper.util.Variables.*;

public class AddLogs extends Node {
    public boolean activate() {
        return Inventory.isFull() && doBonfires;
    }

    public void execute() {
        Inventory.getItem(log).getWidgetChild().interact("Light");
        status = "[BONFIRE] Lighting log";
        do sleep(1000);
        while (Players.getLocal().getAnimation() == 16700);

        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                return i.getId() == log;
            }
        });
        if (!Actionbar.isOpen())
            Actionbar.open();
        top:
        if (items != null) {
            if (Actionbar.getSlotStateAt(0) == Actionbar.SlotState.ITEM) {
                if (Actionbar.isOpen()) {
                    status = "[ACTIONBAR] Clicking";
                    Actionbar.getSlot(0).getAvailableWidget().interact("Craft");
                    status = "[ACTIONBAR] Waiting";
                    Widget wc = new Widget(1179);
                    waitForWidget(wc.getChild(0));
                    sleep(500);
                    while (!Utilities.isOnScreen(wc.getChild(0))) {
                        sleep(500);
                    }
                    status = "[BONFIRE] Clicking";
                    Point p = Widgets.get(1179, 38).getCentralPoint();
                    Mouse.move(p.x + (Random.nextInt(5, 10)), p.y + Random.nextInt(20, 60));
                    Mouse.click(true);
                    status = "[BONFIRE] Clicked";
                    sleep(3000);
                    SceneObject fire = SceneEntities
                            .getNearest(BONFIRES_ID);
                    while (fire.validate() && Inventory.getCount(log) != 0) {
                        sleep(500);
                        if (doAntiban) Utilities.antiban();
                        status = "[BONFIRE] Sleeping";
                    }
                } else {
                    Actionbar.open();
                    status = "[ACTIONBAR] Readying";
                }

            } else {
                status = "[ACTIONBAR] Log not in slot";
                Mouse.move(Inventory.getItem(log).getWidgetChild().getCentralPoint());
                Mouse.drag(Actionbar.Slot.ONE.getAvailableWidget().getCentralPoint());
                break top;
            }
        }
    }
}