/*
 * Created by JFormDesigner on Thu May 30 12:39:49 BST 2013
 */

package org.injustice.powerminer.util;

import org.injustice.powerminer.util.enums.Rock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Azmat Habibullah
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();
    }

    private void startActionPerformed(ActionEvent e) {
        String choice = options.getSelectedItem().toString().toLowerCase();
        switch(choice) {
            case "tin":
                UserSettings.chosenRock = Rock.TIN;
                break;
            case "copper":
                UserSettings.chosenRock = Rock.COPPER;
                break;
            case "clay":
                UserSettings.chosenRock = Rock.CLAY;
                break;
            case "iron":
                UserSettings.chosenRock = Rock.IRON;
                break;
            case "coal":
                UserSettings.chosenRock = Rock.COAL;
                break;
            case "limestone":
                UserSettings.chosenRock = Rock.LIMESTONE;
                break;
            case "granite":
                UserSettings.chosenRock = Rock.GRANITE;
                break;
            case "silver":
                UserSettings.chosenRock = Rock.SILVER;
                break;
            case "gold":
                UserSettings.chosenRock = Rock.GOLD;
                break;
            case "mithril":
                UserSettings.chosenRock = Rock.MITHRIL;
                break;
            case "adamant":
                UserSettings.chosenRock = Rock.ADAMANT;
                break;
            case "rune":
                UserSettings.chosenRock = Rock.RUNE;
                break;
            case "gem_rock":
                UserSettings.chosenRock = Rock.GEM_ROCK;
        }
        this.setVisible(false);
        Var.guiDone = true;

    }

    private void initComponents() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - this.getWidth()) / 2);
        int y = (int) ((d.getHeight() - this.getHeight()) / 2);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Powerminer GUI");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setBounds(x, y, 200, 100);
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2, 0));
        JButton start = new JButton("Start!");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startActionPerformed(e);
            }
        });
        this.add(options);
        this.add(start);
        this.setVisible(true);

    }

    final JComboBox options = new JComboBox(Rock.values());
}
