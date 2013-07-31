package main.operation;


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
		String manual="1.支持音乐选择功能,断点续播功能,拖动滑块快进快退功能\n" +
				"1.2.支持单曲循环,列表循环,随机播放模式\n" +
				"1.3.支持自定义背景音乐\n"+
				"1.4只要你将音乐文件放入软件目录下的-music_dir-目录即可,"+"音乐格式仅支持mp3\n"+
				"1.5支持音乐标签霓虹灯效果\n"+
				"2.支持自定义背景图片,并幻灯片播放,只需将自己的图片放入-img_dir-目录即可\n"+
				"3.采用kde风格界面,界面精致美观\n"+
				"4.支持富文本编辑以及插入图片文件\n"+
				"5.采用二进制形式保存用户的日记文件,有效保证了安全性\n"+
				"5.1.对用户密码采用SHA-1算法,有效保证安全性"+
				"6.支持日志记录\n"+
				"7.下一版本将集成数据库,敬请期待\n"+
				"8.支持启动闪屏\n"+
				"9.支持系统托盘\n"+
				"10.点击窗口的x其实是最小化到系统托盘,您可以右击托盘图标选择关闭按钮\n"+
				"11.添加水印签名"+
				"12.欢迎及时反馈BUG,及新想法";
//		JJOptionPane.showMessageDialog(null,manual, "使用手册" ,JOptionPane.PLAIN_MESSAGE);
		new JJDialog(manual).look();
	}
}
