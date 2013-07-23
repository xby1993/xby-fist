package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SetLookAndFeel {
	public static void setLookAndFeel(JFrame com){
		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			com.setResizable(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
//		JFrame.setDefaultLookAndFeelDecorated(false);

		SwingUtilities.updateComponentTreeUI(com);//使更改的lookandfeel立即生效
	}
	public static void setDecorated(){
		JFrame.setDefaultLookAndFeelDecorated(true);//使标题栏装饰生效
		JDialog.setDefaultLookAndFeelDecorated(true);//使对话框装饰生效
	}
}
