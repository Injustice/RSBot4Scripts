package org.injustice.barbcourse;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 12/05/13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", name = "Barbarian Basic Course", description = "Does the barbarian basic agility course", version = 0.1)
public class BarbarianSimpleCourse extends ActiveScript implements PaintListener{
    @Override
    public int loop() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onStart() {

    }

    public void onStop() {

    }

    @Override
    public void onRepaint(Graphics graphics) {

    }
}
