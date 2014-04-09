package org.injustice.fighter.util.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public enum Loot {
    ADAMANT_ARROW(true, 890),
    ALL_CHARMS(true, 12158, 12159, 12160, 12163),
    BLUE_CHARM(true, 12163),
    BONES(false, 526),
    CHAOS_RUNE(true, 562),
    COAL(true, 454),
    COSMIC_RUNE(true, 564),
    CRIMSON_CHARM(true, 12160),
    GOLD_CHARM(true, 12158),
    GREEN_CHARM(true, 12159),
    LAW_RUNE(true, 563),
    MEDIUM_CLUE_SCROLL(false, 3612),
    MITHRIL_ARROW(true, 888),
    RUNE_ESSENCE(true, 1437),
    TOP_SCEPTRE(false, 9010);

    private int id;
    private int[] ids;
    private boolean stackable;
    private String name;

    Loot(final boolean stackable, final int... ids) {
        id = this.ids[0];
        this.ids = ids;
        this.stackable = stackable;
    }

    public int getId() {
        return id;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int[] getIds() {
        return ids;
    }
}
