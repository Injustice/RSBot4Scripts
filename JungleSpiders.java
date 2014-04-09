import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
 
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
 
 
@Manifest( authors = "Verisign", name = "vJungle Spiders", description = "Kills Jungle Spiders east of Yanille and banks to replenish food", version = 1.0)
public class JungleSpiders extends ActiveScript implements PaintListener, MessageListener {
 
 
    final int FOOD[] = {333, 351, 329, 361, 379, 365, 373, 7946, 385, 697, 391, 15266, 15272} ;
 
    Tile bankTile = new Tile(2610, 3094, 0);
    Tile spiderTile = new Tile(2671, 3088, 0);
    Area littleArea = new Area(new Tile(2681, 3090, 0), new Tile(2683, 3091, 0), new Tile(2687, 3084, 0),
            new Tile(2681, 3075, 0), new Tile(2656, 3084, 0), new Tile(2659, 3091, 0),
            new Tile(2667, 3086, 0), new Tile(2673, 3083, 0), new Tile(2681, 3083, 0),
            new Tile(2682, 3083, 0), new Tile(2681, 3083, 0) );
    Area spiderArea = new Area(new Tile(2674, 3092, 0), new Tile(2670, 3092, 0), new Tile(2667, 3089, 0),
            new Tile(2667, 3086, 0), new Tile(2669, 3086, 0), new Tile(2672, 3083, 0),
            new Tile(2675, 3083, 0) );
    private Tile[] pathToBank = new Tile[] { new Tile(2675, 3091, 0), new Tile(2677, 3099, 0), new Tile(2680, 3107, 0),
            new Tile(2672, 3110, 0), new Tile(2664, 3111, 0), new Tile(2657, 3111, 0),
            new Tile(2651, 3107, 0), new Tile(2651, 3099, 0), new Tile(2644, 3093, 0),
            new Tile(2640, 3086, 0), new Tile(2631, 3087, 0), new Tile(2626, 3090, 0),
            new Tile(2624, 3099, 0), new Tile(2617, 3105, 0), new Tile(2610, 3099, 0),
            new Tile(2610, 3092, 0) };
    private Tile[] pathToSpiders = new Tile[] { new Tile(2611, 3092, 0), new Tile(2604, 3094, 0), new Tile(2608, 3100, 0),
            new Tile(2615, 3103, 0), new Tile(2622, 3105, 0), new Tile(2623, 3098, 0),
            new Tile(2623, 3091, 0), new Tile(2630, 3088, 0), new Tile(2637, 3087, 0),
            new Tile(2644, 3088, 0), new Tile(2648, 3094, 0), new Tile(2652, 3100, 0),
            new Tile(2651, 3107, 0), new Tile(2657, 3111, 0), new Tile(2664, 3111, 0),
            new Tile(2671, 3111, 0), new Tile(2677, 3107, 0), new Tile(2679, 3100, 0),
            new Tile(2676, 3093, 0), new Tile(2670, 3089, 0) };
 
    String s;
    private int startExp;
    private Timer runTime = new Timer(0);
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
 
    public void onStart() {
        startExp = Skills.getExperience(Skills.ATTACK);
        startExp += Skills.getExperience(Skills.STRENGTH);
        startExp += Skills.getExperience(Skills.DEFENSE);
        provide(new AttackSpiders(), new FindSpiders(), new WalkToSpiders(), new Eating(), new Banking(), new WalkToBank());
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
        return 25;
    }
 
    @Override
    public void messageReceived(MessageEvent e) {
        String s = e.getMessage();
        if (s.contains("can't reach")) {
            spiderTile.clickOnMap();
        }
    }
 
    public class AttackSpiders extends Node {
 
        @Override
        public boolean activate() {
            s = "Activating";
            return spiderArea.contains(Players.getLocal()) && Inventory.contains(FOOD) && getPercent() >= 80;
 
        }
        @Override
        public void execute() {
            s = "In loop";
            NPC spider = NPCs.getNearest(62);
            out:
            if (spider != null && spiderArea.contains(spider) && !littleArea.contains(Players.getLocal())) {
                s = "Spider exists";
                if (spider.isOnScreen()) {
                    if (!spider.isInCombat() && !Players.getLocal().isMoving()) {
                        s = "Spider not in combat";
                        if (spider.interact("Attack")) {
                            s = "Clicked it";
                            do {
                                s = "Sleeping";
                                sleep(500);
                            }
                            while (Players.getLocal().getInteracting().getHealthPercent() != 0);
                        }
                    } else {
                        sleep(1000, 1250);
                        s = "Sleeping";
                    }
                } else {
                    Camera.turnTo(spider);
                    s = "Turning to spider";
                }
            } else if (littleArea.contains(Players.getLocal())){
                spiderTile.clickOnMap();
                s = "Walking to tile";
            } else {
                s = "Sleeping, no Spiders";
                spider = NPCs.getNearest(62);
                do {
                    sleep(500);
                    if (spider.validate())
                        break out;
                }
                while (!spider.isOnScreen());
            }
        }
    }
 
 
    private class FindSpiders extends Node {
 
        @Override
        public boolean activate() {
            return !spiderArea.contains(Players.getLocal()) && Inventory.contains(FOOD) && Calculations.distanceTo(spiderTile) <= 6;
        }
 
        @Override
        public void execute() {
            spiderTile.clickOnMap();
        }
    }
 
 
 
    private class Eating extends Node {
 
        @Override
        public boolean activate() {
            return Inventory.getItem(FOOD) != null && getPercent() < 80 && Inventory.contains(FOOD);
 
        }
 
        @Override
        public void execute() {
            Item food = Inventory.getItem(FOOD);
            food.getWidgetChild().interact("Eat");
        }
 
    }
 
 
    private class WalkToBank extends Node {
 
        @Override
        public boolean activate() {
            return !bankTile.isOnScreen() && !Inventory.contains(FOOD);
        }
 
        @Override
        public void execute() {
            s = "Walking to Bank";
            walkTilePath(pathToBank);
 
        }
 
    }
    private class WalkToSpiders extends Node {;
        @Override
        public boolean activate() {
            return Calculations.distanceTo(bankTile) <= 10 &&  Inventory.contains(FOOD);
        }
 
        @Override
        public void execute() {
            s = "Walking to spiders";
 
            walkTilePath(pathToSpiders);
            sleep(200, 500);
        }
    }
 
    private class Banking extends Node {
 
        @Override
        public boolean activate() {
            return !Inventory.contains(FOOD) && Calculations.distanceTo(bankTile) <= 5;
        }
 
        @Override
        public void execute() {
            s = "Banking";
            if (Bank.open()) {
                sleep(1000);
                for (int i = 0; i < FOOD.length; i++) {
                    if (Inventory.isFull())
                        break;
                    if (Bank.withdraw(FOOD[i], Bank.Amount.ALL)) {
                        s = "Withdrawing food";
                    }
                }
            }
        }
    }
 
    private int getPercent() {
        if (!Players.getLocal().isInCombat()) {
            int currHP = Widgets.get(748, 5).getHeight();
            if ((Math.abs(100 - 100 * currHP / 28) * 120 / 100) >= 100)
                return 100;
            return Math.abs(100 - 100 * currHP / 28) * 120 / 100;
        }
        return Players.getLocal().getHealthPercent();
    }
 
    //START: Code generated using Enfilade's Easel
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
 
    private final Color color1 = new Color(0, 0, 0, 127);
    private final Color color2 = new Color(255, 255, 255);
 
    private final BasicStroke stroke1 = new BasicStroke(1);
 
    private final Font font1 = new Font("Arial", 0, 10);
 
    private final Image img1 = getImage("http://cur.cursors-4u.net/games/images9/gam868.gif");
    private final Image img2 = getImage("http://www.runeweb.net/images/site/saradomin_sword.gif");
 
    public void onRepaint(Graphics g1) {
 
        int tot =  Skills.getExperience(Skills.STRENGTH) + Skills.getExperience(Skills.ATTACK) + Skills.getExperience(Skills.DEFENSE);
        int expGained = tot - startExp;
        int expHour = (int)(expGained * 3600000d / runTime.getElapsed());
 
        Graphics2D g = (Graphics2D)g1;
        g.setColor(color1);
        g.fillRect(366, 155, 145, 161);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(366, 155, 145, 161);
        g.setFont(font1);
        g.drawString("vJungle Spiders v1", 392, 206);
        g.drawRect(373, 213, 133, 74);
        g.drawString("XP/Hour:" + expHour, 381, 247);
        g.drawString("XP Gained:" + expGained, 380, 262);
        g.drawString("Runtime:" + runTime.toElapsedString(), 380, 276);
        g.drawImage(img1, 395, 132, null);
        g.drawImage(img2, 416, 161, null);
        g.drawString("Status:" + s, 370, 305);
    }
    //END: Code generated using Enfilade's Easel
 
    private void walkTilePath(Tile[] path) {
        for (Tile t : path) {
            t.clickOnMap();
            do sleep(500, 750);
            while (Calculations.distanceTo(t) >= 7);
        }
    }
}