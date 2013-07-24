package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuItem;

public class JJMenuItem extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JJMenuItem(String string) {
		// TODO Auto-generated constructor stub
		super(string); 
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		new ObjectRec(g2d).setObjectRec();
		super.paintComponent(g2d);
	}
}
