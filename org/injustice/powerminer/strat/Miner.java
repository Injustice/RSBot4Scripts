package org.injustice.powerminer.strat;

import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.Var;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.powerminer.util.Methods.debug;
import static org.injustice.powerminer.util.Methods.isOnScreen;
import static org.injustice.powerminer.util.Var.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 30/05/13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class Miner {
    public static void mine() {
        final SceneObject rock = SceneEntities.getNearest(ROCK_FILTER);
        final SceneObject rockNoRadius = SceneEntities.getNearest(ROCK_NO_RADIUS_FILTER);
        if (rock != null) {
            debug((Var.debug ? "[MINE] Rock found - id (" + rock.getId() + ") - loc " + rock.getLocation() : null));
            if (isOnScreen(rock)) {
                if (rock.interact("Mine")) {
                    Methods.debug("[MINE] Mining - " + (28 - Inventory.getCount())+ " left", true);
                    Timer t = new Timer(7500);
                    while (Players.getLocal().getAnimation() == -1 && t.isRunning()) {
                        Task.sleep(500);
                    }
                    t.reset();
                    while (Players.getLocal().getAnimation() != -1 && t.isRunning()) {
                        Task.sleep(250);
                    }

                }
            } else {
                debug(Var.debug ? "[MINE] Change of camera needed to mobile angle " + Camera.getMobileAngle(rock) : "[MINE] Turning to rock");
                Camera.turnTo(rock);
                for (int i = 0; i < 15 && !rock.isOnScreen(); i++) {
                    Task.sleep(250, 300);
                }
                debug(Var.debug ? "[MINE] Successfully changed camera to yaw " + Camera.getYaw() : null);
            }
        } else if (rockNoRadius != null) {
            if (Calculations.distance(rockNoRadius, startTile) <= 11 && Game.getClientState() == Game.INDEX_MAP_LOADED) {
                debug("[MINE] Increase radius", true);
            }
        } else {
            Fail.fail();
        }
    }
}
