package net.nunnsy.teloschopper.framework;


public class StrategyThread implements Runnable {
	
	Strategy strategy;
	
	public StrategyThread(Strategy s) {
		strategy = s;
	}

	@Override
	public void run() {
		strategy.started();
		strategy.getTask().run();
		strategy.finished();
	}
}
