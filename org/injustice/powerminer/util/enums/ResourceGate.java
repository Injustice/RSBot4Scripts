package org.injustice.powerminer.util.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 31/05/13
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public enum ResourceGate {
    DWARVEN_MINE(52855, 52864),
    MINING_GUILD(52656, 52866),
    AL_KHARID(52860, 52872);

    private int entrance, exit;

    ResourceGate(int entrance, int exit) {
        this.entrance = entrance;
        this.exit = exit;
    }
}
