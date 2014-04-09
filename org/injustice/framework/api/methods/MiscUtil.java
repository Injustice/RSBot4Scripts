package org.injustice.framework.api.methods;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.interactive.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/05/13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */

/**
 * Miscellaneous methods
 */
public class MiscUtil {
    /**
     * Checks if an entity is on screen, accounting for action bar author Spectrum_
     * @param e Entity to check
     * @return <tt>true</tt> if entity is on screen, <tt>false</tt> otherwise
     */
    public static boolean isOnScreen(Entity e) {
        return e.isOnScreen() && !(Widgets.get(640, 4).validate() ?
                Widgets.get(640, 4) : Widgets.get(640, 2)).getBoundingRectangle().contains(e.getCentralPoint());
    }

    /**
     * Saves a screenshot in the temp folder author Coma
     * @param x X location
     * @param y Y Location
     * @param w Width
     * @param h Height
     */
    public static void savePaint(final int x, final int y, final int w, final int h) {
        final File path = new File(Environment.getStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        final BufferedImage img = Environment.captureScreen().getSubimage(x, y, w, h);
        try {
            ImageIO.write(img, "png", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an image, recommended to cache it
     * @param url URL to get the image from
     * @return Image from the URL, if unsuccessful returns null
     */
    public static Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Moves camera to a random pitch and angle
     * @see MiscUtil#moveCamera(int, int, int, int)
     */
    public static void moveCameraRandomly() {
        moveCamera(0, 360, 0, 360);
    }

    /**
     * Moves camera to a random pitch and angle between parameters
     * @param minP Minimum pitch
     * @param maxP Maximum pitch
     * @param minA Minimum angle
     * @param maxA Maximum angle
     */
    public static void moveCamera(int minP, int maxP, int minA, int maxA) {
        Camera.setPitch(Random.nextInt(minP, maxP));
        Camera.setAngle(Random.nextInt(minA, maxA));
    }

    /**
     * Moves the mouse a random distance between minDistance and maxDistance, author Enfilade
     * Moves the mouse a random distance between minDistance
     * and maxDistance from the current position of the mouse by generating
     * random vector and then multiplying it by a random number between
     * minDistance and maxDistance. The maximum distance is cut short if the
     * mouse would go off screen in the direction it chose.
     * @param minDistance The minimum distance the cursor will move
     * @param maxDistance The maximum distance the cursor will move (exclusive)
     */
    public static void moveMouseRandomly(int minDistance, int maxDistance) {
        double xVector = Math.random();
        if (Random.nextInt(0, 2) == 1) {
            xVector = -xVector;
        }
        double yVector = Math.sqrt(1 - xVector * xVector);
        if (Random.nextInt(0, 2) == 1) {
            yVector = -yVector;
        }
        double distance = maxDistance;
        Point p = Mouse.getLocation();
        int maxX = (int) Math.round(xVector * distance + p.x);
        distance -= Math.abs((maxX - Math.max(0,
                Math.min(Game.getDimensions().getWidth(), maxX)))
                / xVector);
        int maxY = (int) Math.round(yVector * distance + p.y);
        distance -= Math.abs((maxY - Math.max(0,
                Math.min(Game.getDimensions().getHeight(), maxY)))
                / yVector);
        if (distance < minDistance) {
            return;
        }
        distance = Random.nextInt(minDistance, (int) distance);
        Mouse.move((int) (xVector * distance) + p.x, (int) (yVector * distance) + p.y);
    }

    /**
     * Checks if the player is under attack, author AddictiveScripts
     * @return <tt>true</tt> if being attacked, <tt>false</tt> otherwise
     */
    public static boolean isUnderAttack() {
        return NPCs.getNearest(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.getInteracting() != null &&
                        npc.getInteracting().equals(Players.getLocal()) &&
                        npc.getLevel() > 0 &&
                        npc.getHealthPercent() > 0;
            }
        }) != null;
    }

    /**
     * Checks if the player's inventory is empty
     * @return <tt>true</tt> if empty, <tt>false</tt> if not
     */
    public static boolean inventEmpty() {
        return Inventory.getCount() == 0;
    }

    /**
     * Waits for a condition with a timeout
     * @param c Condition to check for
     * @param timeout Max time to wait for
     * @return <tt>true</tt> if the Condition is met, <tt>false</tt> otherwise
     */
    public boolean waitFor(Condition c, long timeout) {
        final Timer t = new Timer(timeout);
        while (!c.validate() || t.isRunning()) {
            Task.sleep(50, 75);
        }
        return c.validate();
    }

}
