package org.injustice.fighter;

import org.injustice.fighter.node.Attacker;
import org.injustice.fighter.node.Eater;
import org.injustice.fighter.node.abilities.HandleAbilities;
import org.injustice.fighter.node.failsafes.FailsafeDoor;
import org.injustice.fighter.node.loot.CharmLooter;
import org.injustice.fighter.node.loot.ClueScrollLooter;
import org.injustice.fighter.node.loot.CustomLoot;
import org.injustice.fighter.ui.FighterGUI;
import org.injustice.fighter.ui.FighterPaint;
import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.framework.Strategy;
import org.injustice.framework.StrategyHandler;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.randoms.SpinTickets;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", description = "Kills Catablepons in SoS", name = "Catable Killer", version = 5.0)
public class Fighter extends ActiveScript implements PaintListener, MessageListener {
 /*   private final Node[] nodes = { new RejuvenateUser(), new Attacker(),
            new CharmLooter(), new Eater(), new FailsafeDoor(), new AbilityHandler()};
 */

    StrategyHandler s = new StrategyHandler();

    private int getExp(int skill) {
        return Skills.getExperience(skill);
    }

    private void setTotalExp() {
        Var.totalExp = getExp(Skills.ATTACK) +
                getExp(Skills.MAGIC) +
                getExp(Skills.DEFENSE) +
                getExp(Skills.CONSTITUTION) +
                getExp(Skills.STRENGTH) +
                getExp(Skills.RANGE) +
                getExp(Skills.MAGIC);
    }

    private int getLvl(int skill) {
        return Skills.getRealLevel(skill);
    }

    private void setTotalLvl() {
        Var.totalLvl = getLvl(Skills.ATTACK) +
                getLvl(Skills.MAGIC) +
                getLvl(Skills.DEFENSE) +
                getLvl(Skills.CONSTITUTION) +
                getLvl(Skills.STRENGTH) +
                getLvl(Skills.RANGE) +
                getLvl(Skills.MAGIC);
    }

    @Override
    public void onStart() {
        Var.status = "[ONSTART] Starting...";
        s.provide(new HandleAbilities());
        s.provide(new FailsafeDoor());
        s.provide(new CharmLooter());
        s.provide(new ClueScrollLooter());
        s.provide(new Attacker());
        s.provide(new Eater());
        s.provide(new CustomLoot());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Var.status = "[STARTUP] In setup";
                try {
                    FighterGUI frame = new FighterGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        do sleep(500); while (!Var.guiDone);
        Mouse.setSpeed(UserSettings.mouseSpeed);
        Util.debug();
        Util.l.showInfo("------------------------------------\n");
        Util.l.showInfo("Welcome to Injustice's CatableKiller\n");
        Util.l.showInfo("------------------------------------\n");
        Util.l.showInfo("Updates:\nv3.0:\nAbilities work!\nAll rooms supported!" +
                "\nRemoval of logout failsafe (didn't work anyway)\n" +
                "Gate actually works!\n\nv3.01\nGate interaction fix\n" +
                "\nAddition of debug window thanks to Wyn\nv4.0\nGUI\n" +
                "Ticket claiming\nOption to use abilities\nMouse speed\n" +
                "Eat percent\nv5.0:\nNew GUI\nOption to use rejuvenate\n" +
                "Custom loot!\nOn screen abilitybar fix\n" +
                "Post any bugs on the thread.\n");
        if (UserSettings.claimSpins) {
            getContainer().submit(new Task() {
                @Override
                public void execute() {
                    sleep(5000);
                    Environment.enableRandom(SpinTickets.class, false);
                }
            });
        }
        if (Util.isReady()) {
            Var.startTile = Players.getLocal().getLocation();
            setTotalExp();
            setTotalLvl();
            Var.startConstitutionExp = getExp(Skills.CONSTITUTION);
            Var.startConstitutionLvl = getLvl(Skills.CONSTITUTION);
        } else {
            Util.waitFor(new Condition() {
                @Override
                public boolean validate() {
                    Var.status = "[STARTUP] Not logged in";
                    return Util.isReady();
                }
            }, 5000);
        }

    }

    @Override
    public void onStop() {
        Util.ExpStats e = new Util.ExpStats();
        Var.status = "[STOPPING]";
        int expGained = e.getTotalExp() - Var.totalExp;
        int killed = expGained / 133;
        int constExp = e.getExp(Skills.CONSTITUTION) - Var.startConstitutionExp;
        Util.l.showInfo("---------------------------------");
        Util.l.showInfo("--------------STATS--------------");
        Util.l.showInfo("Killed: " + (expGained / 133));
        Util.l.showInfo("Killed PH: " + (int) (killed * 3600000d / Var.runTime.getElapsed()));
        Util.l.showInfo("Exp gained: " + expGained);
        Util.l.showInfo("Exp PH: " + (int) (expGained * 3600000d / Var.runTime.getElapsed()));
        Util.l.showInfo("Const exp: " + constExp);
        Util.l.showInfo("Const PH: " + (int) (constExp * 3600000d / Var.runTime.getElapsed()));
        Util.l.showInfo("Levels: " + (e.getTotalLvl() - Var.totalLvl));
//        System.out.println("Charms: " + Var.charmsLooted);
//        System.out.println("Rejuvs: " + Var.rejuvs);
        Util.l.showInfo("Runtime: " + Var.runTime.toElapsedString());
        Util.l.showInfo("--------------STATS--------------");
        Util.l.showInfo("---------------------------------");
        Util.l.showInfo("Thanks for using my script!");
        Util.l.showInfo("Please post proggies!");
    }


    @Override
    public int loop() {
        //       for(Node n : nodes) {
        try {
            for (Strategy strat : s.getStrategies()) {
                if (strat.activate() && Util.isReady() && Var.guiDone &&
                        Var.totalExp != 0 && Var.totalLvl != 0 && // paint counters
                        Var.startConstitutionExp != 0 &&
                        Var.startConstitutionLvl != 0 &&
                        (!strat.isRunning() && strat.getCondition().activate())) {
                    s.executeStrategy(strat);

                } else if (Var.totalLvl == 0) {
                    setTotalLvl();;
                } else if (Var.totalExp == 0) {
                    setTotalExp();
                } else if (Var.startConstitutionExp == 0) {
                    Var.startConstitutionExp = getExp(Skills.CONSTITUTION);
                } else if (Var.startConstitutionLvl == 0) {
                    Var.startConstitutionLvl = getExp(Skills.CONSTITUTION);
                } else if (!Var.guiDone && !Var.status.equals("[GUI] Waiting")) {
                    Var.status = "[GUI] Waiting";
                    Util.debug();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 50;
    }

    @Override
    public void onRepaint(Graphics g) {
        FighterPaint.onRepaint(g);
    }

    @Override
    public void messageReceived(MessageEvent e) {
        String s = "reach";
        if (e.toString().contains(s)) {
            Var.cannotReach = true;
        }
    }
}
