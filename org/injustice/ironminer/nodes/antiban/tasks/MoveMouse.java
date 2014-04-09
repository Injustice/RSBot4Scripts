package org.injustice.ironminer.nodes.antiban.tasks;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;

import java.awt.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 13/04/13
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class MoveMouse {
    public static void run() {
        Point p = Mouse.getLocation();
        Mouse.move(Random.nextInt(0, randomise((int) p.getX())), Random.nextInt(0, (int) p.getY()));
    }

    private static int randomise(int i) {
        return i += Random.nextInt(-100, 100);
    }
}
