package util;

import javax.swing.JButton;

import ui.UIUtils;

public class ToolButtonProvider implements ButtonProvider{
	
	private final JButton bold;
	private final JButton italic;
	private final JButton underline;
	private final JButton insertIMG;
	public ToolButtonProvider(){
		insertIMG=UIUtils.createToolButton("insertImg", "插入图片");
		bold=UIUtils.createToolButton("bold","粗体");
		italic=UIUtils.createToolButton("italic", "斜体");
		underline=UIUtils.createToolButton("underline", "下划线");
	}
	public JButton getButton(String cmd){
		switch (cmd) {
		case "粗体":
			return bold;
		case "斜体":
			return italic;
		case "下划线":
			return underline;
		case "插入图片":
			return insertIMG;
		default:
			break;
		}
		return null;
	}

}
