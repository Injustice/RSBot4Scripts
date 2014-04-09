package org.injustice.framework.api.methods;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/05/13
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */

/**
 * Methods to do with location
 */
public class LocationUtil {
    private int radius;
    private Tile loc;

    public LocationUtil(Tile loc, int radius) {
        this.radius = radius;
        this.loc = loc;
    }

    /**
     * Creates an Area of given radius around loc
     * @return An Area made of four points with distance radius for North South East
     * and West, <tt>null</tt> if not successfully created.
     */
    public Area createArea() {
        final int x = loc.getX();
        final int y = loc.getY();
        final int p = loc.getPlane();
        final Tile N = new Tile(x, y + radius + 1, p);
        final Tile S = new Tile(x, y - radius - 1, p);
        final Tile E = new Tile(x - radius, y, p);
        final Tile W = new Tile(x + radius + 1, y, p);
        Area area = new Area(N, E, S, W);
        if (area != null) {
            return new Area(N, E, S, W);
        } else {
            return null;
        }
    }

    /**
     * Creates a Tile[] of given radius around loc using {@link LocationUtil#createArea()}
     * @return a Tile[] of given radius around loc in a diamond formation {@link LocationUtil#createArea()}
     */
    public Tile[] createTileArray() {
        Area a = createArea();
        if (a != null) {
            return a.getTileArray();
        } else {
            return null;
        }
    }
}
