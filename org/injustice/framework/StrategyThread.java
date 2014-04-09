package org.injustice.framework;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class StrategyThread implements Runnable {
    final Strategy strategy;

    public StrategyThread(final Strategy s) {
        strategy = s;
    }

    @Override
    public void run() {
        try {
            strategy.started();
            strategy.getJob().execute();
            strategy.finished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
