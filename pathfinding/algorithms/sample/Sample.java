package pathfinding.algorithms.sample;

import pathfinding.algorithms.Algorithm;
import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/5/13
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */

public class Sample implements Algorithm
{
    @Override
    public LinkedList<Tile> findPath(Tile start, Tile destination)
    {
        if(start == null || destination == null)
            return null;

        final Node first = new Node(start);
        final Node last = new Node(destination);

        LinkedList<Node> nodes = new LinkedList<Node>();

        nodes.add(first);
        nodes.add(last);

        for(Tile tile : Manager.tiles)
            if(!tile.equals(start) && !tile.equals(destination))
                nodes.add(new Node(tile));

        LinkedList<Node> open = new LinkedList<Node>();
        LinkedList<Node> closed = new LinkedList<Node>();

        Node curr = first;
        open.add(curr);

        while (!open.isEmpty())
        {
            open.remove(curr);
            closed.add(curr);

            if(curr.equals(last))
                return convertToTileArray(getPath(first, last, closed));

            for(Node node : curr.getNeighbors(nodes))
            {
                if(node.counter == 0 || node.counter > curr.counter + 1)
                    node.counter = curr.counter + 1;

                if(!closed.contains(node) && !open.contains(node))
                    open.add(node);
            }

            if(!open.isEmpty())
                curr = open.get(0);
        }
        return null;
    }

    private LinkedList<Node> getPath(Node start, Node destination, LinkedList<Node> nodes)
    {
        LinkedList<Node> reversedPath = new LinkedList<Node>();

        Node curr = destination;

        while(!curr.equals(start))
        {
            reversedPath.add(curr);
            curr = curr.getNearestWithLowestCounter(nodes);
        }

        reversedPath.add(start);

        LinkedList<Node> path = new LinkedList<Node>();

        for(int i = 0; i < reversedPath.size(); i++)
            path.add(reversedPath.get(reversedPath.size() - i - 1));

        return path;
    }

    private LinkedList<Tile> convertToTileArray(LinkedList<Node> nodes)
    {
        LinkedList<Tile> tiles = new LinkedList<Tile>();

        for(Node node : nodes)
            tiles.add(node.tile);

        return tiles;
    }
}
