package pathfinding.algorithms.dijkstra;

import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/5/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */

public class Node
{
    final Tile tile;
    double dist;

    Node parent;

    public Node(Tile tile)
    {
        this.tile = tile;
        dist = Double.MAX_VALUE;
        parent = null;
    }

    public Node[] getNeighbors(LinkedList<Node> nodes)
    {
        LinkedList<Node> neighbors = new LinkedList<Node>();

        for(final Node node : nodes)
        {
            double dist = node.tile.distanceTo(tile);

            if((Manager.useDiagonals ? dist >= 1 && dist < 1.5 : dist == 1) && tile.getType() != Type.BLOCK)
                neighbors.add(node);
        }

        return neighbors.toArray(new Node[neighbors.size()]);
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Node))
            return false;

        Node node = (Node)o;
        return node.dist == dist && node.tile.equals(tile);
    }
}
