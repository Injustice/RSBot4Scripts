package org.injustice.agility.util;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public boolean isOnScreen(Entity e) {
        return e.isOnScreen() && !(Widgets.get(640, 4).validate() ?
                Widgets.get(640, 4) : Widgets.get(640, 2)).getBoundingRectangle().contains(e.getCentralPoint());
    }

    public boolean in(Area a) {
        return a.contains(Players.getLocal());
    }

    public boolean idle() {
        return !Players.getLocal().isMoving() &&
                Players.getLocal().getInteracting() == null &&
                Players.getLocal().getAnimation() == -1;
    }

    public boolean doObstacle(String action, int... id) {
        final SceneObject obj = getObject(null, id);
        if (obj != null) {
            if (isOnScreen(obj)) {
                return obj.interact(action);
            }
            else {
                if (!Players.getLocal().isMoving()) {
                    Camera.turnTo(obj);
                }
                if (!isOnScreen(obj)) {
                    if (Walking.getDestination() == null || Walking.getDestination() != null &&
                            !Players.getLocal().isMoving()) {
                        if (Walking.walk(obj.getLocation())) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public SceneObject getObject(final Tile location, final int... id) {
        return SceneEntities.getNearest(new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject obj) {
                if (location != null) {
                    for (int i : id) {
                        if (obj.getId() != i || !obj.getLocation().equals(location)) {
                            continue;
                        }
                        return obj.getId() == i && obj.getLocation().equals(location);
                    }
                } else {
                    for (int i : id) {
                        if (obj.getId() != i) {
                            continue;
                        }
                        return obj.getId() == i;
                    }
                }
                return false;
            }

        });
    }

    public class camera extends Thread {
        private char direction;

        public camera(char direction) {
            this.direction = direction;
            Thread t = new Thread(this);
            t.start();
        }

        public void setCompass(final char direction) {
            switch (direction) {
                case 'n':
                    Camera.setAngle(Random.nextInt(10, 20));
                    break;
                case 'w':
                    Camera.setAngle(Random.nextInt(79, 99));
                    break;
                case 's':
                    Camera.setAngle(Random.nextInt(169, 189));
                    break;
                case 'e':
                    Camera.setAngle(Random.nextInt(259, 279));
                    break;
                default:
                    break;
            }
        }

        private boolean cameraDirection(final char direction) {
            int angle = Camera.getX();  // TODO get angle
            switch (direction) {
                case 'n':
                    if (angle >= 0 && angle <= 20 || angle >= 335 && angle <= 359) {
                        return true;
                    }
                    return false;
                case 'w':
                    if (angle >= 70 && angle <= 110) {
                        return true;
                    }
                    return false;
                case 's':
                    if (angle >= 160 && angle <= 199) {
                        return true;
                    }
                    return false;
                case 'e':
                    if (angle >= 249 && angle <= 289) {
                        return true;
                    }
                    return false;
            }
            return false;
        }

        @Override
        public void run() {
            if (!cameraDirection(direction)) {
                setCompass(direction);
            }
        }
    }

    public class turn extends Thread {
        private SceneObject s;

        public turn(SceneObject s) {
            this.s = s;
            Thread t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            if (s != null) {
                if (!isOnScreen(s)) {
                    Camera.turnTo(s);
                }
            }
        }
    }

    public class pitch extends Thread {
        private boolean up;

        public pitch(boolean up) {
            this.up = up;
            Thread t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            if (up) {
                Keyboard.sendKey((char) KeyEvent.VK_UP, 3000);
            } else {
                Keyboard.sendKey((char) KeyEvent.VK_DOWN, 3000);
            }
        }
    }
}
