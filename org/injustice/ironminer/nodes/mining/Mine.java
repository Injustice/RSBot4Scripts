package org.injustice.ironminer.nodes.mining;

import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.methods.Sleeping;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class Mine extends Node {
    String action = "[MINING] ";
    @Override
    public boolean activate() {
        Methods.s(action + "Validating");
        return !Inventory.isFull() && !Dynamics.actionBarDrop && Dynamics.regularDrop;
    }

    @Override
    public void execute() {
        SceneObject door = SceneEntities.getNearest(Constants.CLOSED_DOOR_ID, Constants.OPEN_DOOR_ID);
        SceneObject rock = SceneEntities.getNearest(Constants.IRON_ROCK_ID);
        if (door.getId() == Constants.CLOSED_DOOR_ID && door != null) {
            Methods.s("[DOOR] Closed");
            if (door.isOnScreen()) {
                door.interact("Open");
                Methods.s("[DOOR] Opening");
                Sleeping.waitFor(door == null, 3000);
                Camera.setNorth();
            }
        }
        if (rock.isOnScreen()) {
            s("On screen");
            if (!Methods.isIdle()) {
                if (rock.interact("Mine", "Iron")) {
                    s("Interacting");
                    Sleeping.waitFor(Methods.isMining(), 2000);
                }
            } else {
                s("Waiting");
                Sleeping.waitFor(Methods.isIdle(), 2000);
            }
        } else {
            s("Turning");
            Camera.turnTo(rock);
            if (!rock.isOnScreen()) {
                s("Setting north");
                Camera.setNorth();
            }
        }
    }

    private void s(String status) {
        Methods.s(action + status);
    }
}
