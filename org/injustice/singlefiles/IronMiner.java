package org.injustice.singlefiles;

import org.injustice.framework.api.Logger;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 18/05/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
@Manifest(name = "Injustice's Iron Powerminer",
        authors = "Injustice",
        topic = 1011643,
        description = "Have iron ore in first slot",
        version = 4.4,
        instances = 2,
        hidden = true)
public final class IronMiner extends ActiveScript implements PaintListener, MouseListener {
    private static final Font font3 = new Font("Tahoma", 0, 11);
    static Logger l = new Logger(font3);

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (pointRadius(p, r1) && radius == 1) {
            radius = 250;
        } else if (pointRadius(p, r2) && radius == 2) {
            radius = 250;
        } else if (pointRadius(p, r3) && radius == 3) {
            radius = 250;
        } else if (pointRadius(p, r4) && radius == 4) {
            radius = 250;
        } else if (pointRadius(p, r5) && radius == 5) {
            radius = 250;
        } else if (pointRadius(p, r6) && radius == 6) {
            radius = 250;
        } else if (pointRadius(p, r6) && radius == 6) {
            radius = 250;
        } else if (pointRadius(p, r7) && radius == 7) {
            radius = 250;
        } else if (pointRadius(p, r8) && radius == 8) {
            radius = 250;
        } else if (pointRadius(p, r9) && radius == 9) {
            radius = 250;
        } else if (pointRadius(p, r10) && radius == 10) {
            radius = 250;
        } else if (pointRadius(p, r11) && radius == 11) {
            radius = 250;
        } else
        if (r1.contains(p)) radius = 1;
        else if (r2.contains(p)) radius = 2;
        else if (r3.contains(p)) radius = 3;
        else if (r4.contains(p)) radius = 4;
        else if (r5.contains(p)) radius = 5;
        else if (r6.contains(p)) radius = 6;
        else if (r7.contains(p)) radius = 7;
        else if (r8.contains(p)) radius = 8;
        else if (r9.contains(p)) radius = 9;
        else if (r10.contains(p)) radius = 10;
        else if (r11.contains(p)) radius = 11;
        else if (rDebug.contains(p)) {
            debug = !debug;
            debug("[SETTINGS] Advanced debug is " + (debug ? "on" : "off"));
        }
        if (rAll.contains(p))
            Logger.log("[SETTINGS] Rocks in radius (" + radius + "): " + getRocks(radius));
    }

    private enum State{
        MINE, DROP, DROP_GEMS
    }

    private boolean pointRadius(Point p, Rectangle r) {
        return r.contains(p);
    }

    private String status;
    private void debug(String status) {
        if (status != this.status && status != null) {
            if (debug) {
                l.log(status);
            } else {
                this.status = status;
                l.log(status);
            }
        }
    }
    private boolean debug = false;
    private int startExp;
    private int startLvl;
    private int gemsDropped;
    private int[] gems = {1623, 1621, 1619, 1617};
    private Tile startTile;
    private Timer runTime = new Timer(0);
    private boolean isOnScreen(Entity e) {
        return e.isOnScreen() && !(Widgets.get(640, 4).validate() ?
                Widgets.get(640, 4) : Widgets.get(640, 2)).getBoundingRectangle().contains(e.getCentralPoint());
    }
    private static int radius = 5;
    int i = 0;

    @Override
    public void onStart() {
        l.log("[CREDITS] Thanks to nathantehbeast for logger");
        l.log("[STARTUP] Injustice's Iron Miner v" + this.getClass().getAnnotation(Manifest.class).version());
        int i = 0;
        while(Game.getClientState() != 11 && i == 0) {
            l.log("[STARTUP] Waiting for logon");
            i++;
            sleep(1000);
        }
        if (Game.getClientState() == 11) {
            Mouse.setSpeed(Mouse.Speed.FAST);
            startExp = Skills.getExperience(Skills.MINING);
            l.log("[STARTUP] Start exp: " + startExp);
            startLvl = Skills.getRealLevel(Skills.MINING);
            l.log("[STARTUP] Start level: " + startLvl);
            startTile = Players.getLocal().getLocation();
            l.log("[STARTUP] Start tile: " + startTile);
            l.log("[SETTINGS] Rocks in radius (" + radius + "): " + getRocks(radius));
        }
    }

    @Override
    public void onStop() {
        updateDynamicSig((runTime.getElapsed() - addon),
                ((Skills.getExperience(Skills.MINING) - startExp)/ 35 - addOres),
                (Skills.getExperience(Skills.MINING) - startExp - addExp));
        debug("[STOPPING] Post proggies!");
        sleep(1500);
        l.remove();
    }

    private final int[] ROCKS = {2092, 2093,5773,5774,5775,9717,9718,9719,11954,11955,11956,14099,14107,14857,14856,14858,14913,14914,
            21281,21282,21283,37307,37308,37309,72083,72081,72082};
    private final int IRON_ITEM = 440;
    private Area rockArea;
    private Tile[] tileArray;

    private State getState() {
        if (Inventory.isFull() || SceneEntities.getNearest(ROCK_FILTER) == null) {
            return State.DROP;
        } else if (Inventory.contains(gems)) {
            return State.DROP_GEMS;
        } else {
            return State.MINE;
        }
    }
    @Override
    public int loop() {
        if (i == 200) {
            debug("[SIG] Updating stats");
            updateDynamicSig((runTime.getElapsed() - addon),
                    ((Skills.getExperience(Skills.MINING) - startExp) / 35 - addOres),
                    (Skills.getExperience(Skills.MINING) - startExp - addExp));
            i = 0;
        }
        switch(getState()) {
            case MINE:
                final SceneObject rock = SceneEntities.getNearest(ROCK_FILTER);
                final SceneObject rockNoRadius = SceneEntities.getNearest(ROCK_NO_RADIUS_FILTER);
                if (rock != null) {
                    debug((debug ? "[MINE] Rock found - id (" + rock.getId() + ") - loc " + rock.getLocation() : null));
                    if (isOnScreen(rock)) {
                        if (rock.interact("Mine")) {
                            if (debug) {
                                l.log("[MINE] Mining - " + (28 - Inventory.getCount()) + " left");
                            } else {
                                debug("[MINE] Mining");
                            }
                            Timer t = new Timer(5000);
                            while (Players.getLocal().getAnimation() == -1 && t.isRunning()) {
                                sleep(500);
                            }
                            t.reset();
                            while (Players.getLocal().getAnimation() != -1 && t.isRunning()) {
                                sleep(500);
                            }
                        }
                    } else {
                        debug(debug ? "[MINE] Change of camera needed to mobile angle " + Camera.getMobileAngle(rock) : "[MINE] Turning to rock");
                        Camera.turnTo(rock);
                        for (int i = 0; i < 15 && !rock.isOnScreen(); i++) {
                            sleep(250, 300);
                        }
                        debug(debug ? "[MINE] Successfully changed camera to yaw " + Camera.getYaw() : null);
                    }
                } else if (rockNoRadius != null){
                    if (Calculations.distance(rockNoRadius, startTile) <= 11) {
                        debug("[MINE] Increase radius");
                    }
                } else {
                    debug("[MINE] Post ID of rock on thread");}
                break;
            case DROP:
                l.log("[DROP] Dropping");
                while (Inventory.contains(IRON_ITEM)) {
                    status = "[DROP] Dropping";
                    Keyboard.sendKey('1');
                    sleep(150, 250);
                }
            case DROP_GEMS:
                final Item gem = Inventory.getItem(gems);
                if (gem != null) {
                    debug(debug ? "[GEM] Dropping " + gem.getName() + " - ID - " + gem.getId() : "[GEM] Dropping");
                    if (gem.getWidgetChild().interact("Drop")) {
                        gemsDropped++;
                    }
                }

        }
        if (Widgets.canContinue()) {
            debug(debug ? "[FAILSAFE] Clicking widget - ID - " + Widgets.getContinue().getWidget().getId() :"[FAILSAFE] Clicking widget");
            Widgets.clickContinue();
        }
        i++;
        return Random.nextInt(150, 200);
    }

    private int getRocks(final int radius) {
        final Filter<SceneObject> rocks = new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject o) {
                for (int i : ROCKS) {
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

    @Override
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        drawTiles(g);
        int expGain = Skills.getExperience(Skills.MINING) - startExp;
        int lvlGain = Skills.getRealLevel(Skills.MINING) - startLvl;
        int ironMined = expGain/35;
        int ironHr = (int) (ironMined * 3600000d / runTime.getElapsed());
        int expHr = (int) (expGain * 3600000d / runTime.getElapsed());

        g.setStroke(stroke1);
        g.setColor(grey);
        g.fillRoundRect(5, 178, 190, 135, 7, 7);
        g.setColor(Color.GREEN);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 178, 190, 135, 7, 7);
        g.setFont(font1);
        g.drawRect(5, 178, 190, 21);

        g.drawString("Injustice's Iron Miner", 8, 196);
        g.setFont(font2);
        g.drawString("v" + this.getClass().getAnnotation(Manifest.class).version(), 176, 196);
        g.setFont(font3);
        g.drawString("Iron mined: " + format(ironMined, 2), loc.x, loc.y);
        g.drawString("Iron PH: " + format(ironHr, 2), loc.x + 100, loc.y);
        g.drawString("Level: " + startLvl + " (+" + lvlGain + ")", loc.x + 100, loc.y + 15);
        g.drawString("Time TNL: " + getTimeToNextLevel(Skills.MINING, expHr), loc.x, loc.y + 15);
        g.drawString("Exp gain: " + format(expGain, 2), loc.x, loc.y + 30);
        g.drawString("Exp PH: " + format(expHr, 2), loc.x + 100, loc.y + 30);
        g.drawString("Runtime: " + runTime.toElapsedString(), loc.x, loc.y + 60);
        g.drawString("Status: " + status, loc.x, loc.y + 75);
        g.drawString("Radius: ", 10, 308);
        g.drawString("1  2  3  4  5  6  7  8  9  10  11", 48, 308);
        g.drawString("Toggle debug", loc.x + 100, loc.y + 60);
        g.setColor(grey);
        g.draw(rDebug);
        g.draw(r1);
        g.draw(r2);
        g.draw(r3);
        g.draw(r4);
        g.draw(r5);
        g.draw(r6);
        g.draw(r7);
        g.draw(r8);
        g.draw(r9);
        g.draw(r10);
        g.draw(r11);
        g.setColor(Color.GREEN);
        if (getSelectedRectangle() != null) {
            g.draw(getSelectedRectangle());
        }
        g.setColor(green);
        drawMouse(g);
        percBar(false, loc.x, loc.y + 35, 180, 13,
                getPercentToNextLevel(Skills.MINING), green, blue, stroke1, g);
        g.setColor(Color.BLACK);
        g.drawString("" + getPercentToNextLevel(Skills.MINING) + "%", 88, 265);
    }

    /**
     * @param g1 Graphics
     * @author not me, will find - phl0w/pxpc2, not sure
     */
    private void drawMouse(Graphics g1) {
        Point p = Mouse.getLocation();
        g1.setColor(gradient[0]);
        g1.drawLine(p.x + circleRadius, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x - 2000, p.y, p.x - circleRadius, p.y);
// Vertical
        g1.drawLine(p.x, p.y + circleRadius, p.x, p.y + 2000);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y - circleRadius);
        for (int r = gradient.length - 1; r > 0; r--) {
            int steps = 200 / ((gradient.length - 1) * 2);
            for (int i = steps; i > 0; i--) {
                float ratio = (float) i / (float) steps;
                int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
                        .getRed() * (1 - ratio));
                int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
                        .getGreen() * (1 - ratio));
                int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
                        .getBlue() * (1 - ratio));
                Color stepColor = new Color(red, green, blue);
                g1.setColor(stepColor);
// Horizontal
                g1.drawLine(p.x + circleRadius, p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        - circleRadius, p.y);
// Vertical
                g1.drawLine(p.x, p.y + circleRadius, p.x, p.y
                        + ((i * 5) + (100 * r)));
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        - circleRadius);
            }
        }
        g1.setColor(outerCircle);
        final long mpt = System.currentTimeMillis() - Mouse.getPressTime();
        if (Mouse.getPressTime() == -1 || mpt >= 200) {
            g1.drawOval(p.x - circleRadius / 3, p.y - circleRadius / 3,
                    circleDiameter / 3, circleDiameter / 3);
        }
        if (mpt < 200) {
            g1.drawLine(p.x - circleRadius, p.y + circleRadius, p.x
                    + circleRadius, p.y - circleRadius);
            g1.drawLine(p.x - circleRadius, p.y - circleRadius, p.x
                    + circleRadius, p.y + circleRadius);
        }
        g1.setColor(outerCircle);
        g1.drawOval(p.x - circleRadius, p.y - circleRadius, circleDiameter,
                circleDiameter);
    }

    private void drawTiles(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        final SceneObject rock = SceneEntities.getNearest(ROCK_FILTER);
        final SceneObject[] rocks = SceneEntities.getLoaded(ROCK_FILTER);
        final SceneObject[] badRocks = SceneEntities.getLoaded(ROCKS);
        makeArea(radius, startTile);
        g.setColor(new Color(0, 0, 0, 50));
        for (Tile t : tileArray) {
            t.draw(g);
        }
        if (badRocks != null) {
            for (SceneObject r : badRocks) {
                if (r != null) {
                    g.setColor(Color.RED);
                    r.getLocation().draw(g);
                }
            }
        }
        if (rocks != null) {
            for (final SceneObject r : rocks) {
                if (r != null) {
                    g.setColor(Color.GREEN);
                    r.getLocation().draw(g);
                }
            }
        }
        if (rock != null) {
            g.setColor(Color.BLUE);
            rock.getLocation().draw(g);
        }
        fillPolygons(g, blue, startTile);
    }

    private static String format(long number, int precision) {
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

    private void percBar(boolean vertical, int x, int y,
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

    private int getPercentToNextLevel(final int index) {
        final int lvl = Skills.getRealLevel(index);
        return getPercentToLevel(index, lvl + 1);
    }

    private int getPercentToLevel(final int index, final int endLvl) {
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

    private String getTimeToNextLevel(final int skill, final int xpPerHour) {
        if (xpPerHour < 1) {
            return Time.format(0L);
        }
        return Time.format((long)
                (Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) * 3600000D / xpPerHour));
    }

    // PaintUtil vars
    private final Font font1 = new Font("Tahoma", 0, 15);
    private final Font font2 = new Font("Tahoma", 0, 9);
    private final Color grey = new Color(0, 0, 0, 100);
    private final BasicStroke stroke1 = new BasicStroke(1);
    private final Point loc = new Point(10, 220);
    private final Color green = new Color(20, 126, 59, 150);
    private final Color blue = new Color(10, 50, 255, 100);
    // Mouse vars
    private final Color[] gradient = new Color[]{new Color(255, 0, 0),
            new Color(255, 0, 255), new Color(0, 0, 255),
            new Color(0, 255, 255), new Color(0, 255, 0),
            new Color(255, 255, 0), new Color(255, 0, 0)};
    private final Color outerCircle = gradient[0];
    private final int circleRadius = 7;
    private final int circleDiameter = circleRadius * 2;
    private static Rectangle rDebug = new Rectangle(110, 269, 65, 13);
    private static final Rectangle rAll = new Rectangle(46, 298, 141, 11);
    private static final Rectangle r1 = new Rectangle(46, 298, 10, 11);
    private static final Rectangle r2 = new Rectangle(56, 298, 12, 11);
    private static final Rectangle r3 = new Rectangle(68, 298, 12, 11);
    private static final Rectangle r4 = new Rectangle(80, 298, 12, 11);
    private static final Rectangle r5 = new Rectangle(92, 298, 12, 11);
    private static final Rectangle r6 = new Rectangle(104, 298, 12, 11);
    private static final Rectangle r7 = new Rectangle(116, 298, 12, 11);
    private static final Rectangle r8 = new Rectangle(128, 298, 12, 11);
    private static final Rectangle r9 = new Rectangle(140, 298, 12, 11);
    private static final Rectangle r10 = new Rectangle(152, 298, 20, 11);
    private static final Rectangle r11 = new Rectangle(172, 298, 15, 11);
    private final Filter<SceneObject> ROCK_FILTER = new Filter<SceneObject>() {
        @Override
        public boolean accept (SceneObject o){
            for (int r : ROCKS) {
                if (o.getId() == r
                        && Calculations.distance(startTile, o) <= radius) {
                    return true;
                }
            }
            return false;
        }
    };
    private final Filter<SceneObject> ROCK_NO_RADIUS_FILTER = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject object) {
            for (int r : ROCKS) {
                if (object.getId() == r) {
                    return true;
                }
            }
            return false;
        }
    };

    private static void fillPolygons(Graphics g, Color c, Entity... e) {
        g.setColor(c);
        for (Entity entity : e) {
            for (Polygon p : entity.getBounds()) {
                g.fillPolygon(p);
            }
        }
    }

    private static Rectangle getSelectedRectangle() {
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

    private void makeArea(final int radius, final Tile loc) {
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
    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    int addOres;
    int addExp;
    long addon;

    private void updateDynamicSig(long runtime, int oresMined, int expGained) {
        addOres = (Skills.getExperience(Skills.MINING) - startExp) / 35;
        addExp = Skills.getExperience(Skills.MINING) - startExp;
        addon = runTime.getElapsed();

        try {
            URL submit = new URL(
                    "http://injusticescripts.sekaihosting.com/adgtyuyg1/gro8y1243/9asdidhi123/input.php?username=" +
                            Environment.getDisplayName() + "&runtime=" + runtime / 1000 +
                            "&expgain=" + expGained +
                            "&oresmined=" + oresMined);
            URLConnection con = submit.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("param")) {
                    debug("[UPDATE] Dynamic sig failed");
                } else {
                    debug("[UPDATE] Dynamic sig succesful");
                }
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

