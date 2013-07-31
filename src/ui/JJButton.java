package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class JJButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JJButton(String string){
		super(string);
	}
	public JJButton(){
		super();
	}
	@Override
	public void paintComponent(Graphics g){
		new ObjectRec((Graphics2D)g).setObjectRec();
		super.paintComponent(g);
	}

}
