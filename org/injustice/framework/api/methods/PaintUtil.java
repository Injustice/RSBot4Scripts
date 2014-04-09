package org.injustice.framework.api.methods;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/05/13
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */

/**
 * Methods to do with Paint
 */
public class PaintUtil {
    /**
     * Fills entities
     * @param g Graphics to use
     * @param c Colour to fill with
     * @param fill Fill or draw the entity
     * @param e Entity to paint
     */
    public void paintEntities(Graphics g, Color c, boolean fill, Entity... e) {
        g.setColor(c);
        for (Entity entity : e) {
            for (Polygon p : entity.getBounds()) {
                if (fill) {
                    g.fillPolygon(p);
                } else {
                    g.drawPolygon(p);
                }
            }
        }
    }

    /**
     * Formats a number with decimal places to a set precision
     * @param number Number to format
     * @param precision Number of decimal places
     * @return <tt>number</tt> formatted with the number of decimal places in <tt>precision</tt>
     */
    public String format(long number, int precision) {
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

    /**
     * @param vertical   Vertical or horizontal bar
     * @param x          X location
     * @param y          Y location
     * @param width      Width of bar
     * @param height     Height of bar
     * @param percentage Percentage to level {@link SkillUtil#getPercentToLevel(int, int)}
     * @param PercCol    Colour of the filled bar
     * @param outC       Colour of the outside bar
     * @param outS       Colour of the stroke
     * @param g1         Graphics to use
     * @author Wyn
     */
    public void percBar(boolean vertical, int x, int y,
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

    /**
     * Paints the tile the player is currently on
     * @param g1 Graphics to use
     * @param c Color to paint with
     * @param fill Whether to fill or draw the tile
     */
    public void paintPlayerTile(Graphics g1, Color c, boolean fill) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(c);
        if (fill) {
            paintEntities(g, c, true, Players.getLocal());
        } else {
            Players.getLocal().getLocation().draw(g);
        }
    }

    /**
     * Draws tiles of all Locatables given with chosen colour,
     * option to choose to only paint when they're on screen
     * @param g1 Graphics to use
     * @param c Colour to draw with
     * @param onScreen Only draw if on screen
     * @param locs Locatables to draw
     */
    public void drawTiles(Graphics g1, Color c, boolean onScreen, Locatable... locs) {
        Graphics2D g = (Graphics2D) g1;
        for (Locatable l : locs) {
            if (l != null) {
                if (onScreen && MiscUtil.isOnScreen((Entity) l)) {
                    l.getLocation().draw(g);
                } else if (onScreen && !MiscUtil.isOnScreen((Entity) l)) {
                    Camera.turnTo(l);
                } else if (!onScreen) {
                    l.getLocation().draw(g);
                }
            }
        }
    }
}
