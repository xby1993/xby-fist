/*
 * 此类用于局部NoteFrame事件相关的包装类
 */
package main.event;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import main.Edit;
import main.FileOperator;
import main.Help;
import main.MyFont;
import ui.JJTextPane;
import ui.NoteFrame;

public class NoteFrameEvent {
	private int findnextcount = 0;
	private JJTextPane textPane;
	private NoteFrame frame;
	public NoteFrameEvent(NoteFrame frame){
		this.frame=frame;
		textPane=frame.getJJTextPane();
//		textPane=frame.textPane;
	}
	void replace() {// 替换对话框，添加监听器并重写actionPerformed方法
		JFrame jr = new JFrame("替代");
		JLabel jl1 = new JLabel("查找：");
		JLabel jl2 = new JLabel("替换为：");
		final JTextField jtf1 = new JTextField(10);
		final JTextField jtf2 = new JTextField(10);
		JButton jb1 = new JButton("替换/替换下一个");
		JButton jb2 = new JButton("全部替换");
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		jp1.add(jl1);
		jp1.add(jtf1);
		jp2.add(jl2);
		jp2.add(jtf2);
		jp3.add(jb1);
		jp3.add(jb2);
		jr.setLayout(new GridLayout(3, 1));
		jr.add(jp1);
		jr.add(jp2);
		jr.add(jp3);
		jr.setSize(400, 200);
		jr.setLocationRelativeTo(null);
		jr.setVisible(true);

		jb1.addActionListener(new ActionListener() {// 逐个替换
			public void actionPerformed(ActionEvent e) {
				String sfind = jtf1.getText();
				String sreplace = jtf2.getText();
				String cur = textPane.getText();
				if (cur.indexOf(sfind) == -1) {
					unfind();
				} else {
					if (textPane.getCaretPosition() == cur.length()) {
						textPane.setCaretPosition(0);
					}
					textPane.setSelectionStart(cur.indexOf(sfind,
							textPane.getCaretPosition()));
					textPane.setSelectionEnd(cur.indexOf(sfind,
							textPane.getCaretPosition())
							+ sfind.length());
					textPane.replaceSelection(sreplace);
				}
			}
		});
		jb2.addActionListener(new ActionListener() {// 全部替换
			public void actionPerformed(ActionEvent e) {
				while (true) {
					String sfind = jtf1.getText();
					String sreplace = jtf2.getText();
					String cur = textPane.getText();
					if (cur.indexOf(sfind) == -1) {
						break;
					}
					textPane.setSelectionStart(cur.indexOf(sfind,
							textPane.getCaretPosition()));
					textPane.setSelectionEnd(cur.indexOf(sfind,
							textPane.getCaretPosition())
							+ sfind.length());
					textPane.replaceSelection(sreplace);
				}
			}
		});
	}

	void find() {
		JFrame jfind = new JFrame("查找");
		JLabel jl2 = new JLabel("请输入要查询的字符串：");
		final JTextField jtf1 = new JTextField(10);
		JButton jb1 = new JButton("查找/查下一个");
		final JRadioButton up = new JRadioButton("向上(U)");
		final JRadioButton down = new JRadioButton("向下(D)", true);// 默认向下找
		ButtonGroup bg = new ButtonGroup();
		bg.add(up);
		bg.add(down);
		Box box1 = Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createTitledBorder("方向"));
		box1.add(Box.createHorizontalStrut(5));
		box1.add(up);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(down);
		box1.add(Box.createHorizontalStrut(5));
		jfind.setLayout(new FlowLayout());
		jfind.add(jl2);
		jfind.add(jtf1);
		jfind.add(box1);
		jfind.add(jb1);
		jfind.setSize(400, 200);
		jfind.setVisible(true);
		jfind.setLocationRelativeTo(null);

		jb1.addActionListener(new ActionListener() {// 按钮“查找/查下一个”的监听器方法
			public void actionPerformed(ActionEvent e) {

				String sfind = jtf1.getText();
				String cur = textPane.getText();
				String find = jtf1.getText();
				String main = textPane.getText().replaceAll("\n", "");
				int count = 1;
				int length = main.length() - find.length() + 1;
				if (length > 0) {
					int[] start = new int[length];// 标记匹配处开头位置，最多有Length个匹配的开头坐标值
					for (int i = 0; i < length; i++) {
						if (main.substring(i, i + find.length()).equals(find)) {
							// 要记下i的位置
							start[count] = i;
							count++;
						}
					}

					if (cur.indexOf(sfind) == -1) {
						unfind();
					} else if (findnextcount >= count) {
						findnextcount = 0;
					} else if (findnextcount < count) {
						textPane.setSelectedTextColor(Color.RED);
						if (up.isSelected()) {
							textPane.select(
									start[count - findnextcount],
									start[count - findnextcount]
											+ find.length());
						} else if (down.isSelected()) {
							textPane.select(start[findnextcount],
									start[findnextcount] + find.length());
						}
						if (textPane.getCaretPosition() == cur.length()) {
							textPane.setCaretPosition(0);
						}
						textPane.setSelectionStart(cur.indexOf(sfind,
								textPane.getCaretPosition()));
						textPane.setSelectionEnd(cur.indexOf(sfind,
								textPane.getCaretPosition())
								+ sfind.length());
					}
				}
				findnextcount++;
			}

		});
	}
	void unfind() {
		JOptionPane.showMessageDialog(null, "查找失败,未找到符合的结果!");
	}
public void actionEvent(ActionEvent event){
	ActionEvent e=event;
	switch (e.getActionCommand()) {
	case "关于":
		Help.about();
		break;
	case "说明":
		Help.help();
		break;
	case "字体":
		new MyFont(new Font("SansSerif", 16, 16), null).showDialog(null);
		break;
	case "颜色":
		Color color = JColorChooser.showDialog(null, "字体颜色选择", Color.red);
		if (color != null) {
			SimpleAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setFontFamily(attr, "SansSerif");
			StyleConstants.setFontSize(attr, 14);
			StyleConstants.setForeground(attr, color);
			int pos = textPane.getSelectionStart();
			int end = textPane.getSelectionEnd();
			if (pos > end) {
				int tmp = end;
				end = pos;
				pos = tmp;
			}

			textPane.getStyledDocument().setCharacterAttributes(pos, end - pos + 1, attr, true);
		}

		break;
	case "查找":
	case "查找下一个":
		find();
		break;
	case "替换":
	case "转到":
		replace();
		break;
	case "插入图片":
		insertIcon();
		break;
	case "撤销":
		new Edit(frame).cancel();
		break;
	case "恢复":
		new Edit(frame).recover();
		break;
	case "剪切":
		new Edit(frame).Cut();
		break;
	case "复制":
		new Edit(frame).Copy();
		break;
	case "粘贴":
		new Edit(frame).Paste();
		break;
	case "删除":
		new Edit(frame).Delete();
		break;

	case "全选":
		new Edit(frame).SelectAll();
		break;
	case "新建":
		new FileOperator(frame).createFile();
		break;
	case "打开":
//		new FileOperator(frame).openFile();

			new FileOperator(frame).readTo();
		
		break;
	case "保存":
		new FileOperator(frame).saveFile();
		break;
	case "另存为":
		new FileOperator(frame).saveFile();
		break;
	case "页面设置":
		new FileOperator(frame).pageSetup();
		break;
	case "打印":
		new FileOperator(frame).print();
		break;
	case "退出":
		new FileOperator(frame).exit();
		break;
	default:
		break;
	}
}
void insertIcon() {
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"jpg & gif图像文件", "jpg", "JPG", "JPEG", "GIF", "gif");// 设置可选文件后缀名
	chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
	chooser.setFileFilter(filter);
	if (chooser.showOpenDialog(frame) == 0) {
		File file = chooser.getSelectedFile();
		if (file.exists()) {

			ImageIcon icon = new ImageIcon(file.getPath());
			textPane.insertIcon(icon);
		}
	}

}
}
