package net.nunnsy.teloschopper.utility.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import net.nunnsy.teloschopper.strategy.skill.StratChop;
import net.nunnsy.teloschopper.strategy.skill.StratTreeTrack;
import net.nunnsy.teloschopper.strategy.unload.StratFire;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.stats.Stats;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class Paint {
	
	private static Font Title = new Font("Arial", 1, 12);
	private static Font Stat = new Font("Arial", 0, 10);
	private static DecimalFormat format = new DecimalFormat("#.#");

	public static void paint(Graphics g) {
		if (StratWalk.getStartTile() != null && Game.isLoggedIn()) {
			// PaintUtil tiles
			paintTiles(g);
			
			// PaintUtil trees
			paintTrees(g);
		}
		
		// PaintUtil mouse
		paintMouse(g);
		
		// PaintUtil stats
		paintBackground(g);
		paintStats(g);
	}

	private static void paintStats(Graphics g) {
		g.setFont(Stat);
		g.setColor(Color.WHITE);
		
		g.drawString("Time Running: " + formatTime(Stats.getElapsedTime()), 8, 90);
		g.drawString("State: " + Dynamics.getState().getStateName(), 8, 105);
		
		if (Dynamics.isScriptStarted()) {
			g.drawString("Gained XP: " + Stats.getXPGained(), 8, 125);
			g.drawString("XP/Hour: " + Double.parseDouble(format.format(Stats.getXPhour())), 8, 140);
			
			g.drawString("Gained Logs: " + Stats.getLogsChopped(), 8, 160);
			g.drawString("Logs/Hour: " + Double.parseDouble(format.format(Stats.getLogshour())), 8, 175);
			
			g.drawString("Gained Money: " + Stats.getGainedMoney(), 8, 195);
			g.drawString("Money/Hour: " + Double.parseDouble(format.format(Stats.getMoneyHour())), 8, 210);
			
			g.drawString("Current Level: " + Stats.getLevel(), 8, 230);
			g.drawString("Gained Levels: " + Stats.getGainedLevels(), 8, 245);
			
			g.drawString("Tree: " + Dynamics.getTree().getName(), 8, 265);
			g.drawString("Method: " + Dynamics.getMethod().getUnloadName(), 8, 280);
		}
	}

	private static void paintMouse(Graphics g) {
		int x = Mouse.getX();
		int y = Mouse.getY();
		g.setColor(Color.WHITE);
		g.fillRect(x - 1, y - 10, 3, 21);
		g.fillRect(x - 10, y - 1, 21, 3);
	}

	private static void paintBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 50, 140, 237);
		
		g.setColor(Color.BLACK);
		g.fillRect(2, 52, 136, 234);

		g.setColor(Color.GREEN);
		g.setFont(Title);
		g.drawString("TelosChopper", 30, 70);
	}

	private static void paintTiles(Graphics g) {
		
		g.setColor(Color.BLUE);
		for (Tile t : StratWalk.getBoundingTiles()) {
			t.draw(g);
		}
		
		if (StratFire.getFire() != null) {
			g.setColor(Color.RED);
			StratFire.getFire().getLocation().draw(g);
		}

		if (StratChop.getCurrentTree() != null) {
			g.setColor(Color.GREEN);
			for (Tile t : StratChop.getCurrentTree().getArea().getBoundingTiles()) {
				t.draw(g);
			}
		}

		if (StratChop.getNextTree() != null) {
			g.setColor(Color.YELLOW);
			for (Tile t : StratChop.getNextTree().getArea().getBoundingTiles()) {
				t.draw(g);
			}
		}
		
		g.setColor(Color.ORANGE);
		Players.getLocal().getLocation().draw(g);
	}
	
	private static void paintTrees(Graphics g) {
		if (StratTreeTrack.getDeadTrees().size() != 0) {
			for (int i = 0; i < StratTreeTrack.getDeadTrees().size(); i++) {
				g.setColor(Color.RED);
				g.drawString(((System.currentTimeMillis() - StratTreeTrack.getDeadTrees().get(i).getTimeChopped()) / 1000) + "s",
						StratTreeTrack.getDeadTrees().get(i).getEntity().getCentralPoint().x,
						StratTreeTrack.getDeadTrees().get(i).getEntity().getCentralPoint().y - 20);
			}
		}
	}
	
	private static String formatTime(final long milliseconds) {
		final long t_seconds = milliseconds / 1000;
		final long t_minutes = t_seconds / 60;
		final long t_hours = t_minutes / 60;
		final long seconds = t_seconds % 60;
		final long minutes = t_minutes % 60;
		final long hours = t_hours % 500;
		return hours + ":" + minutes + ":" + seconds;
	}
}
