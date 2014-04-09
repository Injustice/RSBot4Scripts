package net.nunnsy.teloschopper.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.nunnsy.teloschopper.strategy.antiban.StratAntiban;
import net.nunnsy.teloschopper.strategy.unload.StratBank;
import net.nunnsy.teloschopper.strategy.utility.BankLocation;
import net.nunnsy.teloschopper.strategy.utility.TreeType;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Dynamics;
import javax.swing.JCheckBox;

public class OptionsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] unloadMethod;
	private String[] logsAvailable;
	private String[] banksAvailable;
	
	private JLabel lblTeloswillows;
	private JLabel lblMethod;
	private JLabel lblType;
	private JLabel lblBank;
	private JLabel lblFurthestTile;
	
	private JSlider tileSlider;
	private JButton btnStart;
	
	private JCheckBox chkAntiban;
	private JCheckBox chkNests;
	private JCheckBox chkUrns;
	private JCheckBox chkOrt;
	
	private JComboBox<String> methodSelection;
	private JComboBox<String> logSelection;
	private JComboBox<String> bankSelection;

	
	public OptionsGUI() {
		setTitle("TelosChopper");
		
		loadEnumValues();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 351, 354);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTeloswillows = new JLabel("TelosChopper");
		lblTeloswillows.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTeloswillows.setForeground(Color.WHITE);
		lblTeloswillows.setBackground(Color.BLACK);
		lblTeloswillows.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeloswillows.setFont(new Font("Arial", Font.BOLD, 18));
		lblTeloswillows.setBounds(10, 0, 328, 29);
		contentPane.add(lblTeloswillows);
		
		methodSelection = new JComboBox<String>(unloadMethod);
		methodSelection.setBackground(Color.BLACK);
		methodSelection.setFont(new Font("Arial", Font.PLAIN, 11));
		methodSelection.setBounds(239, 86, 99, 20);
		contentPane.add(methodSelection);
		
		lblMethod = new JLabel("Method:");
		lblMethod.setBackground(Color.BLACK);
		lblMethod.setForeground(Color.WHITE);
		lblMethod.setFont(new Font("Arial", Font.PLAIN, 11));
		lblMethod.setBounds(190, 89, 60, 14);
		contentPane.add(lblMethod);
		
		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Arial", Font.PLAIN, 20));
		btnStart.setBounds(10, 258, 328, 56);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				startPress();
			}
		});
		contentPane.add(btnStart);
		
		logSelection = new JComboBox<String>(logsAvailable);
		logSelection.setBackground(Color.BLACK);
		logSelection.setFont(new Font("Arial", Font.PLAIN, 11));
		logSelection.setBounds(59, 43, 99, 20);
		contentPane.add(logSelection);
		
		lblType = new JLabel("Type:");
		lblType.setBackground(Color.BLACK);
		lblType.setForeground(Color.WHITE);
		lblType.setFont(new Font("Arial", Font.PLAIN, 11));
		lblType.setBounds(10, 46, 60, 14);
		contentPane.add(lblType);
		
		lblFurthestTile = new JLabel("Furthest tile: ");
		lblFurthestTile.setBackground(Color.BLACK);
		lblFurthestTile.setForeground(Color.WHITE);
		lblFurthestTile.setHorizontalAlignment(SwingConstants.CENTER);
		lblFurthestTile.setFont(new Font("Arial", Font.PLAIN, 11));
		lblFurthestTile.setBounds(10, 122, 328, 14);
		contentPane.add(lblFurthestTile);
		
		tileSlider = new JSlider();
		tileSlider.setForeground(Color.WHITE);
		tileSlider.setBackground(Color.BLACK);
		tileSlider.setValue(20);
		tileSlider.setMaximum(50);
		tileSlider.setMinimum(3);
		tileSlider.setBounds(10, 147, 328, 20);
		contentPane.add(tileSlider);
		
		lblFurthestTile.setText("Furthest tile: " + tileSlider.getValue());
		
		lblBank = new JLabel("Bank:");
		lblBank.setForeground(Color.WHITE);
		lblBank.setFont(new Font("Arial", Font.PLAIN, 11));
		lblBank.setBackground(Color.BLACK);
		lblBank.setBounds(10, 89, 60, 14);
		contentPane.add(lblBank);
		
		bankSelection = new JComboBox<String>(banksAvailable);
		bankSelection.setFont(new Font("Arial", Font.PLAIN, 11));
		bankSelection.setBackground(Color.BLACK);
		bankSelection.setBounds(59, 86, 99, 20);
		contentPane.add(bankSelection);
		
		chkAntiban = new JCheckBox("Antiban");
		chkAntiban.setSelected(true);
		chkAntiban.setForeground(Color.WHITE);
		chkAntiban.setBackground(Color.BLACK);
		chkAntiban.setBounds(10, 188, 160, 23);
		contentPane.add(chkAntiban);
		
		chkNests = new JCheckBox("Pickup Nests");
		chkNests.setSelected(true);
		chkNests.setForeground(Color.WHITE);
		chkNests.setBackground(Color.BLACK);
		chkNests.setBounds(10, 214, 160, 23);
		contentPane.add(chkNests);
		
		chkUrns = new JCheckBox("Use Urns");
		chkUrns.setEnabled(false);
		chkUrns.setForeground(Color.WHITE);
		chkUrns.setBackground(Color.BLACK);
		chkUrns.setBounds(172, 188, 160, 23);
		contentPane.add(chkUrns);
		
		chkOrt = new JCheckBox("Pickup Ort");
		chkOrt.setSelected(true);
		chkOrt.setForeground(Color.WHITE);
		chkOrt.setBackground(Color.BLACK);
		chkOrt.setBounds(172, 214, 160, 23);
		contentPane.add(chkOrt);
		
		tileSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				lblFurthestTile.setText("Furthest tile: " + tileSlider.getValue());
			}
		});
	}
	
	private void loadEnumValues() {
		logsAvailable = new String[TreeType.values().length];
		for (int i = 0; i < TreeType.values().length; i++) {
			logsAvailable[i] = TreeType.values()[i].getName();
		}
		
		unloadMethod = new String[UnloadMethod.values().length];
		for (int i = 0; i < UnloadMethod.values().length; i++) {
			unloadMethod[i] = UnloadMethod.values()[i].getUnloadName();
		}
		
		banksAvailable = new String[BankLocation.values().length];
		for (int i = 0; i < BankLocation.values().length; i++) {
			banksAvailable[i] = BankLocation.values()[i].getName();
		}
	}

	private void startPress() {
		Dynamics.setMethod(UnloadMethod.values()[methodSelection.getSelectedIndex()]);
		Dynamics.setTree(TreeType.values()[logSelection.getSelectedIndex()]);
		Dynamics.setOptions(chkNests.isSelected(), chkUrns.isSelected(), chkOrt.isSelected());
		StratBank.setBankLocation(BankLocation.values()[bankSelection.getSelectedIndex()]);
		StratWalk.setFurthestTile(tileSlider.getValue());
		StratAntiban.setAntiban(chkAntiban.isSelected());
		Dynamics.GUICompleted();
		dispose();
	}
}
