package org.injustice.fighter.node.abilities;

/*
import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.DefenseAbility;
*/
public class AbilityHandler {
/*    BarNode rejuv = ActionBar.getNode(DefenseAbility.REJUVENATE);
    NPC[] possibleTargets = NPCs.getLoaded(Var.CATABLEPON_ID);

    public boolean activate() {
        return possibleTargets != null
                &&
                Players.getLocal().getInteracting() != null
                &&
                Players.getLocal().isInCombat()
                &&
                ActionBar.makeReady()
                &&
                Util.isUnderAttack()
                &&
                UserSettings.useAbilities;
    }

    public void execute() {
        BarNode basicFilter = ActionBar.getNode(new Filter<BarNode>() {
            @Override
            public boolean accept(BarNode n) {
                return n != null && n.isValid() && n.canUse();
            }
        });
        BarNode nonRejuv = ActionBar.getNode(new Filter<BarNode>() {
            @Override
            public boolean accept(BarNode n) {
                return n != rejuv;
            }
        });
        for (BarNode node : ActionBar.getNodes())  {
            if (node != null && Util.isUnderAttack()) {
                if (node.isValid() && node.canUse()) {
                    if (Util.getHpPercent() < 60 && UserSettings.useRejuv) {
                        if (ActionBar.getAdrenaline() != 100) {
                            Var.status = "[ABILITY] Saving for rejuv";
                            Util.debug();
                            node = basicFilter;
                        }
                    }
                    if (UserSettings.useRejuv &&
                            rejuv.canUse() && Util.getHpPercent() > 60) {
                        Var.status = "[ABILITY] Rejuv available, high HP";
                        Util.debug();
                        node = nonRejuv;
                    }
                    try {
                        if (Players.getLocal().getInteracting() != null &&
                                Players.getLocal().getInteracting().getHealthPercent() < 30) {
                        }
                        Var.status = "[ABILITY] " + format(node.toString().substring(9));
                        Util.debug();
                        node.use();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("NPE: " + e.getCause());
                        Var.status = "NPE: " + e.getCause();
                        Util.debug();
                    }
                    // Was getting NPEs in this part
                }
            }
        }
    }


    private String format(String s) {
        String string = s.replace('_', ' ');
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }  */
}
