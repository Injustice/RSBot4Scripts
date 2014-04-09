/*
 * Created by JFormDesigner on Tue May 07 18:00:18 BST 2013
 */

package org.injustice.fighter.ui;

import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Loot;
import org.powerbot.game.api.methods.input.Mouse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Injustice
 */
public class FighterGUI extends JFrame {
    public FighterGUI() {
        initComponents();
    }

    private void startActionPerformed(ActionEvent e) {
        if (cbAbilities.isSelected()) {
            Util.l.showInfo("[SETTINGS] Using abilities\n");
            UserSettings.useAbilities = true;
        }
        if (checkBox1.isSelected()) {
            Util.l.showInfo("[SETTINGS] Using rejuv\n");
            UserSettings.useRejuv = true;
        }
        if (cbSpins.isSelected()) {
            Util.l.showInfo("[SETTINGS] Claiming tickets\n");
            UserSettings.claimSpins = true;
        }
        if (checkBox2.isSelected()) {
            Util.l.showInfo("[SETTINGS] Picking charms\n");
            UserSettings.pickCharms = true;
        }
        UserSettings.eatPercent = jsEat.getValue();

        switch (cmbSpeed.getSelectedIndex()) {
            case 0:
                UserSettings.mouseSpeed = Mouse.Speed.VERY_SLOW;
                Util.l.showInfo("[SETTINGS] Very slow mouse\n");
                break;
            case 1:
                UserSettings.mouseSpeed = Mouse.Speed.SLOW;
                Util.l.showInfo("[SETTINGS] Slow mouse\n");
                break;
            case 2:
                UserSettings.mouseSpeed = Mouse.Speed.NORMAL;
                Util.l.showInfo("[SETTINGS] Regular mouse\n");
                break;
            case 3:
                UserSettings.mouseSpeed = Mouse.Speed.FAST;
                Util.l.showInfo("[SETTINGS] Fast mouse\n");
                break;
            case 4:
                UserSettings.mouseSpeed = Mouse.Speed.VERY_FAST;
                Util.l.showInfo("[SETTINGS] Very fast mouse\n");
                break;
        }
        Loot[] selectedLoot = (Loot[]) list2.getSelectedValues();
        for (Loot l : selectedLoot) {
            UserSettings.selectedLoot.add(l.getId());
            Util.l.showInfo("[SETTINGS] Looting " + l.name() + ":" + l.getId());
        }
        Var.guiDone = true;
        Util.l.showInfo("[GUI] Complete!\n");
        this.dispose();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Azmat Habibullah
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        lblEat = new JLabel();
        jsEat = new JSlider();
        cbAbilities = new JCheckBox();
        lblSpeed = new JLabel();
        cmbSpeed = new JComboBox<>();
        cbSpins = new JCheckBox();
        lblNote = new JLabel();
        scrollPane2 = new JScrollPane();
        list2 = new JList<>();
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();
        label1 = new JLabel();
        buttonBar = new JPanel();
        btnStart = new JButton();

        //======== this ========
        setTitle("Fighter GUI");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- lblEat ----
                lblEat.setText("Eat at percent");
                contentPanel.add(lblEat);
                lblEat.setBounds(45, 0, 70, 25);

                //---- jsEat ----
                jsEat.setMajorTickSpacing(10);
                jsEat.setMinimum(30);
                jsEat.setMaximum(80);
                jsEat.setMinorTickSpacing(5);
                jsEat.setPaintLabels(true);
                jsEat.setSnapToTicks(true);
                contentPanel.add(jsEat);
                jsEat.setBounds(0, 25, 155, jsEat.getPreferredSize().height);

                //---- cbAbilities ----
                cbAbilities.setText("Use Abilities");
                contentPanel.add(cbAbilities);
                cbAbilities.setBounds(new Rectangle(new Point(5, 60), cbAbilities.getPreferredSize()));

                //---- lblSpeed ----
                lblSpeed.setText("Mouse speed");
                contentPanel.add(lblSpeed);
                lblSpeed.setBounds(175, 5, 70, lblSpeed.getPreferredSize().height);

                //---- cmbSpeed ----
                cmbSpeed.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Very slow",
                        "Slow",
                        "Default",
                        "Fast",
                        "Very fast"
                }));
                cmbSpeed.setSelectedIndex(2);
                contentPanel.add(cmbSpeed);
                cmbSpeed.setBounds(175, 20, 75, 30);

                //---- cbSpins ----
                cbSpins.setText("Claim spins");
                contentPanel.add(cbSpins);
                cbSpins.setBounds(95, 60, 85, 24);

                //---- lblNote ----
                lblNote.setText("Have space in inventory");
                contentPanel.add(lblNote);
                lblNote.setBounds(10, 120, 120, 25);

                //======== scrollPane2 ========
                {

                    //---- list2 ----
                    list2.setVisibleRowCount(4);
                    list2.setModel(new AbstractListModel<String>() {
                        String[] values = {
                         /*   "Adamant arrow",
                            "Chaos rune",
                            "Coal",
                            "Cosmic rune",
                            "Law rune",
                            "Clue scroll (2)",
                            "Mithril arrow",
                            "Rune essence",
                            "Top of sceptre"      */
                                Loot.ADAMANT_ARROW.name(),
                                Loot.CHAOS_RUNE.name(),
                                Loot.COAL.name(),
                                Loot.COSMIC_RUNE.name(),
                                Loot.LAW_RUNE.name(),
                                Loot.MITHRIL_ARROW.name(),
                                Loot.MEDIUM_CLUE_SCROLL.name(),
                                Loot.RUNE_ESSENCE.name(),
                                Loot.TOP_SCEPTRE.name()
                        };
                        @Override
                        public int getSize() { return values.length; }
                        @Override
                        public String getElementAt(int i) { return values[i]; }
                    });
                    scrollPane2.setViewportView(list2);
                }
                contentPanel.add(scrollPane2);
                scrollPane2.setBounds(160, 110, 91, 70);

                //---- checkBox1 ----
                checkBox1.setText("Rejuvenate (60%)");
                contentPanel.add(checkBox1);
                checkBox1.setBounds(70, 85, 130, 25);

                //---- checkBox2 ----
                checkBox2.setText("Pick charms");
                contentPanel.add(checkBox2);
                checkBox2.setBounds(175, 60, 85, checkBox2.getPreferredSize().height);

                //---- label1 ----
                label1.setText("for loot/charms");
                contentPanel.add(label1);
                label1.setBounds(30, 140, 80, label1.getPreferredSize().height);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- btnStart ----
                btnStart.setText("Start!");
                btnStart.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startActionPerformed(e);
                    }
                });
                buttonBar.add(btnStart, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Azmat Habibullah
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel lblEat;
    private JSlider jsEat;
    private JCheckBox cbAbilities;
    private JLabel lblSpeed;
    private JComboBox<String> cmbSpeed;
    private JCheckBox cbSpins;
    private JLabel lblNote;
    private JScrollPane scrollPane2;
    private JList<String> list2;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JLabel label1;
    private JPanel buttonBar;
    private JButton btnStart;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
