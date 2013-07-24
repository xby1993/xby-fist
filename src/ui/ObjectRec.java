package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ObjectRec {
	private Graphics2D g2d;
	public ObjectRec(Graphics g2d){
		this.g2d=(Graphics2D)g2d;
	}
	public void setObjectRec(){
		RenderingHints render=new RenderingHints(null);
		render.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		render.put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHints(render);
	}
}
