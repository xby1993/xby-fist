package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;

public class JJMenuBar extends JMenuBar {
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		new ObjectRec(g2d).setObjectRec();
		super.paintComponent(g2d);
	}
}
