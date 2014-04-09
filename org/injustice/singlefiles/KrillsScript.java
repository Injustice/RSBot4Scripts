package org.injustice.singlefiles;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 05/06/13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", version = 1.0, name = "Draynor chopper", description = "chops in draynor and banks")
public class KrillsScript extends ActiveScript {
    private final Area area = new Area(
            new Tile(3073, 3247, 0), new Tile(3067, 3249, 0),
            new Tile(3066, 3252, 0), new Tile(3068, 3255, 0),
            new Tile(3070, 3260, 0), new Tile(3072, 3264, 0),
            new Tile(3074, 3269, 0), new Tile(3075, 3274, 0),
            new Tile(3078, 3279, 0), new Tile(3081, 3288, 0),
            new Tile(3084, 3289, 0), new Tile(3087, 3287, 0),
            new Tile(3086, 3276, 0), new Tile(3085, 3265, 0),
            new Tile(3079, 3263, 0), new Tile(3073, 3247, 0));
    Timer t = new Timer(5000);
    Timer t1 = new Timer(2500);
    private final int[] ids = { 38760, 47600,38785,47598,38783,38731 };
    private final Tile bankTile = new Tile(3092, 3244, 0);

    @Override
    public int loop() {
        if (Inventory.isFull()) {
            if (bankTile.isOnScreen()) {
                if (Bank.open()) {
                    t1.reset();
                    while (Bank.isOpen() && t.isRunning()) {
                        sleep(500);
                    }
                    if (Bank.deposit(1511, Bank.Amount.ALL)) {
                        sleep(1000);
                        Bank.close();
                    }
                }
            } else {
                Walking.walk(bankTile);
            }
        } else {
            if (area.contains(Players.getLocal())) {
                SceneObject s = SceneEntities.getNearest(ids);
                if (s!=null) {
                    if (s.isOnScreen()) {
                        if (s.interact("Chop")) {
                            sleep(1000);
                            t.reset();
                            while(Players.getLocal().isMoving() && t.isRunning()) {
                                sleep(500);
                            }
                            t.reset();
                            while(Players.getLocal().getAnimation() != -1 && t.isRunning()) {
                                sleep(500);
                            }
                        }
                    } else {
                        Camera.turnTo(s);
                        Camera.setPitch(0);
                    }
                }
            } else {
                Walking.walk(area.getNearest());
            }
        }
        return 150;
    }
}
