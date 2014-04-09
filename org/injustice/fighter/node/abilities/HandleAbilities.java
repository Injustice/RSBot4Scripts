package org.injustice.fighter.node.abilities;


import org.injustice.fighter.util.UserSettings;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.framework.Actionbar;
import org.injustice.framework.Job;
import org.injustice.framework.Strategy;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class HandleAbilities extends Strategy implements Job {
    Actionbar.Defence_Abilities rejuv = Actionbar.Defence_Abilities.REJUVENATE;
    NPC[] possibleTargets = NPCs.getLoaded(Var.CATABLEPON_ID);

    public boolean activate() {
        return possibleTargets != null
                &&
                Players.getLocal().getInteracting() != null
                &&
                Players.getLocal().isInCombat()
                &&
                Actionbar.isOpen()
                &&
                Util.isUnderAttack()
                &&
                UserSettings.useAbilities;
    }

    public void execute() {
        for (int i = 1; i <= 12; i++)  {
            if (Actionbar.getSlot(i) != null && Util.isUnderAttack()) {
                if (Actionbar.getSlot(i).getSlotState() == Actionbar.SlotState.ABILITY) {
                    if (Util.getHpPercent() < 60 && UserSettings.useRejuv) {
                        if (Actionbar.getAdrenalinPercent() != 100) {
                            Var.status = "[ABILITY] Saving for rejuv";
                            Util.debug();
                            if (Actionbar.getAbilityAt(i) == Actionbar.Defence_Abilities.REJUVENATE) {
                                i++;
                            }
                        }
                    }
                    try {
                        if (Players.getLocal().getInteracting() != null &&
                                Players.getLocal().getInteracting().getHealthPercent() < 30) {
                        }
                        Var.status = "[ABILITY] Slot " + i;
                        Util.debug();
                        Actionbar.getSlot(i).activate(true);
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
    }
}
