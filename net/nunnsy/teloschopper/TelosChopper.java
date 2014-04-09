package net.nunnsy.teloschopper;

import java.awt.Graphics;

import javax.swing.SwingUtilities;

import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.framework.StrategyHandler;
import net.nunnsy.teloschopper.gui.OptionsGUI;
import net.nunnsy.teloschopper.strategy.antiban.StratAntiban;
import net.nunnsy.teloschopper.strategy.pickup.StratPickup;
import net.nunnsy.teloschopper.strategy.skill.StratChop;
import net.nunnsy.teloschopper.strategy.skill.StratTreeTrack;
import net.nunnsy.teloschopper.strategy.unload.StratBank;
import net.nunnsy.teloschopper.strategy.unload.StratDrop;
import net.nunnsy.teloschopper.strategy.unload.StratFire;
import net.nunnsy.teloschopper.strategy.walk.StratWalk;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.Failsafe;
import net.nunnsy.teloschopper.utility.paint.Paint;
import net.nunnsy.teloschopper.utility.state.StateCheck;
import net.nunnsy.teloschopper.utility.stats.Counter;
import net.nunnsy.teloschopper.utility.stats.Stats;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;

@Manifest(
		authors = "Nunnsy",
		name = "TelosChopper",
		version = 1.38d,
		description = "An extremely fast wood chopper! Universal, banking, dropping, bonfiring and more! (Please look at setup instructions on thread)",
		topic = 787554,
		website = "http://www.powerbot.org/community/topic/787554-sdn-antiban-teloschopper-a-very-fast-powerchopper-bonfires-fast/",
		vip = false)

public class TelosChopper extends ActiveScript implements PaintListener, MessageListener {
	
	private long loginTime = 0;
	private OptionsGUI optionsGUI;
	private StrategyHandler strategyHandler = new StrategyHandler();

	@Override
    public void onStart() {
		Stats.scriptStarted();
		
		Mouse.setSpeed(Speed.FAST);
		
		runGUI();
		
		strategyHandler.provide(new StratAntiban());
		
		strategyHandler.provide(new StratPickup());
	
		strategyHandler.provide(new StratChop());
		strategyHandler.provide(new StratTreeTrack());
		
		strategyHandler.provide(new StratDrop());
		strategyHandler.provide(new StratFire());
		strategyHandler.provide(new StratBank());
		
		strategyHandler.provide(new StratWalk());
    }

	@Override
	public int loop() {
		
		if (loginTime == 0 && Game.isLoggedIn()) {
			loginTime = System.currentTimeMillis();
		}
		
		if (StratWalk.getStartTile() == null &&
				StratWalk.getFurthestTile() != 0 &&
				System.currentTimeMillis() > loginTime + 2000 &&
				loginTime != 0) {
			StratWalk.setStartTile(Players.getLocal().getLocation());
			Stats.setStartLevel(Skills.getLevel(Skills.WOODCUTTING));
		}
		
		if (Dynamics.isScriptStarted() && Game.isLoggedIn()) {
			for (Strategy strategy : strategyHandler.getStrategies()) {
				if (!strategy.isRunning() && strategy.getCondition().validate()) {
					System.out.println(System.currentTimeMillis() + "       " + strategy.getClass().getName());
					strategyHandler.ExecuteStrategy(strategy);
				}
			}
			
			StateCheck.run();
		}
		
		Stats.run();
		
		if (Failsafe.run()) {
			Game.logout(true);
			this.shutdown();
		}
		
        return Random.nextInt(10, 50);
	}
	
	
	private void runGUI() {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					optionsGUI = new OptionsGUI();
					optionsGUI.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
		Paint.paint(g);
	}

	@Override
	public void messageReceived(MessageEvent e) {
		Counter.process(e.getMessage());
	}
}
