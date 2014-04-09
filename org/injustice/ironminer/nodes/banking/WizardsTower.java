package org.injustice.ironminer.nodes.banking;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;

public class WizardsTower extends Node {
    public boolean activate() {
        return Inventory.isFull() && Dynamics.bankAtWizardsTower;
    }

    public void execute() {
        final WidgetChild depositInvent = Widgets.get(11, 19);
        final WidgetChild closeBox = Widgets.get(11, 8);
        final SceneObject box = SceneEntities.getNearest(Constants.WIZARDS_TOWER_CHEST);
        if (box == null) {
            if (Tabs.EQUIPMENT.open()) {
                Methods.s("[TELE] Wizards Tower");
                Equipment.getItem(Equipment.Slot.HELMET).getWidgetChild().interact("Teleport");
                do Sleeping.sleep(1000, 1250); while (Players.getLocal().getAnimation() != -1);
            }
        }
        Methods.s("[BANK] Stuck");
        if (box.isOnScreen()) {
            Methods.s("here");
            if (box.interact("Deposit")) {
                do {
                    Sleeping.sleep(500, 750);
                    Methods.s("[BANK] Waiting");
                } while (Players.getLocal().isMoving());
                if (depositInvent.isOnScreen()) {
                    if (depositInvent.interact("Deposit")) {
                        Sleeping.sleep(1000, 1500);
                        if (closeBox.isOnScreen()) {
                            Point p = closeBox.getCentralPoint();
                            Mouse.click(p.x + Random.nextInt(-5, 5), p.y +
                                    Random.nextInt(-5, 5), true);
                        }
                    }
                }
            }
        }
    }

}