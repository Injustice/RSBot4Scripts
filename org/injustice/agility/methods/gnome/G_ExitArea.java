package org.injustice.agility.methods.gnome;

import org.injustice.agility.util.Obstacle;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class G_ExitArea implements Obstacle {
    @Override
    public boolean isValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int loop() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String state() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

