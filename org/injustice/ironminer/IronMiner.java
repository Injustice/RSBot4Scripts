package org.injustice.ironminer;

import org.injustice.ironminer.nodes.antiban.Antiban;
import org.injustice.ironminer.nodes.banking.Daemonheim;
import org.injustice.ironminer.nodes.banking.WizardsTower;
import org.injustice.ironminer.nodes.dropping.MouseKeys;
import org.injustice.ironminer.nodes.dropping.Regular;
import org.injustice.ironminer.nodes.mining.M1D1;
import org.injustice.ironminer.nodes.mining.Mine;
import org.injustice.ironminer.nodes.mining.PowerMine;
import org.injustice.ironminer.nodes.teleporting.Ardougne;
import org.injustice.ironminer.ui.GUI;
import org.injustice.ironminer.ui.Paint;
import org.injustice.ironminer.util.methods.Methods;
import org.injustice.ironminer.util.vars.Constants;
import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Manifest(authors = {"Injustice"}, name = "Iron Miner", description = "Mines in Ardougne", version = 1.0)
public class IronMiner extends ActiveScript implements PaintListener {
    public static final double VERSION = 1.0;

    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;

    public final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public final void submit(final Job job) {
        getContainer().submit(job);
    }


    public void onStart() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Methods.s("[STARTING] In setup");
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (Game.isLoggedIn()) Game.logout(true);
                }
            }
        });
        Dynamics.startLevel = Skills.getRealLevel(Skills.MINING);
        Dynamics.startExp = Skills.getExperience(Skills.MINING);
        Dynamics.orePrice = GeItem.lookup(Constants.IRON_ORE_ID).getPrice();
        provide(new Daemonheim(),
                new Ardougne(),
                new WizardsTower(),
                new M1D1(),
                new PowerMine(),
                new Mine(),
                new MouseKeys(),
                new Antiban(),
                new Regular());
    }


    @Override
    public int loop() {
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        return Random.nextInt(10, 50);
    }

    @Override
    public void onRepaint(Graphics g) {
        Paint.onRepaint(g);
    }

}