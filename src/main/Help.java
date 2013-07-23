package main;

import javax.swing.JOptionPane;
/**
 *@description 实现帮助菜单下的所有方法
 * @author 小白杨
 * @date 2013-06-18
 */
public class Help {
	
	public static void about(){
	String about="小白杨出品"+"\n"+"版本:beta4.5.0"+"\n"+"BUG反馈:xby309778901@126.com\n"+"版权:2013-";
	JOptionPane.showMessageDialog(null,about, "关于" ,JOptionPane.PLAIN_MESSAGE);
	}
	public static void help(){
		String manual="本软件界面美观简洁，操作简单。"
				+ "\n下拉菜单说明："
		 		+ "\n(1)文件"
				+ "\n新建：新建一个空白的文档。[如果正在编辑中的文档被修改过，会提醒是否保存。]"
				+ "\n打开：打开一个已知的文本文档。[如果正在编辑中的文档被修改过，会提醒是否保存。]"
				+ "\n保存：保存现在编辑中的文档。[如果没有保存过会提示保存的位置，如果保存过则会自动更新已经保存的文件。]"
				+ "\n另存为：将现在编辑中的文件存到别的地方。[选择要保存到的路径，保存。]"
				+ "\n页面设置：打印文档时设置页面的格式等操作。"
	                        + "\n打印：将现在编辑中的文件输出到打印终端进行打印。"
				+ "\n退出:退出记事本软件。[如果正在编辑文本，会提示退出前是否保存文件。]"
				+ "\n(2)编辑"
	                        + "\n撤销：撤销上一步的操作."
	                        + "\n恢复：恢复上一步操作的数据."
				+ "\n剪切：将选中的文字剪贴到剪贴板中。"
				+ "\n复制：将选中的文字复制到剪贴板中。"
				+ "\n粘贴：将剪贴板中的文字粘贴到记事本的光标处。"
				+ "\n删除：删除选中的文字。"
				+"\n全选"
				+ "\n(3)格式" 
				+ "\n字体：可以设置文字的字体、颜色和大小。"
	                        + "\n(4)帮助"
	                        + "\n帮助主题：将显示此软件的相关帮助信息。"
	                        + "\n关于记事本：将显示此软件的开发者和版本等信息。"
				+"\n(5)本软件已经设置了一般操作的快捷键,打开菜单项即可看到."
				+"\n(6)搜索:可以向两个方向搜索,可以进行替换等"
				+"\n(7)集成自由背景切换,自动和手动均可.背景音乐播放,但有一定问题,将在下一版本修复."
				+"\n(8)自动化添加日期和天气";
		JOptionPane.showMessageDialog(null,manual, "使用手册" ,JOptionPane.PLAIN_MESSAGE);
	}
}
