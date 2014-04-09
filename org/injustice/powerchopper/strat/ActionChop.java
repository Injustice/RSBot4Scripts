package org.injustice.powerchopper.strat;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 17/03/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */


import org.injustice.framework.Actionbar;
import org.injustice.powerchopper.util.Utilities;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.powerchopper.util.Variables.*;


public class ActionChop extends Node {

    @Override
    public boolean activate() {
        return guiDone && actionDrop && !doBonfires && !normalChop;
    }

    @Override
    public void execute() {
        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                return i.getId() == log;
            }
        });
        if (Inventory.contains(log)) {
            top:
            if (items != null) {
                if (Actionbar.getSlotWithItem(log).isAvailable()) {
                    redrop:
                    if (Actionbar.isOpen()) {
                        int i = Inventory.getCount(log);
                        for (int in = 0; in <= i && Inventory.contains(log); i++) {
                            status = "[CHOPPING] Dropping";
                            Actionbar.getSlotWithItem(log).getAvailableWidget().interact("Drop");
                            sleep(350, 450);
                        }
                        if (Inventory.contains(log)) {
                            break redrop;
                        } else {
                            status = "[CHOPPING] Sleeping";
                            if (doAntiban) Utilities.antiban();
                        }
                    } else {
                        Actionbar.open();
                    }
                } else {
                    status = "[ACTIONBAR] Log not in slot";
                    Mouse.move(Inventory.getItem(log).getWidgetChild().getCentralPoint());
                    Mouse.drag(Actionbar.Slot.ONE.getAvailableWidget().getCentralPoint());
                    break top;
                }
            }
        }

        sleep(1000);

        SceneObject treeToCut = SceneEntities.getNearest(tree);
        if (Players.getLocal().getAnimation() == -1) {
            if (!Players.getLocal().isMoving()) {
                if (Players.getLocal().isIdle()) {
                    if (!Inventory.contains(log)) {
                        if (treeToCut != null) {
                            if (Utilities.isOnScreen(treeToCut)) {
                                if (treeToCut.interact("Chop")) {
                                    status = "[CHOPPING] Interacting";
                                    sleep(1500, 2000);
                                    do {
                                        sleep(250);
                                        if (doAntiban) Utilities.antiban();
                                        status = "[CHOPPING] Sleeping";
                                    }
                                    while ((Players.getLocal().isMoving() || Players.getLocal()
                                            .getAnimation() != -1) && !Inventory.contains(log));
                                }
                            } else {
                                status = "[CHOPPING] Turning";
                                Camera.turnTo(treeToCut);
                            }
                        } else
                            sleep(250, 300);
                    }
                }
            } else sleep(250, 300);
        } else sleep(250, 300);
    }
}




