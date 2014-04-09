package pathfinding;

import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;
import pathfinding.interfaces.MainInterface;

import javax.swing.*;
import java.awt.*;

import static pathfinding.misc.Constants.GRID_SIZE;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class PathFinding
{
    public static void main(String[] args)
    {
        MainInterface mainInterface = new MainInterface();

        for(int i = 0; i < mainInterface.drawPanel.getWidth() / GRID_SIZE; i++)
            for(int j = 0; j < mainInterface.drawPanel.getHeight() / GRID_SIZE; j++)
                Manager.tiles.add(new Tile(new Point(i, j)));

        JOptionPane.showMessageDialog(null, "Press BUTTON1 to add block tiles\nPress BUTTON2 to add start tile\nPress BUTTON3 to add end tile", "Path finding", JOptionPane.INFORMATION_MESSAGE);
    }
}
