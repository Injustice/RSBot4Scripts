package org.injustice.agility;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", name = "iAgility", description = "Gnome basic + advanced, Barbarian basic + advanced, Ape Atoll", version = 0.1)
public class AIOAgility extends ActiveScript implements PaintListener {
    @Override
    public void onStart() {

    }

    @Override
    public int loop() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRepaint(Graphics g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
