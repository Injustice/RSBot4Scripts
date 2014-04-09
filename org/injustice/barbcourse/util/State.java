package org.injustice.barbcourse.util;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 12/05/13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public enum State {
    ROPE_SWING(43526, new Area(new Tile(2548, 3552, 0), new Tile(2556, 3558, 0))),
    ROPE_SWING_FAIL(),
    LOG_BALANCE(43595, new Area(new Tile(2544, 3546, 0), new Tile(2556, 3551, 0))),
    OBSTACLE_NET(20211, new Area(new Tile(2535, 3543, 0), new Tile(2544, 3549, 0))),
    BALANCING_LEDGE(2302, new Area(new Tile(2535, 3544, 1), new Tile(2539, 3548, 1))),
    LADDER(3205, new Area(new Tile(2531, 3545, 1), new Tile(2534, 3548, 1))),
    CRUMBLING_WALL(1948),
    LADDER_FAIL(32015);

    private int id;
    private Area area;

    State() {

    }
    State(int id) {
        this.id = id;
    }

    State(int id, Area area) {
        this.id = id;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public Area getArea() {
        return area;
    }
}
