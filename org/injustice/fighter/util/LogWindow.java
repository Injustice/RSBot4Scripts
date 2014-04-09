package org.injustice.fighter.util;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 07/05/13
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class LogWindow extends JFrame {

    private JTextArea textArea = null;

    public LogWindow(String title, int width, int height) {
        super(title);
        setSize(width, height);
        textArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane pane = new JScrollPane(textArea);
        getContentPane().add(pane);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void showInfo(String data) {
        textArea.append(data);
        this.getContentPane().validate();
    }
}
