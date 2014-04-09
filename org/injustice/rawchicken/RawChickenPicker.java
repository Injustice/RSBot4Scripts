package org.injustice.rawchicken;

import org.injustice.rawchicken.strategies.*;
import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Paint;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.injustice.rawchicken.util.Var.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */

@Manifest(authors = {"Injustice"}, name = "Raw Chicken Picker", description = "Picks raw chicken in lumbridge and banks", version = 3.1)
public class RawChickenPicker extends ActiveScript implements PaintListener, MouseListener {
    private final Node[] nodes = {new Deposit(), new Pick(), new WalkBox(), new WalkPen()};

    public void onStart() {
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
        getContainer().submit(new ChickenCounter());
        startTime = System.currentTimeMillis();
        chickenPrice = GeItem.lookup(RAW_CHICKEN_ID[0]).getPrice();
    }

    @Override
    public int loop() {
        for (Node n : nodes) {
            if (n.activate() && Methods.isReady()) {
                n.execute();
            }
        }
        return 25;
    }

    @Override
    public void onRepaint(Graphics g1) {
        Paint p = new Paint();
        p.onRepaint(g1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle screeny = new Rectangle(112, 210, 81, 11);
        Rectangle close = new Rectangle(132, 285, 59, 11);
        Rectangle open = new Rectangle(132, 285, 59, 11);
        Rectangle mouse = new Rectangle(131, 270, 61, 11);
        Rectangle tiles = new Rectangle(140, 255, 52, 11);
        Point p = e.getPoint();
        if (close.contains(p) && !hidePaint) hidePaint = true;
        else if (open.contains(p) && hidePaint) hidePaint = false;
        else if (screeny.contains(p))Methods.savePaint(5, 178, 190, 120);
        else if (mouse.contains(p) && showMouse) showMouse = false;
        else if (mouse.contains(p) && !showMouse) showMouse = true;
        else if (tiles.contains(p) && showTiles) showTiles = false;
        else if (tiles.contains(p) && !showTiles) showTiles = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}