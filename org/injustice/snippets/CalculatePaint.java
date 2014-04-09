package org.injustice.snippets;
/*
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.tab.Skills;


public class CalculatePaint extends LoopTask {
	private static final CalculatePaint INSTANCE = new CalculatePaint();

	private CalculatePaint() { }

	public static CalculatePaint get(int delay) {
		CalculatePaint.DELAY = delay;
		return INSTANCE;
	}

	public static CalculatePaint get() {
		return INSTANCE;
	}

	public static int DELAY = 500;

	public final int naturePrice = GE.lookup(561).getPrice();
	public final int essPrice = GE.lookup(7936).getPrice();
	public int naturesPerHour;
	public int ess;
	public int gpGained, gpPerHour;
	public int profit, profitPerHour;
	public final int lvlStart = Skills.getRealLevel(Skills.RUNECRAFTING);
	public int lvl, lvlGained;
	public final int expStart = Skills.getExperience(Skills.RUNECRAFTING);
	public int expGained, expPerHour;
	public long expNeeded;
	public long millisToLvl;
	public float percentToLvl;
	public int barSize;

	@Override
	public int loop() {
		final int natures = CountNatures.naturesMade();
		final float timeHours = Script.getPaint().getTime().timeHours();
		final long timeMillis = Script.getPaint().getTime().timeMillis();
		naturesPerHour = Math.round(CountNatures.naturesMade() / timeHours);
		gpGained = natures * naturePrice;
		gpPerHour = Math.round(gpGained / timeHours);
		ess = Skills.getRealLevel(Skills.RUNECRAFTING) < 91 ? natures : natures / 2;
		profit = gpGained - (ess * essPrice);
		profitPerHour = Math.round(profit / timeHours);
		lvl = Skills.getRealLevel(Skills.RUNECRAFTING);
		lvlGained = lvl - lvlStart;
		expGained = Skills.getExperience(Skills.RUNECRAFTING) - expStart;
		expPerHour = Math.round(expGained / timeHours);
		expNeeded = Skills.getExperienceToLevel(Skills.RUNECRAFTING, lvl + 1);
		percentToLvl = (float) Skills.getExperience(Skills.RUNECRAFTING) / Skills.getExperienceRequired(lvl + 1);
		barSize = Math.round(254 * percentToLvl);
		millisToLvl = (expGained == 0 || timeMillis == 0) ? 0 : Math.round(expNeeded / ((double) expGained / timeMillis));
		return DELAY;
	}
}
*/