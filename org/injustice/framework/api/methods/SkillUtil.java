package org.injustice.framework.api.methods;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/05/13
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */

/**
 * Methods to do with Skills
 */
public class SkillUtil {
    private int skill;
    private int exp;
    private int level;
    private int realLevel;
    private int expTNL;
    private int percentTNL;
    private int startExp;
    private int startLevel;
    private int startRealLevel;
    private String timeTNL;
    private Timer runTime = new Timer(0);

    public SkillUtil(int skill) {
        this.skill = skill;
        this.startExp = getExp();
        this.exp = getExp();
        this.startLevel = getLevel();
        this.expTNL = getExpTNL();
        this.level = getLevel();
        this.startRealLevel = getRealLevel();
        this.realLevel = getRealLevel();
        this.timeTNL = getTimeTNL();
        this.percentTNL = getPercentTNL();
    }

    public int getStartExp() {
        return startExp;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public int getStartRealLevel() {
        return startRealLevel;
    }

    public int getExp() {
        return Skills.getExperience(skill);
    }

    public int getExpTNL() {
        return Skills.getExperienceToLevel(this.getLevel(), this.getLevel() + 1);
    }

    public int getRealLevel() {
        return Skills.getRealLevel(skill);
    }

    public int getLevel() {
        return Skills.getLevel(skill);
    }

    public int getExpGain() {
        return getExp() - getStartExp();
    }

    public int getLevelGain() {
        return getLevel() - getStartLevel();
    }

    public int getRealLevelGain() {
        return getRealLevel() - getStartRealLevel();
    }

    public int getPercentTNL() {
        return getPercentToNextLevel();
    }

    public int getExpPerHour() {
        return SkillUtil.getExpHour(getExpGain(), runTime.getElapsed());
    }

    public String getTimeTNL() {
        if (getExpPerHour() < 1) {
            return Time.format(0L);
        }
        return Time.format((long) getExpTNL() * 3600000 / getExpPerHour());
    }

    public Timer getRunTime() {
        return runTime;
    }

    /**
     * Gets the time to next level
     * @param skill Skill to check
     * @param xpPerHour Experience gained per hour
     * @return Time to next level in (dd:)HH:mm:ss
     */
    public static String getTimeToNextLevel(final int skill, final int xpPerHour) {
        if (xpPerHour < 1) {
            return Time.format(0L);
        }
        return Time.format((long)
                (Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) * 3600000D / xpPerHour));
    }

    /**
     * Gets percent to chosen level
     * @param endLvl Level to get percent to
     * @return Percentage to endLvl
     */
    public int getPercentToLevel(final int endLvl) {
        final int lvl = Skills.getRealLevel(skill);
        if (lvl == 99) {
            return 0;
        }
        final int xpNeeded = Skills.XP_TABLE[endLvl] - Skills.XP_TABLE[lvl];
        if (xpNeeded == 0) {
            return 0;
        }
        final int xpDone = Skills.getExperience(skill)
                - Skills.XP_TABLE[lvl];
        return 100 * xpDone / xpNeeded;
    }

    /**
     * Gets percentage to next level using {@link SkillUtil#getPercentToLevel(int)}
     * @return Percentage to the next level
     */
    public int getPercentToNextLevel() {
        return getPercentToLevel(getLevel() + 1);
    }

    /**
     * Gets the total level
     * @return The total level of the player
     */
    @SuppressWarnings("deprecation")
    public int getTotalLevel() {
        int levels = 0;
        int[] total = Skills.getLevels();
        for (int i : total) {
            levels += i;
        }
        return levels;
    }

    public int getTotalExp() {
        int[] total = Skills.getExperiences();
        int totalExp = 0;
        for (int i : total) {
            totalExp += i;
        }
        return totalExp;
    }

    /**
     * Gets exp per hour
     * @param expGain Exp gained in time
     * @param runTime Time exp has been gained in
     * @return Experience per hour
     */
    public static int getExpHour(int expGain, long runTime) {
        return (int) (expGain * 3600000d / runTime);
    }
}
