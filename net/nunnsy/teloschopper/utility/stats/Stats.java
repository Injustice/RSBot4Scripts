package net.nunnsy.teloschopper.utility.stats;

import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.utility.Dynamics;

import org.powerbot.game.api.methods.tab.Skills;

public class Stats {
	
	private static long startTime;
	private static long elapsedTime;
	
	
	private static double hourlyXP = 0;
	private static double hourlyLogs = 0;
	private static double hourlyMoney = 0;
	
	private static double gainedXP = 0;
	private static int gainedLogs = 0;
	private static int gainedMoney = 0;
	
	private static int logPrice = 0;
	
	private static int startLevel = 0;
	
	public static void run() {
		elapsedTime = System.currentTimeMillis() - startTime;
		
		if (elapsedTime > 0) {
			if (Dynamics.getMethod() == UnloadMethod.BANK) {
				gainedMoney = logPrice * gainedLogs;
			}
			
			hourlyXP = gainedXP * (3600000D / elapsedTime);
			hourlyLogs = gainedLogs * (3600000D / elapsedTime);
			hourlyMoney = gainedMoney * (3600000D / elapsedTime);
		}
	}
	
	public static void scriptStarted() {
		startTime = System.currentTimeMillis();
	}
	
	public static long getElapsedTime() {
		return elapsedTime;
	}
	
	public static void addXP(double i) {
		gainedXP += i;
	}
	
	public static double getXPGained() {
		return gainedXP;
	}
	
	public static double getXPhour() {
		return hourlyXP;
	}
	
	public static void logChopped() {
		gainedLogs++;
	}
	
	public static int getLogsChopped() {
		return gainedLogs;
	}
	
	public static double getLogshour() {
		return hourlyLogs;
	}

	public static void setStartLevel (int level) {
		startLevel = level;
	}
	
	public static int getLevel() {
		return Skills.getLevel(Skills.WOODCUTTING);
	}
	
	public static int getGainedLevels() {
		return getLevel() - startLevel;
	}
	
	public static int getGainedMoney() {
		return gainedMoney;
	}
	
	public static double getMoneyHour() {
		return hourlyMoney;
	}
	
	public static void setLogPrice(int price) {
		logPrice = price;
	}
}
