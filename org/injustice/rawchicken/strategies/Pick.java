package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Condition;
import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class Pick extends Node {

    @Override
    public boolean activate() {
        return !Inventory.isFull() && Var.CHICKEN_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        Var.status = "Pickin' chicken";
        GroundItem chicken = GroundItems.getNearest(Var.RAW_CHICKEN_ID);
        if (chicken != null) {
            if (Var.CHICKEN_AREA.contains(chicken.getLocation())) {
                if (chicken.isOnScreen()) {
                    if (chicken.interact("Take", "chicken")) {
//                        Var.chickensPicked++;
                        if (Calculations.distanceTo(chicken) < 1) {
                            sleep(500);
                        } else {
                            sleep(250);
                            Methods.waitFor(new Condition() {
                                @Override
                                public boolean validate() {
                                    return !Players.getLocal().isMoving();
                                }
                            }, 1000);
                        }
                    }
                } else Camera.turnTo(chicken);
            }
        } else {
            Var.status = "No chicken";
            Methods.waitFor(new Condition() {
                @Override
                public boolean validate() {
                    return GroundItems.getNearest(Var.RAW_CHICKEN_ID) != null;
                }
            }, 5000);
        }
    }
}
