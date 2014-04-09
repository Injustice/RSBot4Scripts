package org.injustice.ironminer.util.methods;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class Sleeping {
    public static void sleep(int timeone, int timetwo, boolean condition) {
        do sleep(timeone, timetwo); while (!condition);
    }

    public static void sleep(int time, boolean condition) {
        do Task.sleep(time); while (!condition);
    }

    public static void sleep(int timeone, int timetwo) {
        Task.sleep(timeone, timetwo);
    }

    public static boolean waitFor(boolean a, long timeout) {
        Timer t = new Timer(timeout);
        while (t.isRunning() && !a) {
            Task.sleep(50);
        }
        return a;
    }

    public static void sleep(int time) {
        Task.sleep(time);
    }

    public static void sleepFirst(int time, boolean condition, String action) {
        sleep(time);
        while (!condition) {
            Methods.s(action + " Sleeping");
            sleep(time);
            if (condition) break;
        }

    }


}
