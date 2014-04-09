package org.injustice.ironminer.nodes.banking;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.Item;

public class Daemonheim extends Node {
    public boolean activate() {
        return Dynamics.bankAtDaemonheim && Inventory.isFull() && Game.isLoggedIn();
    }

    public void execute() {
        String action = "[TELE] ";
        NPC banker = NPCs.getNearest(Constants.DAEMONHEIM_BANK_NPC);
        if (Calculations.distanceTo(banker) > 25) {
            if (Tabs.EQUIPMENT.open()) {
                Methods.s(action + "Daemonheim");
                Equipment.getItem(Equipment.Slot.RING).getWidgetChild().interact("Teleport");
                Sleeping.sleepFirst(1500, Methods.isIdle(), action);
            }
        } else {
            Methods.s("[BANK] Walking");
            Path p = Walking.findPath(Constants.DAEMONHEIM_BANK_TILE);
            p.traverse();
        }

        if (banker != null) {
            if (banker.isOnScreen()) {
                if (banker.interact("Bank")) {
                    Methods.s("[BANK] Interacting");
                    deposit();
                }
            } else {
                Methods.s("[BANK] Turning");
                Camera.turnTo(banker);
            }
        } else {
            Sleeping.sleepFirst(500, banker != null, "[BANK]");
        }

    }

    private void deposit() {
        for (Item i : Inventory.getItems()) {
            if (Dynamics.pickaxeWielded) {
                if (!i.getName().toLowerCase().contains("pick")) {
                    Bank.deposit(i.getId(), Bank.Amount.ALL);
                    Sleeping.sleepFirst(1500, !Inventory.contains(i.getId()), "[BANKING]");
                }
            } else {
                Bank.depositInventory();
                Sleeping.sleepFirst(1500, !Inventory.contains(i.getId()), "[BANKING]");
            }
        }
    }

    private boolean inventPick() {
        for (Item i : Inventory.getItems()) {
            if (i.getName().toLowerCase().contains("pick")) {
                return true;
            }
        } return false;
    }
}