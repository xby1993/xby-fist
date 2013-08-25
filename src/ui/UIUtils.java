package ui;

import java.awt.Cursor;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.plaf.ButtonUI;

import source.MusicPicString;

public class UIUtils {
	private static ImageIcon[] icons;
	public synchronized static ImageIcon[] getMusicIcon(String name, int count) {
		ImageIcon[] imgs = new ImageIcon[count];
		for (int i = 1; i <= count; i++) {
			URL url = UIUtils.class.getResource(MusicPicString.PIC_PATH + name
					+ i + ".png");
			imgs[i - 1] = new ImageIcon(url);
		}
		return imgs;
	}
	public synchronized static ImageIcon[] getToolIcon(String name, int count){
		ImageIcon[] imgs = new ImageIcon[count];
		for (int i = 1; i <= count; i++) {
			URL url = UIUtils.class.getResource(MusicPicString.TOOL_PATH + name
					+ i + ".png");
			imgs[i - 1] = new ImageIcon(url);
		}
		return imgs;
	}
	public static JButton createToolButton(String name, String cmd){
		icons = getToolIcon(name, 3);
		JButton jb = new JButton();
		/*jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setDoubleBuffered(true);
		jb.setIcon(icons[0]);
		jb.setRolloverIcon(icons[1]);
		jb.setPressedIcon(icons[2]);
		jb.setOpaque(false);
		jb.setFocusable(false);
		jb.setActionCommand(cmd);
		jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
		initButton(jb);
		// jb.addActionListener(listener);
		return jb;
	}
	public static JButton createMusicJButton(String name, String cmd) {

		icons = getMusicIcon(name, 3);
		JButton jb = new JButton();
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setDoubleBuffered(true);
		jb.setIcon(icons[0]);
		jb.setRolloverIcon(icons[1]);
		jb.setPressedIcon(icons[2]);
		jb.setOpaque(false);
		jb.setFocusable(false);
		jb.setActionCommand(cmd);
		jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		initButton(jb);
		// jb.addActionListener(listener);
		return jb;
	}
	public static void setButton(JButton jb,ImageIcon[] icons,String cmd){
		jb.setIcon(icons[0]);
		jb.setRolloverIcon(icons[1]);
		jb.setPressedIcon(icons[2]);
		jb.setActionCommand(cmd);
	}
	public static JRadioButton createJRadioButton(String name, String cmd){
		icons = getMusicIcon(name, 3);
		JRadioButton jb = new JRadioButton(cmd);
/*		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setDoubleBuffered(true);
		jb.setIcon(icons[0]);
		jb.setRolloverIcon(icons[1]);
		jb.setPressedIcon(icons[2]);
		jb.setOpaque(false);
		jb.setFocusable(false);
		jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
		initButton(jb);
		// jb.addActionListener(listener);
		return jb;
	}
	private static void initButton(AbstractButton jb){
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setDoubleBuffered(true);
		jb.setIcon(icons[0]);
		jb.setRolloverIcon(icons[1]);
		jb.setPressedIcon(icons[2]);
		jb.setOpaque(false);
		jb.setFocusable(false);
		jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	/**
     * 根据一些条件,统一生成一个滚动条
     * @param min 最小值 
     * @param max 最大值
     * @param value 当前值
     * @param ball1 球1
     * @param ball2 球2
     * @param ball3 球3
     * @param bg1 背景1
     * @param bg2 背景2
     * @param listener 监听器
     * @param orientation 方向
     * @return 滚动条
     */
    public static MySlider createSlider(int orientation) {
        MySlider slider = new MySlider();
        MySliderUI ui = new MySliderUI(slider);
        slider.setOpaque(false);
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue(0);
        slider.setOrientation(orientation);
        ui.setThumbImage(new ImageIcon(UIUtils.class.getResource(MusicPicString.PIC_PATH+"ball1.png")).getImage());
        ui.setThumbOverImage(new ImageIcon(UIUtils.class.getResource(MusicPicString.PIC_PATH+"ball2.png")).getImage());
        ui.setThumbPressedImage(new ImageIcon(UIUtils.class.getResource(MusicPicString.PIC_PATH+"ball3.png")).getImage());
        ui.setBackgroundImages(new ImageIcon(UIUtils.class.getResource(MusicPicString.PIC_PATH+"pos1.png")).getImage());
        ui.setActiveBackImage(new ImageIcon(UIUtils.class.getResource(MusicPicString.PIC_PATH+"pos2.png")).getImage());
        slider.setUI(ui);
//        slider.addChangeListener(listener);
        return slider;
    }
}