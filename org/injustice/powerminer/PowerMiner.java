package org.injustice.powerminer;

import org.injustice.framework.Actionbar;
import org.injustice.framework.api.Logger;
import org.injustice.powerminer.strat.*;
import org.injustice.powerminer.util.GUI;
import org.injustice.powerminer.util.Methods;
import org.injustice.powerminer.util.UserSettings;
import org.injustice.powerminer.util.Var;
import org.powerbot.core.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.injustice.powerminer.util.Methods.getRocks;
import static org.injustice.powerminer.util.Methods.getState;
import static org.injustice.powerminer.util.Var.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 30/05/13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice",
        name = "Just Mine",
        description = "Mines anything, anywhere. Previously Injustice's Iron Miner",
        version = 5.0,
        topic = 1011643)
public class PowerMiner extends ActiveScript implements PaintListener, MouseListener, MessageListener {
    public static Logger l = new Logger(Var.font3);
    private Client client = Bot.client();
    private int session = Math.round(System.currentTimeMillis() / 1000);

    @Override
    public void onStart() {
        status = "[LOGIN] Waiting";
        getContainer().submit(new LoopTask() {
            @Override
            public int loop() {
                if (Game.getClientState() == Game.INDEX_MAP_LOADED)
                    return -1;
                return 500;
            }
        });
        l.log("[STARTUP] Just Mine by Injustice v" + this.getClass().getAnnotation(Manifest.class).version());
        Mouse.setSpeed(Mouse.Speed.FAST);
        startExp = Skills.getExperience(Skills.MINING);
        l.log("[STARTUP] Start exp: " + startExp);
        startLvl = Skills.getRealLevel(Skills.MINING);
        l.log("[STARTUP] Start level: " + startLvl);
        startTile = Players.getLocal().getLocation();
        l.log("[STARTUP] Start tile: " + startTile);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GUI g = new GUI();
                    g.setVisible(true);
                } catch (Exception e) {
                    l.log("[SEVERE] GUI Exception");
                }
            }
        });
        Methods.debug("[STARTUP] Ensuring actionbar");
        if (Actionbar.isOpen()) {
            Actionbar.close();
        }
        Actionbar.open();
        status = "[GUI] Waiting";
        while(!guiDone) {
            sleep(500);
        }
        l.log("[SETTINGS] Advanced debug is on");
        l.log("[SETTINGS] Chosen rock " + UserSettings.chosenRock.toString());
        l.log("[SETTINGS] Rocks loaded " + getRocks(200));
        l.log("[SETTINGS] Rocks in radius (" + radius + "): " + getRocks(radius));
    }

    int i = 0;
    long addon;
    int addExp;
    int addOres;
    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }
        if (client != Bot.client()) {
            WidgetCache.purge();
            Bot.context().getEventManager().addListener(this);
            client = Bot.client();
        }
        if (i == 250) {
            Methods.debug("[SIG] Updating stats", true);
            updateDynamicSig((runTime.getElapsed() - addon),
                    (Var.oresMined - addOres),
                    (Skills.getExperience(Skills.MINING) - startExp - addExp));
            i = 0;
        }
        if (guiDone) {
            switch(getState()) {
                case MINE:
                    Miner.mine();
                    break;
                case DROP:
                    Drop.drop();
                    break;
                case DROP_GEMS:
                    DropGems.dropGems();
                    break;
                case LIMESTONE:
                    DropLimestone.dropLimestone();
                    break;
                case GRANITE:
                    DropGranite.dropGranite();
                    break;
                case DROP_NO_ROCKS:
                    DropNull.dropNull();
                    break;
            }
            if (Widgets.canContinue()) {
                Methods.debug("[FAILSAFE] Clicking continue", true);
                Widgets.clickContinue();
            }
        } else {
            status = "[STARTUP] GUI";
        }

        getContainer().submit(new LoopTask() {
            @Override
            public int loop() {
                if (guiDone)
                    return -1;
                return 500;
            }
        });
        i++;
        if (i % 50 == 0 && i != 250 && i != 0) {
            Methods.debug("[SIG] " + (250 - i) + " loops to update", true);
        }
        return 150;
    }

    @Override
    public void onStop() {
        Methods.debug("[UPDATE] Dynamic signature");
        updateDynamicSig((runTime.getElapsed() - addon),
                (Var.oresMined - addOres),
                (Skills.getExperience(Skills.MINING) - startExp - addExp));
        Methods.debug("[STOPPING] Post proggies!");
        sleep(1500);
        l.remove();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (pointRadius(p, r1) && radius == 1) {
            radius = 250;
        } else
        if (pointRadius(p, r2) && radius == 2) {
            radius = 250;
        } else
        if (pointRadius(p, r3) && radius == 3) {
            radius = 250;
        } else
        if (pointRadius(p, r4) && radius == 4) {
            radius = 250;
        } else
        if (pointRadius(p, r5) && radius == 5) {
            radius = 250;
        } else
        if (pointRadius(p, r6) && radius == 6) {
            radius = 250;
        } else
        if (pointRadius(p, r6) && radius == 6) {
            radius = 250;
        } else
        if (pointRadius(p, r7) && radius == 7) {
            radius = 250;
        } else
        if (pointRadius(p, r8) && radius == 8) {
            radius = 250;
        } else
        if (pointRadius(p, r9) && radius == 9) {
            radius = 250;
        } else
        if (pointRadius(p, r10) && radius == 10) {
            radius = 250;
        } else
        if (pointRadius(p, r11) && radius == 11) {
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
            Methods.debug("[SETTINGS] Advanced debug is " + (debug ? "on" : "off"));
        }
        if (rAll.contains(p))
            l.log("[SETTINGS] Rocks in radius (" + radius + "): " + getRocks(radius));    }

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


    @Override
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        if (guiDone) {
            drawTiles(g);
        }
        int expGain = Skills.getExperience(Skills.MINING) - startExp;
        int lvlGain = Skills.getRealLevel(Skills.MINING) - startLvl;
        int oresHr = (int) (oresMined * 3600000d / runTime.getElapsed());
        int expHr = (int) (expGain * 3600000d / runTime.getElapsed());

        g.setStroke(stroke1);
        g.setColor(grey);
        g.fillRoundRect(5, 178, 190, 135, 7, 7);
        g.setColor(Color.GREEN);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 178, 190, 135, 7, 7);
        g.setFont(font1);
        g.drawRect(5, 178, 190, 21);

        g.drawString("Just Mine by Injustice", 8, 196);
        g.setFont(font4);
        if (guiDone)
            g.drawString("" + UserSettings.chosenRock.name().replace("_", ""), loc.x, loc.y - 10);
        g.setFont(font2);
        g.drawString("v" + this.getClass().getAnnotation(Manifest.class).version(), 176, 196);
        g.setFont(font3);
        g.drawString("Ores mined: " + Methods.format(oresMined, 2), loc.x, loc.y);
        g.drawString("Ores PH: " + Methods.format(oresHr, 2), loc.x + 100, loc.y);
        g.drawString("Level: " + Skills.getRealLevel(Skills.MINING) + " (+" + lvlGain + ")", loc.x + 100, loc.y + 15);
        g.drawString("Time TNL: " + Methods.getTimeToNextLevel(Skills.MINING, expHr), loc.x, loc.y + 15);
        g.drawString("Exp gain: " + Methods.format(expGain, 2), loc.x, loc.y + 30);
        g.drawString("Exp PH: " + Methods.format(expHr, 2), loc.x + 100, loc.y + 30);
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
        if (Methods.getSelectedRectangle() != null) {
            g.draw(Methods.getSelectedRectangle());
        }
        g.setColor(green);
        drawMouse(g);
        Methods.percBar(false, loc.x, loc.y + 35, 180, 13,
                Methods.getPercentToNextLevel(Skills.MINING), green, blue, stroke1, g);
        g.setColor(Color.BLACK);
        g.drawString("" + Methods.getPercentToNextLevel(Skills.MINING) + "%", 88, 265);
    }

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
        final SceneObject[] badRocks = SceneEntities.getLoaded(UserSettings.chosenRock.getIds());
        Methods.makeArea(radius, startTile);
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
        Methods.fillPolygons(g, blue, startTile);
    }

    private void updateDynamicSig(long runtime, int oresMined, int expGained, int session) {
        addOres = Var.oresMined;
        addExp = Skills.getExperience(Skills.MINING) - startExp;
        addon = runTime.getElapsed();                                      //Create a variable in your script of type int (Call it whatever you want). err, yea.

        try {
            URL submit = new URL(
                    "http://injusticescripts.sekaihosting.com/just_mine/input.php?username=" +
                            Environment.getDisplayName() + "&runtime=" + runtime/1000 +
                            "&expgain=" + expGained +
                            "&oresmined=" + oresMined +
                            "&key=dfsahucvih7vba484a112348g8nb8ads6vbxv8hn7sfy723qg" +
                            "&session=" + session);
            URLConnection con = submit.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;                                                                             I
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("param")) {
                    Methods.debug("[UPDATE] Dynamic sig failed", true);
                } else {
                    Methods.debug("[UPDATE] Dynamic sig succesful", true);
                }
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceived(MessageEvent e) {
        if (e.getMessage().toLowerCase().contains("manage")) {
            Var.oresMined++;
        }
    }

    private boolean pointRadius(Point p, Rectangle r) {
        return r.contains(p);
    }
}
