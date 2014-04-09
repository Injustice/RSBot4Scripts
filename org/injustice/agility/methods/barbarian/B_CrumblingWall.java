package org.injustice.agility.methods.barbarian;

import org.injustice.agility.util.Obstacle;
import org.injustice.agility.util.Util;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class B_CrumblingWall extends Util implements Obstacle {
    private final int crumblingWall = 1948;
    private final Tile wallTile = new Tile(2537, 3553, 0);
    private final Area wallArea = new Area(new Tile(2531, 3545, 0), new Tile(2537, 3556, 0));

    @Override
    public boolean isValid() {
        return in(wallArea) && idle();
    }

    @Override
    public int loop() {
        final SceneObject wall = getObject(wallTile, crumblingWall);
        if (wall != null) {
            if (isOnScreen(wall)) {
                if (wall.interact("Climb")) {
                    new camera('e');
                    return 1000;
                }
            } else {
                new turn(wall);
                if (!isOnScreen(wall)) {
                    if (Walking.walk(new Tile(2537, 3553, 0))) {
                        return 1000;
                    }
                }
            }
        }
        return 250;
    }

    @Override
    public String state() {
        return "Crumbling wall";
    }
}

