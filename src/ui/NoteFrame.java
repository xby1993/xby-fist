package ui;

import java.applet.Applet;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import main.Help;

public class NoteFrame extends JFrame implements UndoableEditListener,ActionListener,ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 菜单条
	private JMenuBar menuBar;
	String[] jmenuStr={"文件","编辑","搜索","格式","帮助"};
	String[][] jitemStr={{"新建","打开","保存","另存为","","页面设置","打印","","退出"},
			{"撤销","恢复","剪切","复制","粘贴","删除","全选"},{"查找","查找下一个","替换","转到"},
			{"字体"},{"说明","关于"}};
	//设置快捷键绑定
	char[][] shortcut = {{'N','O','S',' ',' ',' ','P',' ','E'},{'U','Y','X','C','V','D','A'},
			{'F','G','R','L'},{' '},{' ',' '}};
	//右键菜单
	private JPopupMenu pmenu;
	String[] jpopItemStr = {"撤销","恢复","剪切","复制","粘贴","删除","全选"};
	
	// 编辑文字区
	private JJTextArea textArea;
	private JScrollPane scrollpane;
	
	// 标示状态的工具条
	private JToolBar status;
	private JLabel currentstatus;
	String statusinfo = "";

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

	public NoteFrame() {
		initBar();
		initPanel3();
		initPopMenu();
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
		JMenu[] jmenu=new JMenu[jmenuStr.length];
		JMenuItem[][] jitem=new JMenuItem[jitemStr.length][];
		for (int i = 0; i < jmenu.length; i++) {
			jmenu[i]=new JMenu(jmenuStr[i]);
			jitem[i]=new JMenuItem[jitemStr[i].length];	
			for (int j = 0; j < jitem[i].length; j++) {
				if ("".equals(jitemStr[i][j])) {
					jmenu[i].addSeparator();
				} else {
					jitem[i][j]=new JMenuItem(jitemStr[i][j]);
					jitem[i][j].addActionListener(this);
					jmenu[i].add(jitem[i][j]);
					if(shortcut[i][j]==' '){
						continue;
					}else{
						jitem[i][j].setAccelerator(KeyStroke.getKeyStroke(shortcut[i][j], InputEvent.CTRL_MASK));
					}
				}
			}
			
			
			menuBar.add(jmenu[i]);
		}
		setJMenuBar(menuBar);
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
	void initPopMenu(){
		pmenu=new JPopupMenu();
		JMenuItem[] jpopItem=new JMenuItem[jpopItemStr.length];
		for (int i = 0; i < jpopItem.length; i++) {
			jpopItem[i] = new JMenuItem(jpopItemStr[i]);
			jpopItem[i].addActionListener(this);
			pmenu.add(jpopItem[i]);
			
		}
		textArea.setComponentPopupMenu(pmenu);
	}

	void initPanel1() {
		textArea = new JJTextArea(imgPath);
//		textArea.setFont(new Font("黑体", 20, 20));
		textArea.setLineWrap(true);// 设置自动换行
		textArea.setWrapStyleWord(true);// 激活断行不断字功能
		textArea.setEnabled(true);
		textArea.setVisible(true);
//		scrollpane = new JScrollPane(textArea);
//		panel1=uii.getPanel(imgPath);
		panel1=new JJPanel(imgPath);
		panel1.setLayout(new BorderLayout());
		date = new JTextField(20);
		day = new JComboBox<String>();
		String[] week={"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
		for (int i = 0; i < week.length; i++) {
			day.addItem(week[i]);
		}
		String[] wetherStr={"晴","阴","多云","雨","雪"};
		wether = new JComboBox<String>();
		for (int i = 0; i < wetherStr.length; i++) {
			wether.addItem(wetherStr[i]);
		}
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
		panel1.add(new JScrollPane(textArea),BorderLayout.CENTER);
		panel1.setVisible(true);
		
	}

	void initPanel2() {
		// 设置背景的控件
//		panel2=uii.getPanel(imgPath);
		panel2=new JJPanel(imgPath);
		ButtonGroup setBackground = new ButtonGroup();
		JRadioButton[] background = new JRadioButton[4];
		JPanel backPanel = new JPanel();
		String backgroundName[] = { "背景图片1", "背景图片2", "背景图片3", "背景图片4" };
		Box backgroundBox = Box.createVerticalBox();
		for (int i = 0; i < background.length; i++) {
			background[i] = new JRadioButton(backgroundName[i]);
			background[i].setOpaque(false);//设置为非透明或者非纯白，很有用。
			background[i].addItemListener(this);
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
		musicOn.addItemListener(this);
		musicOff.addItemListener(this);
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
		Box panel1Box = Box.createVerticalBox();
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(music);
		panel1Box.add(Box.createVerticalStrut(30));
		panel1Box.add(backPanel);
		panel1Box.add(Box.createVerticalStrut(170));
		panel1Box.add(Box.createVerticalStrut(10));
		panel2.add(panel1Box);
		panel2.setVisible(true);
	}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (e.getActionCommand()) {
			case "关于":
				Help.about();
				break;
			case "说明":
				Help.help();
				break;
			case "字体":
				break;
			case "查找":
				break;
			case "查找下一个":
			case "替换":
			case "转到":
			case "撤销":
			case "恢复":
			case "剪切":
			case "复制":
			case "粘贴":
			case "删除":
			case "全选":
			case "新建":
			case "打开":
			case "保存":
			case "另存为":
			case "页面设置":
			case "打印":
			case "退出":
		default:
			break;
		}
	}


	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	
}
}
	