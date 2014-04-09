package pathfinding.algorithms.sample;

import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/5/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */

public class Node
{
    final Tile tile;

    double counter;

    public Node(Tile tile)
    {
        this.tile = tile;
        counter = 0;
    }

    public Node[] getNeighbors(LinkedList<Node> nodes)
    {
        LinkedList<Node> neighbors = new LinkedList<Node>();

        for(Node node : nodes)
            if(node.tile.distanceTo(tile) == 1 && node.tile.getType() != Type.BLOCK)
                neighbors.add(node);

        return neighbors.toArray(new Node[neighbors.size()]);
    }

    public Node getNearestWithLowestCounter(LinkedList<Node> nodes)
    {
        Node lowest = null;
        for(Node node : nodes)
        {
            double dist = node.tile.distanceTo(tile);

            if(dist == 1 && (lowest == null || node.counter < lowest.counter))
                lowest = node;
        }

        return lowest;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Node))
            return false;

        Node node = (Node)o;
        return node.counter == counter && node.tile.equals(tile);
    }
}
