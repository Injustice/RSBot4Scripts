package org.injustice.fighter.util;

import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 07/05/13
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
class WindowHandler extends Handler {
    private static WindowHandler handler = null;
    private LogWindow window = null;


    private WindowHandler() {
        configure();
        if (window == null)
            window = new LogWindow("Debug", 500, 300);
    }

    public static synchronized WindowHandler getInstance() {

        if (handler == null) {
            handler = new WindowHandler();
        }
        return handler;
    }

    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String className = this.getClass().getName();
        String level = manager.getProperty(className + ".level");
        String formatter = manager.getProperty(className + ".formatter");

        setLevel(level != null ? Level.parse(level) : Level.INFO);
        setFormatter(makeFormatter(formatter));

    }

    private Formatter makeFormatter(String formatterName) {
        Class c;
        Formatter f;

        try {
            c = Class.forName(formatterName);
            f = (Formatter) c.newInstance();
        } catch (Exception e) {
            f = new SimpleFormatter();
        }
        return f;
    }

    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record))
            return;
        try {
            window.showInfo(getFormatter().format(record));
        } catch (Exception ex) {
            reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }

    }

    public void close() {
    }

    public void flush() {
    }
}
