package org.injustice.ironminer.ui;

import org.injustice.ironminer.IronMiner;
import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class Paint {
    public static void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        Point loc = new Point(10, 220);
        Color green = new Color(20, 126, 59, 150);
        if (!Var.hidePaint) {
            int expGained = Skills.getExperience(Skills.MINING) - Dynamics.startExp;
            int expHour = (int) (expGained * 3600000d / Dynamics.runTime.getElapsed());
            int levelsGained = Skills.getRealLevel(Skills.MINING) - Dynamics.startLevel;
            int ironMined = expGained / 35;
            int ironHour = (int) (ironMined * 3600000d / Var.runTime.getElapsed());
            int cashMade = Dynamics.orePrice * ironMined;
            int cashHour = (int) (cashMade * 3600000d / Dynamics.runTime.getElapsed());

            BasicStroke stroke1 = new BasicStroke(1);
            g.setStroke(stroke1);
            Font font1 = new Font("Tahoma", 0, 15);
            Font font2 = new Font("Tahoma", 0, 9);
            Font font3 = new Font("Tahoma", 0, 11);
            Color grey = new Color(0, 0, 0, 100);

            g.setColor(grey);
            g.fillRoundRect(5, 178, 190, 120, 7, 7);
            g.setColor(Color.GREEN);
            g.setStroke(stroke1);
            g.drawRoundRect(5, 178, 190, 120, 7, 7);
            g.drawRect(5, 178, 190, 21);
            g.setFont(font1);

            g.drawString("Injustice's Iron Miner", 8, 196);
            g.setFont(font2);
            g.drawString("v" + IronMiner.VERSION, 173, 196);
            g.setFont(font3);
            g.drawString("Iron mined: " + Methods.format(ironMined, 2), loc.x, loc.y);
            g.drawString("Iron PH: " + Methods.format(ironHour, 2), loc.x, loc.y + 15);
            if (Dynamics.bank()) {
                g.drawString("Cash made: " + Methods.format(cashMade, 2), loc.x + 90, loc.y);
                g.drawString("Cash PH: " + Methods.format(cashHour, 2), loc.x + 90, loc.y + 15);
            }
            g.drawString("Exp gain: " + Methods.format(expGained, 2) + " (" +
                    Methods.format(expHour, 2) + ")", loc.x, loc.y + + 30);
            g.drawString("Start level: " + Dynamics.startLevel + " (+" + levelsGained + ")", loc.x, loc.y + 45);
            g.drawString("Runtime: " + Dynamics.runTime.toElapsedString(), loc.x, loc.y + 60);
            g.drawString("Status: " + Dynamics.status, loc.x, loc.y + 75);
//            g.drawString("Click to hide", loc.x + 123, loc.y + 75);
//            g.drawString("Show tiles", loc.x + 133, loc.y + 45);
//            g.drawString("Show mouse", loc.x + 122, loc.y + 60);
//            g.drawString("Take screenshot", loc.x + 104, loc.y);
            g.setColor(green);
//            g.drawRoundRect(132, 285, 59, 11, 3, 3);   // hide paint
//            g.drawRoundRect(131, 270, 61, 11, 3, 3);   // show mouse
//            g.drawRoundRect(140, 255, 52, 11, 3, 3);   // show tiles
//            g.drawRoundRect(112, 210, 81, 11, 3, 3);   // take screenshot
            drawMouse(g);
            drawTiles(g);
        } else {
            Font font3 = new Font("Tahoma", 0, 11);
            g.setFont(font3);
            g.setColor(Color.GREEN);
            g.drawString("Show paint", loc.x + 126, loc.y + 75);
            g.setColor(green);
            g.drawRoundRect(132, 285, 59, 11, 3, 3);  // show paint
        }
    }

    /**
     * @param g1 Graphics
     * @author not me, will find - phl0w/pxpc2, not sure
     */
    private static void drawMouse(Graphics g1) {
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        Color outerCircle = gradient[0];
        g1.setColor(gradient[0]);
        int circleRadius = 7;
        int circleDiameter = circleRadius * 2;
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

    private static void drawTiles(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        SceneObject rock = SceneEntities.getNearest(Constants.IRON_ROCK_ID);
        SceneObject[] rocks = SceneEntities.getLoaded(Constants.IRON_ROCK_ID);
        for (SceneObject s : rocks) {
            g.setColor(Color.RED);
            s.getLocation().draw(g);
            g.setColor(Color.BLUE);
            rock.getLocation().draw(g);
            g.setColor(Color.BLACK);
            Tile loc = new Tile(Players.getLocal().getLocation().getX(),
                    Players.getLocal().getLocation().getY(),
                    Players.getLocal().getLocation().getPlane());
            loc.draw(g);
        }
    }
}
