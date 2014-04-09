package org.injustice.wolfkiller.strat;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

import static org.injustice.wolfkiller.util.Constants.WOLF_TILE;
import static org.injustice.wolfkiller.util.Variables.wolvesBeingUsed;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 27/03/13
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class Attack extends Node {
    @Override
    public boolean activate() {
        return WOLF_TILE.isOnMap() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        NPC wolf = NPCs.getNearest(FILTER_WOLF);
        if (WOLF_TILE.isOnScreen()) {
            if (wolf != null) {
                if (!wolf.isInCombat()) {
                    if (wolf.isOnScreen()) {
                        if (wolf.interact("Attack")) {
                            do sleep(1000);
                            while (Players.getLocal().isInCombat());
                        }
                    } else Camera.turnTo(wolf);
                } else {
                    if (wolf.getInteracting() == null) {
                        Walking.walk(wolf);
                        do sleep(1000);
                        while (Players.getLocal().isMoving());
                    }
                }
            }
        } else Camera.turnTo(WOLF_TILE);
    }

    private final Filter<NPC> FILTER_WOLF = new Filter<NPC>() {
        public boolean accept(NPC npc) {
            for (final int ID : wolvesBeingUsed) {
                if (!npc.isInCombat() && npc.getId() == ID && WOLF_TILE.isOnMap()) {
                    return true;
                }
            }

            return false;
        }
    };
}
