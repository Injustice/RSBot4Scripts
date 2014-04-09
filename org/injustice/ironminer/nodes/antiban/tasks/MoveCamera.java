package org.injustice.ironminer.nodes.antiban.tasks;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 23:09
 * To change this template use File | Settings | File Templates.
 */
public class MoveCamera {
    public static void run() {
        int angle = Camera.getX();
        Camera.setPitch(Random.nextInt(0, 90));
        Camera.setAngle(Random.nextInt(0, 360));
        Task.sleep(750, 1000);
        Camera.setAngle(randomise(angle));
    }

    private static int randomise(int i) {
        return i+= Random.nextInt(-15, 15);
    }
}
