package org.injustice.ironminer.nodes.mining;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class PowerMine extends Node {
    SceneObject rock = SceneEntities.getNearest(Constants.IRON_ROCK_ID);
    String action = "[PMINING] ";
    public boolean activate() {
        return Dynamics.actionBarDrop;
    }

    public void execute() {
        if (Inventory.isFull()) {
            while (Inventory.contains(Constants.IRON_ORE_ID)) {
                s("Dropping");
                Keyboard.sendKey('1');
                Sleeping.sleep(200, 250);
            }
        } else {
            rock = SceneEntities.getNearest(Constants.IRON_ORE_ID);
            if (rock != null) {
                if (rock.isOnScreen()) {
                    if (!Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1) {
                        if (rock.interact("Mine", "Iron")) {
                            s("Interacting");
                            do Sleeping.sleep(500, 1000);
                            while (Players.getLocal().isMoving() ||
                                    Players.getLocal().getAnimation() != -1);
                        }
                    } else {
                        s("Waiting for idle");
                        do Sleeping.sleep(500, 1000);
                        while (Players.getLocal().isMoving() ||
                                Players.getLocal().getAnimation() != -1);
                    }
                } else {
                    s("Turning to rock");
                    Camera.turnTo(rock);
                }
            }
        }
    }

    private void s(String status) {
        Methods.s(action + status);
    }
}