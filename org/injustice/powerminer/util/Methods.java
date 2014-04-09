package org.injustice.powerminer.util;

import org.injustice.framework.Condition;
import org.injustice.powerminer.PowerMiner;
import org.injustice.powerminer.util.enums.Rock;
import org.injustice.powerminer.util.enums.State;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;

import static org.injustice.powerminer.util.Var.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 30/05/13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class Methods {
    public static State getState() {
        if (Inventory.contains(gems)) {
            return State.DROP_GEMS;
        } else if (Inventory.contains(Rock.LIMESTONE.getInvId()) && Inventory.isFull()) {
            return State.LIMESTONE;
        } else if (Inventory.isFull()) {
            return State.DROP;
        } else if (Inventory.contains(6979, 6981, 6983) && Inventory.isFull()) {
            return State.GRANITE;
        } else if (SceneEntities.getNearest(ROCK_FILTER) == null &&
                Inventory.contains(UserSettings.chosenRock.getInvId())) {
            return State.DROP_NO_ROCKS;
        }
        return State.MINE;

    }

    public static void debug(String status) {
        if (status != Var.status && status != null) {
            if (debug) {
                PowerMiner.l.log(status);
            } else {
                Var.status = status;
                PowerMiner.l.log(status);
            }
        }
    }

    public static void debug(String status, boolean showStatus) {
        if (showStatus) {
            if (status != null && Var.status != status) {
                Var.status = status;
                PowerMiner.l.log(status);
            }
        } else {
            PowerMiner.l.log(status);
        }
    }

    public static int getRocks(final int radius) {
        final Filter<SceneObject> rocks = new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject o) {
                for (int i : UserSettings.chosenRock.getIds()) {
                    if (o.getId() == i) {
                        if (Calculations.distance(o, startTile) < radius) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        SceneObject[] loadedRocks = SceneEntities.getLoaded(rocks);
        return loadedRocks.length;
    }

    public static String format(long number, int precision) {
        String sign = number < 0 ? "-" : "";
        number = Math.abs(number);
        if (number < 1000) {
            return sign + number;
        }
        int exponent = (int) (Math.log(number) / Math.log(1000));
        return String.format("%s%." + precision + "f%s", sign,
                number / Math.pow(1000, exponent),
                "kmbtpe".charAt(exponent - 1));
    }

    public static void percBar(boolean vertical, int x, int y,
                               int width, int height, double percentage,
                               Color PercCol, Color outC, Stroke outS, Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        int drawPercentage = (int) (((percentage > 100 ? 100 : percentage) / 100) * width);
        g.setColor(PercCol);
        if (!vertical)
            g.fillRect(x, y, (width > height ? drawPercentage : width), (height >= width ? drawPercentage : height));
        else {
            int x1 = (width > height ? width - drawPercentage : 0);
            int y1 = (height > width ? height - drawPercentage : 0);
            g.fillRect(x + x1, y + y1, width - x1, height - y1);
        }
        g.setColor(outC);
        g.setStroke(outS);
        g.drawRect(x, y, width, height);

    }

    public static int getPercentToNextLevel(final int index) {
        final int lvl = Skills.getRealLevel(index);
        return getPercentToLevel(index, lvl + 1);
    }

    public static int getPercentToLevel(final int index, final int endLvl) {
        final int lvl = Skills.getRealLevel(index);
        if (lvl == 99) {
            return 0;
        }
        final int xpNeeded = Skills.XP_TABLE[endLvl] - Skills.XP_TABLE[lvl];
        if (xpNeeded == 0) {
            return 0;
        }
        final int xpDone = Skills.getExperience(index)
                - Skills.XP_TABLE[lvl];
        return 100 * xpDone / xpNeeded;
    }

    public static String getTimeToNextLevel(final int skill, final int xpPerHour) {
        if (xpPerHour < 1) {
            return Time.format(0L);
        }
        if (Skills.getLevel(skill) >= 99) {
            if (Skills.getRealLevel(skill) == Skills.DUNGEONEERING && Skills.getRealLevel(skill) == 120)
                return "Never!";
            return "Never!";
        }
        return Time.format((long)
                (Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) * 3600000D / xpPerHour));
    }

    public static void fillPolygons(Graphics g, Color c, Entity... e) {
        g.setColor(c);
        for (Entity entity : e) {
            for (Polygon p : entity.getBounds()) {
                g.fillPolygon(p);
            }
        }
    }

    public static Rectangle getSelectedRectangle() {
        switch (radius) {
            case 1:
                return r1;
            case 2:
                return r2;
            case 3:
                return r3;
            case 4:
                return r4;
            case 5:
                return r5;
            case 6:
                return r6;
            case 7:
                return r7;
            case 8:
                return r8;
            case 9:
                return r9;
            case 10:
                return r10;
            case 11:
                return r11;
        }
        return null;
    }

    public static void makeArea(final int radius, final Tile loc) {
        final int x = loc.getX();
        final int y = loc.getY();
        final int p = loc.getPlane();
        final Tile N = new Tile(x, y + radius + 1, p);
        final Tile S = new Tile(x, y - radius - 1, p);
        final Tile E = new Tile(x - radius, y, p);
        final Tile W = new Tile(x + radius + 1, y, p);
        rockArea = new Area(N, E, S, W);
        tileArray = rockArea.getTileArray();
    }

    public static boolean isOnScreen(Entity e) {
        return e.isOnScreen() && !(Widgets.get(640, 4).validate() ?
                Widgets.get(640, 4) : Widgets.get(640, 2)).getBoundingRectangle().contains(e.getCentralPoint());
    }

    public static void magic(int min, int max, Tile[] tileArray, int radius, int timeout) {

        Timer notFound = new Timer(timeout);
        for (Tile destinationTile : tileArray) {
            notFound.reset();
            while (Calculations.distanceTo(destinationTile) > radius) {
                if (!notFound.isRunning()) {
                    return;
                }
                Walking.walk(destinationTile);
                Task.sleep(min, max);
            }
        }
    }

    public static boolean waitFor(final Condition c, final long timeout) {
        final Timer t = new Timer(timeout);
        while (!c.activate() || t.isRunning()) {
            Task.sleep(50, 75);
        }
        return c.activate();
    }
}
