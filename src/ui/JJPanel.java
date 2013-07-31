package ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
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
	private static String imgPath = " ";
	public JJPanel(String imgPath) {
		setImgPath(imgPath);
		// Dimension size=new Dimension(img.getWidth(null),img.getHeight(null));
		// setSize(size);
		// setPreferredSize(size);
		// setMinimumSize(size);
		// setMaximumSize(size);
	}

	public void paint(Graphics g) {
//		 img=new ImageIcon(getImgPath()).getImage();

		img = new ImageIcon(JJPanel.class.getResource(getImgPath())).getImage();
		
//		imgInit();
		// img=new ImageIcon(imgPath).getImage();
		setOpaque(false);// 设置false以便于设置背景
		// 启用图像缓存设置好图像。
		bufImg = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_3BYTE_BGR);
		g2d = bufImg.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.rotate(Math.toRadians(-30));// 旋转绘图上下文对象
		Font font = new Font("楷体", Font.BOLD, 72);// 创建字体对象
		g2d.setFont(font);// 指定字体
		g2d.setColor(Color.GREEN);// 指定颜色
		AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);// 获得表示透明度的AlphaComposite对象
		g2d.setComposite(alpha);// 指定AlphaComposite对象
		g2d.drawString("小白杨", -30, 240);// 绘制文本,实现水印
		g2d.dispose();// 释放资源
		// 准备矩形，用来创建一个纹理填充
		Rectangle rectan = new Rectangle(0, 0, img.getWidth(null),
				img.getHeight(null));
		TexturePaint tu = new TexturePaint(bufImg, rectan);
		// 用创建的纹理填充来填充整个面板
		g2d = (Graphics2D) g;
		alpha = AlphaComposite.SrcOver.derive(0.8f);
		g2d.setComposite(alpha);
		new ObjectRec(g2d).setObjectRec();
		g2d.setPaint(tu);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		super.paint(g);

	}



	/**
	 * @return the imagePath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public static void setImgPath(String imgPath) {
		JJPanel.imgPath = imgPath;
	}

/*	private void imgInit() {
		if (innerImg||Strings.count<0)
			img = new ImageIcon(JJPanel.class.getResource(imgPath)).getImage();
		else {
			try {
				img = ImageIO.read(new File(imgPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}*/

}
