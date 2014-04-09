package pathfinding.algorithms.astar;

import pathfinding.algorithms.Algorithm;
import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */

public class AStar implements Algorithm
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
        curr.h = calculateH(first, last);
        open.add(curr);

        while(!open.isEmpty())
        {
            curr = getWithLowestF(open);

            if(curr.equals(last))
                return convertArray(getPath(first, last));

            open.remove(curr);
            closed.add(curr);

            final Node[] neighbors = curr.getNeighbors(nodes);

            if(neighbors == null || neighbors.length <= 0)
                continue;

            for(final Node neighbor : neighbors)
            {
                if(neighbor != null && !closed.contains(neighbor) && neighbor.tile.getType() != Type.BLOCK)
                {
                    double dist = curr.tile.distanceTo(neighbor.tile);

                    final double g = curr.g + dist;
                    boolean useNewValues = false;

                    if(!open.contains(neighbor))
                    {
                        open.add(neighbor);
                        useNewValues = true;
                    }

                    if(g < neighbor.g)
                        useNewValues = true;

                    if(useNewValues)
                    {
                        neighbor.parent = curr;
                        neighbor.h = calculateH(curr, last);
                        neighbor.g = g;
                    }
                }
            }
        }

        return null;
    }

    private double calculateH(Node a, Node b)
    {
        return a.tile.distanceTo(b.tile);
    }

    private static Node getWithLowestF(final LinkedList<Node> openList)
    {
        Node best = null;

        for(final Node tile : openList)
        {
            if(best == null || tile.getF() < best.getF())
                best = tile;
        }

        return best;
    }

    private static Node[] getPath(final Node start, final Node destination)
    {
        LinkedList<Node> path = new LinkedList<Node>();
        Node next = destination;

        while (next != null && !next.equals(start))
        {
            path.add(next);
            next = next.parent;
        }

        path.add(start);

        final Node[] truePath = new Node[path.size()];

        for(int i = 0; i < path.size(); i++)
        {
            truePath[i] = path.get(path.size() - i - 1);
        }

        return truePath;
    }

    private static LinkedList<Tile> convertArray(Node... nodes)
    {
        LinkedList<Tile> path = new LinkedList<Tile>();

        for(Node node : nodes)
            path.add(node.tile);

        return path;
    }
}
