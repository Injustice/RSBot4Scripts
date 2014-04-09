package pathfinding.algorithms.astar;

import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class Node
{
    public final Tile tile;

    public double g;
    public double h;

    public Node parent;

    public Node(Tile tile)
    {
        this.tile = tile;
        parent = null;
    }

    public double getF()
    {
        return g + h;
    }

    public Node[] getNeighbors(LinkedList<Node> nodes)
    {
        LinkedList<Node> neighbors = new LinkedList<Node>();

        for(final Node node : nodes)
        {
            double dist = node.tile.distanceTo(tile);

            if(Manager.useDiagonals ? dist >= 1 && dist < 1.5 : dist == 1)
                neighbors.add(node);
        }

        return neighbors.toArray(new Node[neighbors.size()]);
    }
}
