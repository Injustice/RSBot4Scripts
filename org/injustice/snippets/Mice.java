package org.injustice.snippets;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.core.script.job.Task;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Azmat
 * Date: 19/03/13
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class Mice {
    /**

     * @param c color object

     * @param g graphics2D object

     *

     */

    public static void drawMouse1(Color c, Graphics2D g) {

        Point p = Mouse.getLocation();

        g.setColor(c);

        g.setStroke(new BasicStroke(2));

        if (Mouse.isPressed())

            g.setColor(Color.RED);

        g.fill(new Rectangle(p.x + 1, p.y - 4, 2, 15));

        g.fill(new Rectangle(p.x - 6, p.y + 2, 16, 2));

    }

    public static void drawMouse2(Graphics g) {
       Color MOUSE_COLOR = Color.CYAN,

       MOUSE_BORDER_COLOR = new Color(204, 0, 255);

        ((Graphics2D) g).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Graphics2D spinG = (Graphics2D) g.create();
        Graphics2D spinGRev = (Graphics2D) g.create();
        spinG.setColor(MOUSE_BORDER_COLOR);
        spinGRev.setColor(MOUSE_COLOR);
        spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2
                * Math.PI / 180.0, p.x, p.y);
        spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d)
                * 2 * Math.PI / 180.0, p.x, p.y);
        final int outerSize = 20;
        final int innerSize = 12;
        spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, 100, 75);
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, -100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, 100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, -100, 75);
    }

    public static void drawMouse3(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;
        g1.setColor(color2);
        g1.drawLine(p.x - 2000, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y + 2000);
        int steps = 100;
        for (int i = steps; i > 0; i--) {
            float ratio = (float) i / (float) steps;
            int red = (int) (color2.getRed() * ratio + color1.getRed()
                    * (1 - ratio));
            int green = (int) (color2.getGreen() * ratio + color1.getGreen()
                    * (1 - ratio));
            int blue = (int) (color2.getBlue() * ratio + color1.getBlue()
                    * (1 - ratio));
            Color stepColor = new Color(red, green, blue);
            g1.setColor(stepColor);
            g1.drawLine(p.x - (i * 5), p.y, p.x + (i * 5), p.y);
            g1.drawLine(p.x, p.y - (i * 5), p.x, p.y + (i * 5));
        }
    }


    public static void drawMouse4(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        g1.setColor(gradient[0]);
        g1.drawLine(p.x - 2000, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y + 2000);
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
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        + ((i * 5) + (100 * r)));
            }
        }
    }


    public static void drawMouse5 (Graphics g1) {
        double angle = 0;
        double angle2 = Math.PI / 2;
        double d = 6;
        boolean bufferMouse = false;
        Graphics2D g = (Graphics2D) g1;
        g.setColor(Color.RED);
        Point p = Mouse.getLocation();
        if (bufferMouse) { //a boolean that is true while working / safe waiting
            angle += 0.1;
            angle %= Math.PI * 2;
            angle2 += 0.1;
            angle2 %= Math.PI * 2;
            double dcos1 = d * Math.cos(angle), dsin1 = d
                    * Math.sin(angle), dcos2 = d * Math.cos(angle2), dsin2 = d
                    * Math.sin(angle2);
            double x1, y1, x2, y2, x3, y3, x4, y4;
            x1 = p.x - dcos1;
            y1 = p.y - dsin1;
            x2 = p.x + dcos1;
            y2 = p.y + dsin1;
            x3 = p.x - dcos2;
            y3 = p.y - dsin2;
            x4 = p.x + dcos2;
            y4 = p.y + dsin2;
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            g.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
        } else {
            g.drawLine(p.x - 6, p.y, p.x + 6, p.y);
            g.drawLine(p.x, p.y - 6, p.x, p.y + 6);
        }
    }
    public static boolean bufferMouse;

    public static void waitForTab(Tabs tab) {
        int i = 0;
        while (Tabs.getCurrent() != tab) {
            bufferMouse = true;
            Task.sleep(10);
            i++;
            if (i > 150) {
                break;
            }
        }
        bufferMouse = false;
        Task.sleep(50);
    }

    public static void drawMouse6(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        Color outerCircle = gradient[0];
        Color innerCircle = Color.white;
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


}

