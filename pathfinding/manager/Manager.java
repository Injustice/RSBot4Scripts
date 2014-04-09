package pathfinding.manager;

import pathfinding.algorithms.Algorithm;
import pathfinding.algorithms.astar.AStar;
import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;

import javax.swing.*;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class Manager
{
    public static final LinkedList<Tile> tiles = new LinkedList<Tile>();

    public static LinkedList<Tile> path = new LinkedList<Tile>();

    public static Algorithm algorithm = new AStar();

    public static boolean useDiagonals = true;

    public static void calculatePath()
    {
        if(algorithm == null)
        {
            JOptionPane.showMessageDialog(null, "Couldn't calculate path, no algorithm chosen", "Error calculating path", JOptionPane.ERROR_MESSAGE);

            return;
        }

        path = algorithm.findPath(getStartTile(), getEndTile());
    }

    public static void clearPath()
    {
        path = new LinkedList<Tile>();
    }

    public static Tile getStartTile()
    {
        for(final Tile tile : tiles)
            if(tile.getType() == Type.START)
                return tile;
        return null;
    }

    public static Tile getEndTile()
    {
        for(final Tile tile : tiles)
            if(tile.getType() == Type.END)
                return tile;
        return null;
    }
}
