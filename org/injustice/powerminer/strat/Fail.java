package org.injustice.powerminer.strat;

import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.Var;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.node.SceneEntities;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 01/06/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class Fail {
    public static void fail() {
        if (SceneEntities.getNearest(Var.ROCK_NO_RADIUS_FILTER) != null) {
            Methods.debug("[FAIL] No rocks found in radius", true);
            Task.sleep(2000, 2500);
        } else {
            Methods.debug("[FAIL] No rocks found", true);
            Task.sleep(1000, 1500);
        }
    }
}
