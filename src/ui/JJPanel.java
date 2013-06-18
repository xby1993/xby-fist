package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JJPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	BufferedImage bufImg;
	Graphics2D g2d;
	private Image img;  
	private static String imgPath=" ";
	public JJPanel(String imgPath) {
		setImgPath(imgPath);
//		Dimension size=new Dimension(img.getWidth(null),img.getHeight(null));
//		setSize(size);
//		setPreferredSize(size);
//		setMinimumSize(size);
//		setMaximumSize(size);
	}
	public void paintComponent(Graphics g) {
		img=new ImageIcon(getImgPath()).getImage();
	setOpaque(false);// 设置false以便于设置背景
	// 启用图像缓存设置好图像。
	bufImg = new BufferedImage(img.getWidth(null),
			img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
	g2d = bufImg.createGraphics();
	g2d.drawImage(img, 0, 0, null);
	g2d.dispose();// 释放资源
	// 准备矩形，用来创建一个纹理填充
	Rectangle rectan = new Rectangle(0, 0, img.getWidth(null),
			img.getHeight(null));
	TexturePaint tu = new TexturePaint(bufImg, rectan);
	// 用创建的纹理填充来填充整个面板
	g2d = (Graphics2D) g;
	g2d.setPaint(tu);
	g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	super.paintComponent(g);

}
	public void repaint() {
		img=new ImageIcon(getImgPath()).getImage();
        super.repaint();
    }
	
	/**
	 * @return the imagePath
	 */
	public  String getImgPath() {
		return imgPath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public static void setImgPath(String imgPath) {
		JJPanel.imgPath = imgPath;
	}
}
