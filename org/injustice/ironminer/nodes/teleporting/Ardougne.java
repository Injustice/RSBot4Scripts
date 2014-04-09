package org.injustice.ironminer.nodes.teleporting;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Ardougne extends Node {
    public boolean activate() {
        SceneObject rock = SceneEntities.getNearest(Constants.IRON_ROCK_ID);
        return Methods.inventEmpty() && Dynamics.bank() && rock == null;
    }

    public void execute() {
        SceneObject door = SceneEntities.getNearest(Constants.CLOSED_DOOR_ID, Constants.OPEN_DOOR_ID);
        String action = "[TELE] ";
        if (Tabs.EQUIPMENT.open()) {
            Methods.s(action + "Ardougne");
            Equipment.getItem(Equipment.Slot.CAPE).getWidgetChild().interact("Kandarin");
            do Sleeping.sleep(1000, 1250); while (Players.getLocal().getAnimation() != -1);
        }
        if (door.getId() == Constants.CLOSED_DOOR_ID && door != null) {
            Methods.s("[DOOR] Closed");
            if (door.isOnScreen()) {
                door.interact("Open");
                Methods.s("[DOOR] Opening");
                Sleeping.waitFor(door == null, 3000);
            }
        }
    }

}