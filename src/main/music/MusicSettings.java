package main.music;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;

import javax.swing.JLabel;

public class MusicSettings {
	private int dely = 0;
	private int delayCount;
	private int rX = 0, rY = 0;
	private static final int RGB_MAX = 255;
	private JLabel label;
	private boolean shiftLabelWay = false;
	private Random ran = new Random();
	private int fontSize = 14;
	public MusicSettings(int delays, int rX, int rY) {
		this.delayCount = delays;
		this.rX = rX;
		this.rY = rY;
	}

	public void setAnim() {
		if (dely == delayCount) {
			dely = 0;
			label.setFont(new Font("宋体", Font.PLAIN, fontSize));
			
			ran.setSeed(System.currentTimeMillis());
			/*
			 * System.out.println(ran.nextInt(RGB_MAX) + ":" +
			 * ran.nextInt(RGB_MAX) + ":" + ran.nextInt(RGB_MAX));
			 */ 
			label.setBackground(new Color(ran.nextInt(RGB_MAX),ran.nextInt(RGB_MAX),ran.nextInt(RGB_MAX),ran.nextInt(RGB_MAX)));
			Point point = label.getLocation();
			if (shiftLabelWay) {
				shiftLabelWay = false;
				point = new Point(point.x + ran.nextInt(rX), point.y
						+ ran.nextInt(rY));
			} else {
				shiftLabelWay = true;
				point = new Point(point.x - ran.nextInt(rX), point.y
						- ran.nextInt(rY));
			}
			label.setLocation(point);
		}
		dely++;
	}

	void setPlayname(String name) {
		label.setOpaque(true);
		setAnim();
		label.setText(name);
	}

	/**
	 * @return the label
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}
}
