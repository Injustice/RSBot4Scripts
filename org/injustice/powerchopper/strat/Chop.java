package org.injustice.powerchopper.strat;

import org.injustice.framework.Actionbar;
import org.injustice.powerchopper.util.Utilities;
import org.injustice.powerchopper.util.Variables;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.powerchopper.util.Variables.*;

public class Chop extends Node {

    @Override
    public boolean activate() {
        return !Inventory.isFull() && guiDone && normalChop && Players.getLocal().getAnimation() == -1 && Players.getLocal().isIdle();
    }

    @Override
    public void execute() {
        SceneObject tree = SceneEntities.getNearest(Variables.tree);
        if (Players.getLocal().getAnimation() == -1) {
            if (!Players.getLocal().isMoving()) {
                if (tree != null) {
                    if (tree.getLocation().isOnMap()) {
                        if (Utilities.isOnScreen(tree)) {
                            if (tree.interact("Chop")) {
                                status = ("[CHOPPING] Interacting");
                                sleep(1000, 1200);
                                if (doAntiban) Utilities.antiban();
                                while (Players.getLocal().getAnimation() == 867
                                        || Players.getLocal().isMoving()) {
                                    sleep(100, 150);
                                    if (doAntiban) Utilities.antiban();
                                    status = ("[CHOPPING] Sleeping");
                                }
                            }
                        } else {
                            Camera.turnTo(tree);
                            status = ("[CHOPPING] Turning");
                        }
                    } else {
                        Walking.walk(tree.getLocation());
                        status = "[CHOPPING] Walking to tree";
                        do sleep(1000);
                        while (Players.getLocal().isMoving());

                    }
                }
            }
        } else
            sleep(250, 300);

        if (Actionbar.isOpen() && !doBonfires)
            Actionbar.close();
    }
}
