package org.injustice.ironminer.util.methods;

import org.injustice.ironminer.util.vars.Dynamics;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class Methods {
    private static final int[] PICKAXES = {1267, 1269, 1271, 1273, 1275, 14099, 14107, 15259};
    /**
     * @param number    number we want to format
     * @param precision precision in dp
     * @return formatted number
     */
    public static String format(long number, int precision) {
        String sign = number < 0 ? "-" : "";
        number = Math.abs(number);
        if (number < 1000) {
            return sign + number;
        }
        int exponent = (int) (Math.log(number) / Math.log(1000));
        return String.format("%s%." + precision + "f%s", sign,
                number / Math.pow(1000, exponent),
                "kmbtpe".charAt(exponent - 1));
    }


    public static boolean inventEmpty() {
        return Inventory.getCount() == 0;
    }

    public static boolean isIdle() {
        return !Players.getLocal().isMoving() &&
               Players.getLocal().getAnimation() == -1;
    }

    public static boolean isMining() {
        return Players.getLocal().getAnimation() == 12188;
    }

    private static void setStatus(String status) {
        Dynamics.status = status;
    }

    public static void s(String status) {
        if (Dynamics.status != status)
            setStatus(status);
    }

    private static void checkPickaxe() {
        //iron, steel, addy, mith, rune, volatile, sacred, dragon
        for (int i : PICKAXES) {
            if (Inventory.contains(i)) {
                Dynamics.pickaxeInvent = true;
            } else if (Equipment.getItem(Equipment.Slot.WEAPON).getId() == i) {
                Dynamics.pickaxeWielded = true;
            }
        }
        Dynamics.pickaxeWielded = false;
        Dynamics.pickaxeInvent = true;
    }

    public static boolean inventPick() {
        checkPickaxe();
        return Dynamics.pickaxeInvent;
    }

    public static boolean equipPick() {
        checkPickaxe();
        return Dynamics.pickaxeWielded;
    }

}
