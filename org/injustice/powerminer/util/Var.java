package org.injustice.powerminer.util;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 30/05/13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class Var {
    public static boolean guiDone;

    public static String status;

    public static int radius = 5;
    public static int oresMined = 0;

    public static final Font font3 = new Font("Tahoma", 0, 11);

    public static boolean debug = true;

    public static int startExp;
    public static int startLvl;
    public static int gemsDropped;

    public static int[] depletedGemRocks = {11365, 11366, 11367};
    public static int[] gems = {1621, 1617, 1619, 1623, 1625, 1627, 1629, 1631, 21345};
    public static int[] strangeRocks = {15532, 15533};

    public static Tile startTile;

    public static Timer runTime = new Timer(0);

    public static final int[] ROCKS = {2092, 2093, 5773, 5774, 5775, 9717, 9718, 9719, 11954, 11955, 11956, 14099, 14107, 14857, 14856, 14858, 14913, 14914,
            21281, 21282, 21283, 37307, 37308, 37309, 72083, 72081, 72082};

    public static final int IRON_ITEM = 440;

    public static Area rockArea;

    public static Tile[] tileArray;

    public static final Font font1 = new Font("Tahoma", 0, 15);
    public static final Font font2 = new Font("Tahoma", 0, 10);
    public static final Font font4 = new Font("Tahoma", Font.ITALIC, 9);

    public static final Color grey = new Color(0, 0, 0, 100);

    public static final BasicStroke stroke1 = new BasicStroke(1);

    public static final Point loc = new Point(10, 220);

    public static final Color green = new Color(20, 126, 59, 150);
    public static final Color blue = new Color(10, 50, 255, 100);
    // Mouse vars
    public static final Color[] gradient = new Color[]{new Color(255, 0, 0),
            new Color(255, 0, 255), new Color(0, 0, 255),
            new Color(0, 255, 255), new Color(0, 255, 0),
            new Color(255, 255, 0), new Color(255, 0, 0)};

    public static final Color outerCircle = gradient[0];

    public static final int circleRadius = 7;
    public static final int circleDiameter = circleRadius * 2;

    public static Rectangle rDebug = new Rectangle(110, 269, 65, 13);
    public static final Rectangle rAll = new Rectangle(46, 298, 141, 11);
    public static final Rectangle r1 = new Rectangle(46, 298, 10, 11);
    public static final Rectangle r2 = new Rectangle(56, 298, 12, 11);
    public static final Rectangle r3 = new Rectangle(68, 298, 12, 11);
    public static final Rectangle r4 = new Rectangle(80, 298, 12, 11);
    public static final Rectangle r5 = new Rectangle(92, 298, 12, 11);
    public static final Rectangle r6 = new Rectangle(104, 298, 12, 11);
    public static final Rectangle r7 = new Rectangle(116, 298, 12, 11);
    public static final Rectangle r8 = new Rectangle(128, 298, 12, 11);
    public static final Rectangle r9 = new Rectangle(140, 298, 12, 11);
    public static final Rectangle r10 = new Rectangle(152, 298, 20, 11);
    public static final Rectangle r11 = new Rectangle(172, 298, 15, 11);

    public static final Filter<SceneObject> ROCK_FILTER = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject o) {
            for (int r : UserSettings.chosenRock.getIds()) {
                if (o.getId() == r
                        && Calculations.distance(startTile, o) <= radius) {
                    for (int i : depletedGemRocks) {
                        if (o.getId() != i) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };
    public static final Filter<SceneObject> ROCK_NO_RADIUS_FILTER = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject object) {
            for (int r : UserSettings.chosenRock.getIds()) {
                if (object.getId() == r) {
                    for (int i : depletedGemRocks) {
                        if (object.getId() != i) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };

    public static final int[] BANKERS = {44, 45, 494, 495, 496, 497,
            498, 499, 553, 909, 958, 1036, 2271, 2354, 2355, 2718, 2759, 3198,
            3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 5488, 5901, 5912, 6200,
            6362, 6532, 6533, 6534, 6535, 7605, 8948, 9710, 14367};
    public static final int[] BANK_BOOTHS = {782, 2213, 2995, 5276,
            6084, 10517, 11402, 12759, 14367, 19230, 20325, 24914, 11338, 11758,
            25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019,
            42217, 42377, 42378};
    public static final int[] DEPOSIT_BOXES = {2045, 9398, 20228, 24995, 25937,
            26969, 32924, 32930, 32931, 34755, 36788, 39830, 45079};
    public static final int[] BANK_CHESTS = {2693, 4483, 8981, 12308, 21301, 20607,
            21301, 27663, 42192};
}
