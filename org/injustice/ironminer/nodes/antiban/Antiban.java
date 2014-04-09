package org.injustice.ironminer.nodes.antiban;

import org.injustice.ironminer.nodes.antiban.tasks.MoveCamera;
import org.injustice.ironminer.nodes.antiban.tasks.MoveMouse;
import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class Antiban extends Node {
    @Override
    public boolean activate() {
        return Dynamics.doAntiban;
    }

    @Override
    public void execute() {
        if (Random.nextInt(0, 500) == 1 ) {
            Methods.s("[ANTIBAN] Moving camera");
            MoveCamera.run();
        }
        if (Random.nextInt(0, 500) == 1) {
            Methods.s("[ANTIBAN] Moving mouse");
            MoveMouse.run();
        }
    }
}
