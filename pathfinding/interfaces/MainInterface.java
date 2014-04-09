package pathfinding.interfaces;

import pathfinding.algorithms.astar.AStar;
import pathfinding.algorithms.dijkstra.Dijkstra;
import pathfinding.algorithms.sample.Sample;
import pathfinding.manager.Manager;
import pathfinding.manager.parts.Tile;
import pathfinding.manager.parts.Type;
import pathfinding.misc.Constants;
import pathfinding.misc.Painter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static pathfinding.misc.Constants.GRID_SIZE;

/**
 * Created with IntelliJ IDEA.
 * User: Sharon
 * Date: 5/4/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class MainInterface extends JFrame
{
    private final JPanel sidePanel = new JPanel(null);

    private boolean drawBlock = true;

    public final JPanel drawPanel = new JPanel(null)
    {
        @Override
        public void paint(Graphics g)
        {
            g.setColor(Color.black);

            g.drawLine(0, 0, 0, drawPanel.getHeight());
            g.drawLine(0, 0, drawPanel.getWidth(), 0);
            g.drawLine(drawPanel.getWidth() - 1, 0, drawPanel.getWidth() - 1, drawPanel.getHeight());
            g.drawLine(0, drawPanel.getHeight() - 1, drawPanel.getWidth(), drawPanel.getHeight() - 1);

            Painter.drawTiles((Graphics2D) g, Manager.tiles.toArray(new Tile[Manager.tiles.size()]));

            if(Manager.path != null && Manager.path.size() > 0)
                Painter.drawPath((Graphics2D) g, Manager.path.toArray(new Tile[Manager.path.size()]));
        }
    };

    public MainInterface()
    {
        initialize();
    }

    private void initialize()
    {
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Path finding");
        setLayout(null);

        final Insets insets = getInsets();

        sidePanel.setBounds(10, 10, 200, 600);
        sidePanel.setBorder(new EtchedBorder());

        add(sidePanel);

        JPanel panelOption = new JPanel(null);
        panelOption.setBounds(10, 10, sidePanel.getWidth() - 20, 110);
        panelOption.setBorder(new EtchedBorder());

        sidePanel.add(panelOption);

        JPanel panelAlgorithms = new JPanel(null);
        panelAlgorithms.setBounds(10, panelOption.getY() + panelOption.getHeight() + 20, panelOption.getWidth(), 100);
        panelAlgorithms.setBorder(new EtchedBorder());

        sidePanel.add(panelAlgorithms);

        ButtonGroup buttonGroup = new ButtonGroup();

        final JCheckBox checkBoxDiagonals = new JCheckBox("Check diagonals");

        checkBoxDiagonals.setFont(Constants.FONT_ARIAL_BOLD_11);
        checkBoxDiagonals.setBounds(10, 10, panelOption.getWidth() - 20, 20);

        panelOption.add(checkBoxDiagonals);

        JLabel labelGridSize = new JLabel("Grid size:");

        labelGridSize.setFont(Constants.FONT_ARIAL_BOLD_11);
        labelGridSize.setBounds(checkBoxDiagonals.getX(), checkBoxDiagonals.getY() + checkBoxDiagonals.getHeight() + 10, (int) labelGridSize.getPreferredSize().getWidth(), 20);

        panelOption.add(labelGridSize);

        final JTextField textFieldGridSize = new JTextField("20");

        textFieldGridSize.setFont(Constants.FONT_ARIAL_BOLD_11);
        textFieldGridSize.setBounds(labelGridSize.getX() + labelGridSize.getWidth() + 10, labelGridSize.getY(), panelOption.getWidth() - labelGridSize.getWidth() - 30, 20);

        panelOption.add(textFieldGridSize);

        JButton buttonGridSize = new JButton("Apply changes");

        buttonGridSize.setFont(Constants.FONT_ARIAL_BOLD_11);
        buttonGridSize.setBounds(checkBoxDiagonals.getX(), textFieldGridSize.getY() + textFieldGridSize.getHeight() + 10, panelOption.getWidth() - 20, 20);

        buttonGridSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Constants.GRID_SIZE = tryParse(textFieldGridSize.getText());

                Manager.tiles.clear();
                Manager.path = null;

                for(int i = 0; i < drawPanel.getWidth() / GRID_SIZE; i++)
                    for(int j = 0; j < drawPanel.getHeight() / GRID_SIZE; j++)
                        Manager.tiles.add(new Tile(new Point(i, j)));

                repaint();
            }
        });

        panelOption.add(buttonGridSize);

        JRadioButton radioButtonAStart = new JRadioButton("A Star");

        radioButtonAStart.setFont(Constants.FONT_ARIAL_BOLD_11);
        radioButtonAStart.setBounds(10, 10, panelAlgorithms.getWidth() - 20, 20);
        radioButtonAStart.setSelected(true);

        radioButtonAStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.algorithm = new AStar();
            }
        });

        buttonGroup.add(radioButtonAStart);

        panelAlgorithms.add(radioButtonAStart);

        JRadioButton radioButtonSample = new JRadioButton("Sample");

        radioButtonSample.setFont(Constants.FONT_ARIAL_BOLD_11);
        radioButtonSample.setBounds(10, radioButtonAStart.getY() + radioButtonAStart.getHeight() + 10, panelAlgorithms.getWidth() - 20, 20);
        radioButtonSample.setSelected(true);

        radioButtonSample.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.algorithm = new Sample();
            }
        });

        buttonGroup.add(radioButtonSample);

        panelAlgorithms.add(radioButtonSample);

        JRadioButton radioButtonDijkstra = new JRadioButton("Dijkstra");

        radioButtonDijkstra.setFont(Constants.FONT_ARIAL_BOLD_11);
        radioButtonDijkstra.setBounds(10, radioButtonSample.getY() + radioButtonSample.getHeight() + 10, panelAlgorithms.getWidth() - 20, 20);
        radioButtonDijkstra.setSelected(true);

        radioButtonDijkstra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.algorithm = new Dijkstra();
            }
        });

        buttonGroup.add(radioButtonDijkstra);

        panelAlgorithms.add(radioButtonDijkstra);

        JButton calculateButton = new JButton("Find path");

        calculateButton.setFont(Constants.FONT_ARIAL_BOLD_11);
        calculateButton.setBounds(10, panelAlgorithms.getY() + panelAlgorithms.getHeight() + 20, sidePanel.getWidth() - 20, 20);

        calculateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.useDiagonals = checkBoxDiagonals.isSelected();

                long startMillis = System.currentTimeMillis();

                Manager.calculatePath();

                System.out.println("Took: " + (System.currentTimeMillis() - startMillis) + " millis using the " + Manager.algorithm.getClass().getSimpleName() + " algorithm");

                if(Manager.path == null)
                    JOptionPane.showMessageDialog(null, "Couldn't calculate path, check field", "Couldn't calculate path", JOptionPane.INFORMATION_MESSAGE);

                repaint();
            }
        });

        sidePanel.add(calculateButton);

        JButton clearButton = new JButton("Clear path");

        clearButton.setFont(Constants.FONT_ARIAL_BOLD_11);
        clearButton.setBounds(10, calculateButton.getY() + calculateButton.getHeight() + 20, calculateButton.getWidth(), 20);

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.clearPath();

                repaint();
            }
        });

        sidePanel.add(clearButton);

        JButton generateButton = new JButton("Generate field");

        generateButton.setFont(Constants.FONT_ARIAL_BOLD_11);
        generateButton.setBounds(10, clearButton.getY() + clearButton.getHeight() + 20, clearButton.getWidth(), 20);

        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Manager.clearPath();

                for(Tile tile : Manager.tiles)
                {
                    if(Constants.RANDOM.nextInt(4) == 0)
                        tile.setType(pathfinding.manager.parts.Type.BLOCK);
                    else
                        tile.setType(pathfinding.manager.parts.Type.REGULAR);
                }

                repaint();
            }
        });

        sidePanel.add(generateButton);

        /* ... */

        drawPanel.setBounds(sidePanel.getX() + sidePanel.getWidth() + 10, sidePanel.getY(), 1000, sidePanel.getHeight());
        drawPanel.setBorder(new EtchedBorder());

        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                final Tile tile = Tile.getFromPoint(e.getPoint(), Manager.tiles.toArray(new Tile[Manager.tiles.size()]));

                if(tile != null)
                {
                    drawBlock = tile.getType() == pathfinding.manager.parts.Type.REGULAR;

                    Manager.path = null;

                    if(e.getButton() == MouseEvent.BUTTON1)
                        tile.setType(tile.getType() == pathfinding.manager.parts.Type.REGULAR ? pathfinding.manager.parts.Type.BLOCK : pathfinding.manager.parts.Type.REGULAR);
                    else if(e.getButton() == MouseEvent.BUTTON2)
                    {
                        Tile startTile = Manager.getStartTile();
                        if(startTile != null)
                            startTile.setType(pathfinding.manager.parts.Type.REGULAR);

                        tile.setType(pathfinding.manager.parts.Type.START);
                    }
                    else if(e.getButton() == MouseEvent.BUTTON3)
                    {
                        Tile endTile = Manager.getEndTile();
                        if(endTile != null)
                            endTile.setType(pathfinding.manager.parts.Type.REGULAR);

                        tile.setType(pathfinding.manager.parts.Type.END);
                    }
                }

                repaint();
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    final Tile tile = Tile.getFromPoint(e.getPoint(), Manager.tiles.toArray(new Tile[Manager.tiles.size()]));

                    if(tile != null)
                        tile.setType(drawBlock ? pathfinding.manager.parts.Type.BLOCK : pathfinding.manager.parts.Type.REGULAR);

                    repaint();
                }
            }
        });

        add(drawPanel);

        /* ... */

        setSize(drawPanel.getX() + drawPanel.getWidth() + 10 + insets.left + insets.right,
                drawPanel.getY() + drawPanel.getHeight() + 10 + insets.bottom + insets.top);
        setLocationRelativeTo(null);
    }

    private static int tryParse(final String str)
    {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException ignore)
        {
            return -1;
        }
    }
}
