package org.injustice.singlefiles;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Manifest(authors = {"Injustice"}, name = "Coifs", description = "Makes coifs", version = 0.1)
public class Coif extends ActiveScript implements PaintListener {
    int coif = 1169;
    int leather = 1741;
    String s = "Starting";
    int startExp;

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
        startExp = Skills.getExperience(Skills.CRAFTING);
        provide(new Banking(), new Craft());
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
        int coifsMade = (Skills.getExperience(Skills.CRAFTING) - startExp) / 37;
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 250, 50);
        g.fillRect(0, 0, 250, 50);
        g.setColor(Color.WHITE);
        g.drawString("Exp: " + (Skills.getExperience(Skills.CRAFTING) - startExp), 25, 25);
        g.drawString("Status " + s, 25, 35);
        g.drawString("Made: " + coifsMade, 25, 45);
        g.drawString("Profit: " + coifsMade * 58, 25, 55);

        drawMouse(g);
    }

    private class Banking extends Node {
        @Override
        public boolean activate() {
            return !Inventory.contains(leather);
        }

        @Override
        public void execute() {
            if (Bank.open()) {
                s = "Open";
                sleep(1000);
                if (Bank.depositInventory()) {
                    s = "Depositing";
                    sleep(750);
                    if (Bank.withdraw(leather, Bank.Amount.ALL)) {
                        s = "Withdrawing";
                        sleep(1000);
                        if (Bank.close()) {
                            s = "Closing";
                            sleep(1000);
                        }
                    }
                }
            }
        }
    }

    private class Craft extends Node {
        @Override
        public boolean activate() {
            return Inventory.contains(leather);
        }

        @Override
        public void execute() {
            Item i = Inventory.getItem(leather);
            if (i.getWidgetChild().interact("Craft")) {
                s = "Interacting";
                sleep(1000);
                if (Widgets.get(1370, 40).getChild(3).interact("Make")) {
                    s = "Making";
                    sleep(50000);
                }
            }
        }
    }

    private static void drawMouse(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        Color outerCircle = gradient[0];
        Color innerCircle = Color.white;
        g1.setColor(gradient[0]);
        int circleRadius = 7;
        int circleDiameter = circleRadius * 2;
        g1.drawLine(p.x + circleRadius, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x - 2000, p.y, p.x - circleRadius, p.y);
// Vertical
        g1.drawLine(p.x, p.y + circleRadius, p.x, p.y + 2000);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y - circleRadius);
        for (int r = gradient.length - 1; r > 0; r--) {
            int steps = 200 / ((gradient.length - 1) * 2);
            for (int i = steps; i > 0; i--) {
                float ratio = (float) i / (float) steps;
                int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
                        .getRed() * (1 - ratio));
                int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
                        .getGreen() * (1 - ratio));
                int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
                        .getBlue() * (1 - ratio));
                Color stepColor = new Color(red, green, blue);
                g1.setColor(stepColor);
// Horizontal
                g1.drawLine(p.x + circleRadius, p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        - circleRadius, p.y);
// Vertical
                g1.drawLine(p.x, p.y + circleRadius, p.x, p.y
                        + ((i * 5) + (100 * r)));
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        - circleRadius);
            }
        }
        g1.setColor(outerCircle);
        final long mpt = System.currentTimeMillis() - Mouse.getPressTime();
        if (Mouse.getPressTime() == -1 || mpt >= 200) {
            g1.drawOval(p.x - circleRadius / 3, p.y - circleRadius / 3,
                    circleDiameter / 3, circleDiameter / 3);
        }
        if (mpt < 200) {
            g1.drawLine(p.x - circleRadius, p.y + circleRadius, p.x
                    + circleRadius, p.y - circleRadius);
            g1.drawLine(p.x - circleRadius, p.y - circleRadius, p.x
                    + circleRadius, p.y + circleRadius);
        }
        g1.setColor(outerCircle);
        g1.drawOval(p.x - circleRadius, p.y - circleRadius, circleDiameter,
                circleDiameter);
    }

}