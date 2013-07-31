package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JList;

public class JJList<E> extends JList<E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JJList(){
		super();
	}
	@Override
	public void paintComponent(Graphics g){
		new ObjectRec((Graphics2D)g).setObjectRec();
		super.paintComponent(g);
	}
}
