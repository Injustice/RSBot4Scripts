package org.injustice.wolfkiller;

import org.injustice.wolfkiller.strat.*;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", name = "WolfKiller", description = "Kills wolves in Taverly, collects bones and banks", version = WolfKiller.version)
public class WolfKiller extends ActiveScript implements MouseListener, PaintListener{
    final static double version = 0.1;
    private final List<Node> jobsCollection = Collections
            .synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;

    public final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection
                .size()]));

    }

    public final void submit(final Job job) {
        getContainer().submit(job);
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
    public void onStart() {
        provide(new Attack(), new WalkBank(), new WalkMountain(), new Banking(), new Loot());
     }

    public void onStop() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRepaint(Graphics graphics) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
