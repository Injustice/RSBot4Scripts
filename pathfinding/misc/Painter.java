package pathfinding.misc;

import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;

import java.awt.*;

import static pathfinding.misc.Constants.GRID_SIZE;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */

public final class Painter
{
    public static void drawTiles(Graphics2D g, Tile... tiles) // Draw in different colors
    {
        for(final Tile tile : tiles)
        {
            if(tile.getType() == Type.REGULAR)
                g.setColor(new Color(100, 100, 100, 100));
            else if(tile.getType() == Type.BLOCK)
                g.setColor(Color.black);
            else if(tile.getType() == Type.START)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.RED);

            g.fillRect((int) (tile.getPoint().getX() * GRID_SIZE) + 2, (int) (tile.getPoint().getY() * GRID_SIZE) + 2, GRID_SIZE - 3, GRID_SIZE - 3);

            g.setColor(Color.black);

            g.drawRect((int) (tile.getPoint().getX() * GRID_SIZE), (int) (tile.getPoint().getY() * GRID_SIZE), GRID_SIZE, GRID_SIZE);
        }
    }

    public static void drawPath(Graphics2D g, Tile[] path)
    {
        g.setColor(Color.gray);
        g.setStroke(new BasicStroke(5));

        for(int i = 0; i < path.length - 1; i++)
        {
            Tile curr = path[i];
            Tile next = path[i + 1];

            g.drawLine(curr.getPoint().x * GRID_SIZE + GRID_SIZE / 2, curr.getPoint().y * GRID_SIZE + GRID_SIZE / 2,
                    next.getPoint().x * GRID_SIZE + GRID_SIZE / 2, next.getPoint().y * GRID_SIZE + GRID_SIZE / 2);
        }

        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));

        for(int i = 0; i < path.length - 1; i++)
        {
            Tile curr = path[i];
            Tile next = path[i + 1];

            g.drawLine(curr.getPoint().x * GRID_SIZE + GRID_SIZE / 2, curr.getPoint().y * GRID_SIZE + GRID_SIZE / 2,
                    next.getPoint().x * GRID_SIZE + GRID_SIZE / 2, next.getPoint().y * GRID_SIZE + GRID_SIZE / 2);
        }
    }
}
