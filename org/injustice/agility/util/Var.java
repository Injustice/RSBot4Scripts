package org.injustice.agility.util;

import org.powerbot.game.api.methods.input.Mouse;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class Var {

    public static String status;


    public static class Settings {
        public static boolean eat = true;
        public static LinkedList<Obstacle> obstaclesList = new LinkedList<Obstacle>();
        public static Mouse.Speed mouseSpeed = Mouse.Speed.NORMAL;

    }


}
