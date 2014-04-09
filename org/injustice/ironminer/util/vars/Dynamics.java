package org.injustice.ironminer.util.vars;

import org.powerbot.game.api.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class Dynamics {
    public static Timer runTime = new Timer(0);
    public static String status = "[START] Starting...";

    public static int orePrice = 0;

    public static int startExp;
    public static int startLevel;

    public static boolean bankAtDaemonheim = false;
    public static boolean bankAtWizardsTower = false;
    public static boolean regularDrop = false;
    public static boolean actionBarDrop = true;
    public static boolean mousekeysDrop = false;
    public static boolean pickaxeInvent = false;
    public static boolean pickaxeWielded = false;
    public static boolean mineOneDropOne = false;
    public static boolean doAntiban = true;

    public static boolean bank() {
        return bankAtDaemonheim || bankAtWizardsTower;
    }
}
