package pathfinding.algorithms.dijkstra;

import pathfinding.algorithms.Algorithm;
import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/5/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */

public class Dijkstra implements Algorithm
{
    @Override
    public LinkedList<Tile> findPath(Tile start, Tile destination)
    {
        if(start == null || destination == null)
            return null;

        final Node first = new Node(start);
        final Node last = new Node(destination);

        final LinkedList<Node> nodes = new LinkedList<Node>();

        nodes.add(first);
        nodes.add(last);

        for(final Tile tile : Manager.tiles)
        {
            if(!tile.equals(first.tile) && !tile.equals(last.tile))
                nodes.add(new Node(tile));
        }

        final LinkedList<Node> open = new LinkedList<Node>();
        final LinkedList<Node> closed = new LinkedList<Node>();

        Node curr = first;
        curr.dist = 0;
        open.add(curr);

        while (!open.isEmpty())
        {
            open.remove(curr);
            closed.add(curr);

            if(curr.equals(last))
                return convertArray(getPath(first, last));

            for(Node neighbor : curr.getNeighbors(nodes))
            {
                double dist = curr.tile.distanceTo(neighbor.tile);

                if(neighbor.dist > curr.dist + dist)
                {
                    neighbor.dist = curr.dist + dist;
                    neighbor.parent = curr;
                }

                if(!closed.contains(neighbor) && !open.contains(neighbor))
                    open.add(neighbor);
            }

            if(!open.isEmpty())
                curr = open.get(0);
        }

        return null;
    }

    private LinkedList<Node> getPath(Node start, Node destination)
    {
        LinkedList<Node> path = new LinkedList<Node>();
        Node next = destination;

        while (next != null && !next.equals(start))
        {
            path.add(next);
            next = next.parent;
        }

        path.add(start);

        final LinkedList<Node> truePath = new LinkedList<Node>();

        for(int i = 0; i < path.size(); i++)
        {
            truePath.add(path.get(path.size() - i - 1));
        }

        return truePath;
    }

    private static LinkedList<Tile> convertArray(LinkedList<Node> nodes)
    {
        LinkedList<Tile> path = new LinkedList<Tile>();

        for(Node node : nodes)
            path.add(node.tile);

        return path;
    }
}
