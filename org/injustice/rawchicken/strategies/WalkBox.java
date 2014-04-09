package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Condition;
import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WalkBox extends Node {

    @Override
    public boolean activate() {
        return Inventory.isFull() && !Var.BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        SceneObject depositbox = SceneEntities.getNearest(Var.DEPOSIT_BOX_ID);
        if (!Walking.isRunEnabled() && Walking.getEnergy() > 20) {   // if run isn't enabled and energy is more than 20
            Var.status = "Setting run";                                  // change status
            Walking.setRun(true);                                        // set run to on
        }
        if (Var.closedGate != null) {
            Var.status = "Gate is closed";
            if (Var.closedGate.isOnScreen())  {
                if (Var.closedGate.interact("Open")) {
                    sleep(500);
                    Var.status = "Opening gate";
                    Methods.waitFor(new Condition() {
                        @Override
                        public boolean validate() {
                            Var.status = "Waiting for gate";
                            return !Players.getLocal().isMoving();
                        }
                    }, 2500);
                }
            } else {
                Var.status = "Turning to gate";
                Camera.turnTo(Var.closedGate);
            }
        }
        Var.status = "Walking to box";
        Walking.findPath(Var.BANK_TILE).traverse();
        if (Calculations.distanceTo(depositbox) < Random.nextInt(15, 23)) {
            Camera.setPitch(2);
            Var.status = "Turning to box";
            Camera.turnTo(depositbox);
        }
    }
}

/* -- Thanks to GeemanKan for helping me with some of this -- */

