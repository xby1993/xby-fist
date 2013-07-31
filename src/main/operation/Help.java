package main.operation;


import source.Strings;
import ui.JJDialog;
/**
 *@description 实现帮助菜单下的所有方法
 * @author 小白杨
 * @date 2013-06-18
 */
public class Help {
	
	public static void about(){
	String about="小白杨出品         "+"\n"+"版本:RC1.0-Public          "+"\n"+"BUG反馈:xby309778901@126.com      \n"+"版权:2013-";
//	JJOptionPane.showMessageDialog(null,about, "关于" ,JOptionPane.PLAIN_MESSAGE);
	new JJDialog(about).look();
	}
	public static void help(){
		String manual=Strings.getManual();
//		JJOptionPane.showMessageDialog(null,manual, "使用手册" ,JOptionPane.PLAIN_MESSAGE);
		new JJDialog(manual).look();
	}
}
