package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JJLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JJLabel(){
		super();
	}
	public JJLabel(Icon image){
		super(image);
		
	}
	public JJLabel(String text){
		super(text);
	}
	public JJLabel(String text, Icon icon, int horizontalAlignment){
		super(text,icon,horizontalAlignment);
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		new ObjectRec(g2d).setObjectRec();
		super.paintComponent(g2d);
	}
}
