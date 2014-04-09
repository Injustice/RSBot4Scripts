package pathfinding.algorithms;

import pathfinding.manager.parts.Tile;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */

public interface Algorithm
{
    public LinkedList<Tile> findPath(Tile start, Tile destination);
}
