package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Condition;
import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WalkPen extends Node {

    @Override
    public boolean activate() {
        return Methods.inventoryEmpty() && !Var.CHICKEN_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        if (!Walking.isRunEnabled() && Walking.getEnergy() > 20) {
            Var.status = "Setting run";
            Walking.setRun(true);
        }
        out:
        if (Var.closedGate != null) {
            Var.status = "Gate is closed";
            if (Calculations.distanceTo(Var.closedGate.getLocation()) <= Random.nextInt(15, 23)) {
                if (Var.closedGate.isOnScreen()) {
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
            } else {
                Var.status = "Walking to gate";
                Walking.findPath(Var.GATE_TILE).traverse();
            }
        }
        Var.status = "Walking into pen";
        Walking.findPath(Var.CHICKEN_TILE).traverse();
        if (GroundItems.getNearest(Var.RAW_CHICKEN_ID) != null &&
                Calculations.distanceTo(GroundItems.getNearest(Var.RAW_CHICKEN_ID)) < Random.nextInt(10, 15)) {
            Camera.turnTo(GroundItems.getNearest(Var.RAW_CHICKEN_ID));
            Camera.setPitch(true);
            GroundItems.getNearest(Var.RAW_CHICKEN_ID).interact("Take", "chicken");
            Var.chickensPicked++;
        }
    }
}
