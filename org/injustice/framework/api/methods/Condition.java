package org.injustice.framework.api.methods;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 04/05/13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */

/**
 * Condition used for {@link MiscUtil#waitFor(Condition, long)}
 */
public interface Condition {
    public boolean validate();
}
