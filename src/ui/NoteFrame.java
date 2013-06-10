package ui;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class NoteFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 菜单条
	private JMenuBar menuBar;
	// 文件菜单项
	private JMenu menuFile;
	private JMenuItem newfile;// 新建
	private JMenuItem openfile;// 打开
	private JMenuItem savefile;// 保存
	private JMenuItem saveAs;// 另存为
	private JMenuItem pageSetup;// 页面设置
	private JMenuItem print;// 打印
	private JMenuItem exit;// 退出
	// 编辑菜单项
	private JMenu menuEdit;
	private JMenuItem cancle;// 撤销
	private JMenuItem recover;// 恢复
	private JMenuItem cut;// 剪切
	private JMenuItem copy;// 复制
	private JMenuItem paste;// 粘贴
	private JMenuItem delete;// 删除
	private JMenuItem selectAll;// 全选
	// 搜索菜单
	private JMenu searchMenu;
	private JMenuItem find;// 查找
	private JMenuItem findNext;// 查找下一个
	private JMenuItem replace;// 替换
	private JMenuItem switchTo;// 转到
	// 格式菜单项
	private JMenu menuFormat;
	private JCheckBoxMenuItem autowrap;// 自动换行
	private JMenuItem font;// 字体
	// 查看菜单项
	private JMenu menuView;
	private JCheckBoxMenuItem statusBar;// 状态栏
	// 帮助菜单项
	private JMenu menuHelp;
	private JMenuItem helpTopics;// 帮助主题
	private JMenuItem about;// 关于记事本
	// 右键菜单
	private JPopupMenu pmenu;
	private JMenuItem pcancle;// 撤销
	private JMenuItem precover;// 恢复
	private JMenuItem pcut;// 剪切
	private JMenuItem pcopy;// 复制
	private JMenuItem ppaste;// 粘贴
	private JMenuItem pdelete;// 删除
	private JMenuItem pselectall;// 全选
	// private JCheckBoxMenuItem pkeyboard;//打开/关闭软键盘
	// 编辑文字区
	private JTextArea textArea;
	private JScrollPane scrollpane;
	// 标示状态的工具条
	private JToolBar status;
	private JLabel currentstatus;
	String statusinfo = "";

	private JButton openButton, saveButton;// 快速打开和保存文件
	private JTextField date;// 日期输入框
    String imgPath=new String("src/source/image/7.jpg");
	private JPanel panel1, panel2;
	private JRadioButton musicOn, musicOff;// 背景音乐开关
	private JComboBox<String> day, wether;// 时间,天气选择下拉列表.
	private URL cb;// 音乐文件路颈
	private AudioClip aau;// 音频剪辑播放
	private File tempfile;// 临时存储文件
	// 文本是否改变标志
	private Boolean changed = false;
	// 撤销和恢复
	UIInterface uii=new UIInterface();

	public NoteFrame() {
		initBar();
		initPopMenu();
		initPanel3();
		initShutcut();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = size.width / 2;
		int height = size.height / 2;
		setBounds((size.width - width) / 2, (size.height - height) / 2, width,
				height);
		pack();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	
	void initBar() {
		// 初始化文件菜单
		menuBar = new JMenuBar();
		menuFile = new JMenu("文件");
		newfile = new JMenuItem("新建(N)");
		openfile = new JMenuItem("打开(O)");
		savefile = new JMenuItem("保存(S)");
		saveAs = new JMenuItem("另存为(A)");
		pageSetup = new JMenuItem("页面设置");
		print = new JMenuItem("打印");
		exit = new JMenuItem("退出(Q)");
		
		menuFile.add(newfile);
		menuFile.add(openfile);
		menuFile.add(savefile);
		menuFile.add(saveAs);
		menuFile.addSeparator();
		menuFile.add(pageSetup);
		menuFile.add(print);
		menuFile.addSeparator();
		menuFile.add(exit);
		menuBar.add(menuFile);

		// 初始化编辑菜单
		menuEdit = new JMenu("编辑");
		cancle = new JMenuItem("撤销(U)");
		recover = new JMenuItem("重做(R)");
		cut = new JMenuItem("剪切(X)");
		copy = new JMenuItem("复制(C)");
		paste = new JMenuItem("粘贴(V)");
		delete = new JMenuItem("删除(D)");
		selectAll = new JMenuItem("全部选中(A)");
		
		menuEdit.add(cancle);
		menuEdit.add(recover);
		menuEdit.add(cut);
		menuEdit.add(copy);
		menuEdit.add(paste);
		menuEdit.add(delete);
		menuEdit.addSeparator();
		menuEdit.add(selectAll);
		menuBar.add(menuEdit);
		// 初始化格式菜单
		menuFormat = new JMenu("格式");
		autowrap = new JCheckBoxMenuItem("自动换行(W)");
		autowrap.setSelected(true);
		font = new JMenuItem("字体(F)--");
		
		menuFormat.add(autowrap);
		menuFormat.add(font);
		menuBar.add(menuFormat);
		// 初始化查看菜单
		menuView = new JMenu("查看");
		statusBar = new JCheckBoxMenuItem("状态栏(S)", false);
		
		menuView.add(statusBar);
		menuBar.add(menuView);
		// 初始化搜索菜单
		searchMenu = new JMenu("搜索");
		find = new JMenuItem("查找(F)...");
		find.setEnabled(false);
		findNext = new JMenuItem("查找下一个(N)");
		findNext.setEnabled(false);
		replace = new JMenuItem("替换(R)...");
		replace.setEnabled(false);
		switchTo = new JMenuItem("转到(G)...");
		switchTo.setEnabled(false);
		
		searchMenu.add(find);
		searchMenu.add(findNext);
		searchMenu.add(switchTo);
		menuBar.add(searchMenu);
		// 初始化帮助菜单
		menuHelp = new JMenu("帮助");
		helpTopics = new JMenuItem("使用手册");
		about = new JMenuItem("关于---");
		
		menuHelp.add(helpTopics);
		menuHelp.add(about);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		textArea=new JTextArea(30,50);
	}
	// 右键菜单
	void initPopMenu() {
		
		pmenu = new JPopupMenu();
		pcancle = new JMenuItem("撤销(U)");
		precover = new JMenuItem("恢复(R)");
		pcut = new JMenuItem("剪切(T)");
		pcut.setEnabled(false);
		pcopy = new JMenuItem("复制(C)");
		pcopy.setEnabled(false);
		ppaste = new JMenuItem("粘贴(P)");
		ppaste.setEnabled(false);
		pdelete = new JMenuItem("删除(D)");
		pdelete.setEnabled(false);
		pselectall = new JMenuItem("全选(A)");
		pselectall.setEnabled(false);
		

		pmenu.add(pcancle);
		pmenu.add(precover);
		pmenu.addSeparator();
		pmenu.add(pcut);
		pmenu.add(pcopy);
		pmenu.add(ppaste);
		pmenu.add(pdelete);
		pmenu.add(pselectall);
		pmenu.addSeparator();
		textArea.setComponentPopupMenu(pmenu);// 调用该方法即可设置右键菜单，无需像AWT中那样使用事件机制

		
	}
	void initShutcut(){
		newfile.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		openfile.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		savefile.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));
		cancle.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
		recover.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));
		selectAll.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		find.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		findNext.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK));
		switchTo.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK));
		replace.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.CTRL_MASK));
	}
	void initPanel3(){
		initPanel1();
		initPanel2();
		// 拆分窗格
		
		JSplitPane pane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel2,
				panel1);
		pane1.setResizeWeight(0.3);
		pane1.setVisible(true);
		add(pane1);
		
	}

	void initPanel1() {
		textArea = new JTextArea(30,40);
//		textArea.setFont(new Font("黑体", 20, 20));
		textArea.setLineWrap(true);// 设置自动换行
		textArea.setWrapStyleWord(true);// 激活断行不断字功能
		textArea.setEnabled(true);
		textArea.setVisible(true);
		scrollpane = new JScrollPane(textArea);
		panel1=uii.getPanel(imgPath);
		panel1.setLayout(new BorderLayout());
		date = new JTextField(20);
		day = new JComboBox<String>();
		day.addItem("星期一");
		day.addItem("星期二");
		day.addItem("星期三");
		day.addItem("星期四");
		day.addItem("星期五");
		day.addItem("星期六");
		day.addItem("星期日");
		wether = new JComboBox<String>();
		wether.addItem("晴");
		wether.addItem("阴");
		wether.addItem("多云");
		wether.addItem("雨");
		wether.addItem("雪");
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("日期"));
		box.add(date);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("星期"));
		box.add(day);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("天气"));
		box.add(wether);
		panel1.add(box,BorderLayout.NORTH);
		panel1.add(textArea,BorderLayout.CENTER);
		panel1.setVisible(true);
		
	}

	void initPanel2() {
		// 设置背景的控件
		panel2=uii.getPanel(imgPath);
		ButtonGroup setBackground = new ButtonGroup();
		JRadioButton[] background = new JRadioButton[4];
		JPanel backPanel = new JPanel();
		String backgroundName[] = { "背景图片1", "背景图片2", "背景图片3", "背景图片4" };
		Box backgroundBox = Box.createVerticalBox();
		for (int i = 0; i < background.length; i++) {
			background[i] = new JRadioButton(backgroundName[i]);
			background[i].setOpaque(false);//设置为非透明或者非纯白，很有用。
//			background[i].addItemListener(this);
			setBackground.add(background[i]);
			backgroundBox.add(background[i]);
			backgroundBox.add(Box.createVerticalStrut(10));
		}
		backPanel.add(backgroundBox);
		backPanel.setOpaque(false);
		backPanel.setBorder(BorderFactory.createTitledBorder(null, "设置皮肤",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog",
						Font.BOLD, 12), new Color(51, 51, 51)));
		// 设置音乐的控件
		JPanel music = new JPanel();
		ButtonGroup musicGroup = new ButtonGroup();
		musicOn = new JRadioButton("开");
		musicOff = new JRadioButton("关");
//		musicOn.addItemListener(this);
//		musicOff.addItemListener(this);
		musicOn.setOpaque(false);
		musicOff.setOpaque(false);
		musicGroup.add(musicOn);
		musicGroup.add(musicOff);
		music.add(musicOn);
		music.add(musicOff);
		music.setOpaque(false);
		music.setBorder(BorderFactory.createTitledBorder(null, "音乐",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog",
						Font.BOLD, 12), new Color(51, 51, 51)));
		// 按钮
		openButton = new JButton("打开以前的日记");
		saveButton = new JButton("保存当前的日记");
		Box panel1Box = Box.createVerticalBox();
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(music);
		panel1Box.add(Box.createVerticalStrut(30));
		panel1Box.add(backPanel);
		panel1Box.add(Box.createVerticalStrut(170));
		panel1Box.add(openButton);
		panel1Box.add(Box.createVerticalStrut(10));
		panel1Box.add(saveButton);
		panel2.add(panel1Box);
		panel2.setVisible(true);
//		openButton.addActionListener(this);
//		saveButton.addActionListener(this);
	}
}
	