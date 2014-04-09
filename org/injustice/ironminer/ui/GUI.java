
/*
 * Created by JFormDesigner on Wed Apr 10 20:21:01 BST 2013
 */

package org.injustice.ironminer.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.injustice.ironminer.util.vars.Dynamics.*;

/**
 * @author Azmat Habibullah
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();
    }

    public void setOptions() {
        if (btnDaemon.isSelected()) {
            bankAtDaemonheim = true;
            bankAtWizardsTower = false;
            regularDrop = false;
            actionBarDrop = false;
            mousekeysDrop = false;
            mineOneDropOne = false;
        } else if (btnTower.isSelected()) {
            bankAtDaemonheim = false;
            bankAtWizardsTower = true;
            regularDrop = false;
            actionBarDrop = false;
            mousekeysDrop = false;
            mineOneDropOne = false;
        } else if (btnRegular.isSelected()) {
            bankAtDaemonheim = false;
            bankAtWizardsTower = false;
            regularDrop = true;
            actionBarDrop = false;
            mousekeysDrop = false;
            mineOneDropOne = false;
        } else if (btnAction.isSelected()) {
            bankAtDaemonheim = false;
            bankAtWizardsTower = false;
            regularDrop = false;
            actionBarDrop = true;
            mousekeysDrop = false;
            mineOneDropOne = false;
        } else if (btnMouseKeys.isSelected()) {
            bankAtDaemonheim = false;
            bankAtWizardsTower = false;
            regularDrop = false;
            actionBarDrop = false;
            mousekeysDrop = true;
            mineOneDropOne = false;
        }
        if (cbAntiban.isSelected())
            doAntiban = true;
    }

    private void btnBankActionPerformed(ActionEvent e) {
        if (btnBank.isSelected()) {
            btnTower.setEnabled(true);
            btnDaemon.setEnabled(true);
            btnAction.setEnabled(false);
            btnDrop.setSelected(false);
            btnMouseKeys.setEnabled(false);
            btnRegular.setEnabled(false);
        } else {
            btnTower.setEnabled(false);
            btnDaemon.setEnabled(false);
        }
    }

    private void btnDropActionPerformed(ActionEvent e) {
        if (btnDrop.isSelected()) {
            btnTower.setEnabled(false);
            btnDaemon.setEnabled(false);
            btnAction.setEnabled(true);
            btnDrop.setEnabled(true);
            btnMouseKeys.setEnabled(true);
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        setOptions();
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Azmat Habibullah
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        Title = new JLabel();
        btnBank = new JRadioButton();
        btnDrop = new JRadioButton();
        btnNote = new JLabel();
        btnDaemon = new JRadioButton();
        btnTower = new JRadioButton();
        cbAntiban = new JCheckBox();
        cbScreen = new JCheckBox();
        btnAction = new JRadioButton();
        btnMouseKeys = new JRadioButton();
        btnRegular = new JRadioButton();
        buttonBar = new JPanel();
        button1 = new JButton();

        //======== this ========
        setTitle("Iron Miner GUI");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- Title ----
                Title.setText("Injustice's Ardougne Iron Miner");
                Title.setFont(Title.getFont().deriveFont(Title.getFont().getSize() + 10f));
                contentPanel.add(Title);
                Title.setBounds(35, 0, 300, 25);

                //---- btnBank ----
                btnBank.setText("Bank");
                btnBank.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnBankActionPerformed(e);
                    }
                });
                contentPanel.add(btnBank);
                btnBank.setBounds(5, 40, 65, 23);

                //---- btnDrop ----
                btnDrop.setText("Drop");
                btnDrop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnDropActionPerformed(e);
                    }
                });
                contentPanel.add(btnDrop);
                btnDrop.setBounds(200, 40, 65, 20);

                //---- btnNote ----
                btnNote.setText(" Banking requires Ardougne cape");
                contentPanel.add(btnNote);
                btnNote.setBounds(15, 120, 165, btnNote.getPreferredSize().height);

                //---- btnDaemon ----
                btnDaemon.setText("Daemonheim - Ring of kinship");
                contentPanel.add(btnDaemon);
                btnDaemon.setBounds(25, 65, btnDaemon.getPreferredSize().width, 28);

                //---- btnTower ----
                btnTower.setText("Wizards Tower - Wicked hood");
                contentPanel.add(btnTower);
                btnTower.setBounds(25, 90, 175, 28);

                //---- cbAntiban ----
                cbAntiban.setText("Antiban");
                contentPanel.add(cbAntiban);
                cbAntiban.setBounds(10, 160, 70, cbAntiban.getPreferredSize().height);

                //---- cbScreen ----
                cbScreen.setText("Screenshot on stop");
                contentPanel.add(cbScreen);
                cbScreen.setBounds(new Rectangle(new Point(215, 160), cbScreen.getPreferredSize()));

                //---- btnAction ----
                btnAction.setText("Actionbar");
                contentPanel.add(btnAction);
                btnAction.setBounds(new Rectangle(new Point(230, 65), btnAction.getPreferredSize()));

                //---- btnMouseKeys ----
                btnMouseKeys.setText("Mousekeys");
                contentPanel.add(btnMouseKeys);
                btnMouseKeys.setBounds(230, 90, 85, 28);

                //---- btnRegular ----
                btnRegular.setText("Regular");
                contentPanel.add(btnRegular);
                btnRegular.setBounds(230, 115, 85, 28);

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

                //---- button1 ----
                button1.setText("Start!");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button1ActionPerformed(e);
                    }
                });
                buttonBar.add(button1, new GridBagConstraints(0, 0, 2, 2, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);

        //---- btngrpBanking ----
        ButtonGroup btngrpBanking = new ButtonGroup();
        btngrpBanking.add(btnBank);
        btngrpBanking.add(btnDrop);

        //---- btngrpDroppingOptions ----
        ButtonGroup btngrpDroppingOptions = new ButtonGroup();
        btngrpDroppingOptions.add(btnDaemon);
        btngrpDroppingOptions.add(btnTower);

        //---- btngrpBankingOptions ----
        ButtonGroup btngrpBankingOptions = new ButtonGroup();
        btngrpBankingOptions.add(btnAction);
        btngrpBankingOptions.add(btnMouseKeys);
        btngrpBankingOptions.add(btnRegular);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Azmat Habibullah
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel Title;
    private JRadioButton btnBank;
    private JRadioButton btnDrop;
    private JLabel btnNote;
    private JRadioButton btnDaemon;
    private JRadioButton btnTower;
    private JCheckBox cbAntiban;
    private JCheckBox cbScreen;
    private JRadioButton btnAction;
    private JRadioButton btnMouseKeys;
    private JRadioButton btnRegular;
    private JPanel buttonBar;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
