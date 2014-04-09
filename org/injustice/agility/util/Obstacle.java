package org.injustice.agility.util;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/06/13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public interface Obstacle {
    public boolean isValid();
    public int loop();
    public String state();
}
