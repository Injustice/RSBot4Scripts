package org.injustice.fighter.node.failsafes;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.framework.Job;
import org.injustice.framework.Strategy;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 01/05/13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class FailsafeDoor extends Strategy implements Job {
    @Override
    public boolean activate() {
        return Util.isReady()
                &&
                Var.cannotReach;
    }

    @Override
    public void execute() {
        Var.status = "[GATE]";
        Util.debug();
        SceneObject gate = SceneEntities.getNearest(16089, 16090);
        if (gate != null) {
            Var.status = "[GATE] Opening";
            Util.debug();
            if (Util.isOnScreen(gate)) {
                if (gate.interact("Open")) {
                    Var.cannotReach = false;
                    org.powerbot.core.script.job.Task.sleep(2000, 2250);
                }
            }
        } else {
            Timer t = new Timer(0);
            Var.status = "[GATE] Null";
            Util.debug();
        }
    }
}
