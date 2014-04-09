package org.injustice.ironminer.nodes.mining;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 13/04/13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class M1D1 extends Node {
    @Override
    public boolean activate() {
        return Dynamics.mineOneDropOne;
    }

    @Override
    public void execute() {
        String action = "[M1D1] ";
        SceneObject rock = SceneEntities.getNearest(Constants.IRON_ROCK_ID);
        if (Methods.inventEmpty()) {
            if (Players.getLocal().getAnimation() == -1 &&
                    !Players.getLocal().isMoving() && rock != null) {
                if (rock.isOnScreen()) {
                    if (rock.interact("Mine", "Iron")) {
                        Methods.s(action + "Interacting");
                        sleep(1000);  // takes half a second to turn
                        Methods.s(action + "Mining");
                        do sleep(100);
                        while (Players.getLocal().getAnimation() != -1 && Players.getLocal().isMoving());
                        while (Inventory.contains(Constants.IRON_ORE_ID)) {
                            Keyboard.sendKey('1');
                            Sleeping.sleep(500, 750);
                            Methods.s(action + "Dropping ore");
                        }
                    }
                } else {
                    Methods.s(action + "Turning");
                    Camera.turnTo(rock);
                }
            } else {
                Methods.s(action + "Waiting for idle");
                Sleeping.waitFor(Players.getLocal().isIdle(), 2000);
            }
        } else {
            while (Inventory.contains(Constants.IRON_ORE_ID)) {
                Keyboard.sendKey('1');
                Sleeping.sleep(250, 300);
                Methods.s(action + "Dropping");
            }
        }
    }
}

