package org.injustice.snippets;

import org.powerbot.core.event.listeners.PaintListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public abstract class PaintPanel extends JFrame implements PaintListener {

	private int delay = 50;
	private final int width;
	private final int height;
	
	private final JPanel canvas = new JPanel() {
	
		@Override
		public void paintComponent(final Graphics g) {
			g.clearRect(0, 0, width, height);
			onRepaint(g);
		}
	};
	
	public PaintPanel(final int w, final int h) {
		this(-1, -1, w, h);
	}
	
	public PaintPanel(final int x, final int y, final int w, final int h) {
		super();
		setSize(width = w, height = h);
		if (x >=0 && y >= 0) {
			setLocation(x, y);
		} else {
			setLocationRelativeTo(null);
		}
		
		setContentPane(canvas);
		new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent a) {
				canvas.repaint();
			}
			
		}).start();
	}
	
	/**
	 * Sets the preferred frames per second being rendered.
	 * @param fps The amount of frames to be rendered per second, default 20.
	 */
	public void setFPS(final int fps) {
		delay = 1000 / fps;
	}
}