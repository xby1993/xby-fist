package ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

import main.FileOperator;
import main.JMusic;
import main.event.NoteFrameEvent;
import source.Strings;

public class NoteFrame extends JFrame implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private Action boldAction = new StyledEditorKit.BoldAction();
	private Action italicAction = new StyledEditorKit.ItalicAction();
	private Action underlineAction = new StyledEditorKit.UnderlineAction();
	private JButton boldButton, italicButton, underlineButton;
	private static final long serialVersionUID = 1L;
	private MusicList musicListdata = new MusicList();
	private JScrollPane musicList = musicListdata.getMusicList();
	private JTabbedPane tabPane = new JTabbedPane(JTabbedPane.NORTH,
			JTabbedPane.SCROLL_TAB_LAYOUT);
	private File tempFile;
	private Cursor cursor=new Cursor(Cursor.HAND_CURSOR);//设置光标
	private JMusic music=new JMusic();
	private int tabs;
	private JButton buttonPic;
	// 菜单条
	private JMenuBar menuBar;
	private String[] jmenuStr = { "文件", "编辑", "搜索", "格式", "帮助" };
	private String[][] jitemStr = {
			{ "新建", "打开", "保存", "另存为", "", "页面设置", "打印", "", "退出" },
			{ "插入图片", "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" },
			{ "查找", "查找下一个", "替换", "转到" }, { "" }, { "说明", "关于" } };
	// 设置快捷键绑定
	private char[][] shortcut = {
			{ 'N', 'O', 'S', ' ', ' ', ' ', 'P', ' ', 'E' },
			{ ' ', 'U', 'Y', 'X', 'C', 'V', 'D', 'A' }, { 'F', 'G', 'R', 'L' },
			{ ' ' }, { ' ', ' ' } };
	// 右键菜单
	private JPopupMenu pmenu;
	private String[] jpopItemStr = { "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" };

	// 编辑文字区
	public JTextPane textPane;
	private ArrayList<JTextPane> textList = new ArrayList<JTextPane>();
	public HTMLDocument htmldoc;
	private ArrayList<HTMLDocument> docList = new ArrayList<HTMLDocument>();
	// private HTMLEditorKit editKit;
	@SuppressWarnings("unused")
	private JScrollPane scrollpane;
	private Box box3;
	private Image img;
	// private String playingMusic = "";
	// 标示状态的工具条
	// private JToolBar status;
	// private JLabel currentstatus;
	String statusinfo = "";
	Timer timer = new Timer();
	Strings strResource = new Strings();
	private JTextField date;// 日期输入框
	// String[] imgPathStr = { "/source/image/1.jpg", "/source/image/2.jpg",
	private ArrayList<String> imgPathStr = strResource.getListIMG();
	// private ArrayListMusic<String> music
	String imgPath = imgPathStr.get(0);
	JPanel panel1, panel2;
	private JRadioButton musicOn, musicOff, taskOn, taskOff;// 背景音乐开关
	private JComboBox<String> day, wether;// 时间,天气选择下拉列表.

	private File tempfile = null;// 临时存储文件
	// 文本是否改变标志
	private Boolean changed = false;
	private ArrayList<Boolean> changedList = new ArrayList<Boolean>();
	// 设置正在播放的歌曲
	private JLabel labelMusic;
	private UndoManager undoManager = new UndoManager();// 新建可撤销、恢复列表类
	private JMenuItem[][] jitem;
	
	// 撤销和恢复

	public NoteFrame() {
		/*
		 * for (int i=0; i<imgPathStr.size();i++)
		 * Logger.getLogger("com.xby.log").info(i+"---->"+imgPathStr.get(i));
		 */
		Logger.getLogger("com.xby.log").info(
				"list.size" + imgPathStr.size() + " ----------" + imgPath);
		setImg(imgPath);

		initBar();
		initPanel3();
		initSystemTray();
		// Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		// int width = size.width / 2;
		// int height = size.height / 2;
		// setBounds((size.width - width) / 2, (size.height - height) / 2,
		// width,
		// height);
		// setTabs(textPane, 2);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/source/image/splash.jpg")));
//		setFont(new Font(strResource.getFont_Familly_Name().get(5),Font.PLAIN,14));
		pack();
		setLocationRelativeTo(null);
		// 先关闭默认操作
		/*
		 * setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); addWindowListener(new
		 * WindowAdapter() { public void windowClosing(WindowEvent we) { new
		 * FileOperator(NoteFrame.this).exit(); } });
		 */
		/*
		 * try { //
		 * UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"
		 * ); //
		 * UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
		 * ); UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		 * } catch (ClassNotFoundException | InstantiationException |
		 * IllegalAccessException | UnsupportedLookAndFeelException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } //
		 * setDefaultCloseOperation(EXIT_ON_CLOSE); //
		 * JFrame.setDefaultLookAndFeelDecorated(false);
		 * 
		 * SwingUtilities.updateComponentTreeUI(this);//使更改的lookandfeel立即生效
		 */
//		Graphics2D g2=(Graphics2D)getGraphics();
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		repaint();
		SetLookAndFeel.setLookAndFeel(this);
		
		setVisible(true);

	}

	void initSystemTray() {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
//			Image trayImg = new ImageIcon(NoteFrame.class.getResource("/source/image/Loading.ico")).getImage();?
			Image trayImg=Toolkit.getDefaultToolkit().getImage(NoteFrame.class.getResource("/source/image/splash.jpg"));
			PopupMenu trayPop = new PopupMenu();
			MenuItem open = new MenuItem("打开");
			MenuItem close = new MenuItem("关闭");
			open.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(true);
				}
			});
			close.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(true);
					new FileOperator(NoteFrame.this).exit();
				}
			});
			trayPop.add(open);
			trayPop.add(close);
			TrayIcon trayIcon = new TrayIcon(trayImg, "小白杨日记本", trayPop);
			trayIcon.setImageAutoSize(true);//为了让系统托盘正常显示，需要这样设置
			
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						setVisible(true);
					}
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	void initFormat(JMenu menu) {
		Action action = new StyledEditorKit.BoldAction();
		action.putValue(Action.NAME, "Bold");
		menu.add(action);

		action = new StyledEditorKit.ItalicAction();
		action.putValue(Action.NAME, "Italic");
		menu.add(action);

		action = new StyledEditorKit.UnderlineAction();
		action.putValue(Action.NAME, "Underline");
		menu.add(action);

		menu.addSeparator();

		menu.add(new StyledEditorKit.FontSizeAction("12", 12));
		menu.add(new StyledEditorKit.FontSizeAction("14", 14));
		menu.add(new StyledEditorKit.FontSizeAction("18", 18));

		menu.addSeparator();

		menu.add(new StyledEditorKit.FontFamilyAction("Serif", "Serif"));
		menu.add(new StyledEditorKit.FontFamilyAction("SansSerif", "SansSerif"));

		menu.addSeparator();

		menu.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
		menu.add(new StyledEditorKit.ForegroundAction("Green", Color.green));
		menu.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
		menu.add(new StyledEditorKit.ForegroundAction("Black", Color.black));
	}

	void initBar() {
		// 初始化文件菜单
		Logger.getLogger("com.xby.log").info(0 + "-->" + imgPathStr.get(0));
		Logger.getLogger("com.xby.log").info(1 + "imgPath-->" + imgPath);
		menuBar = new JMenuBar();
		JMenu[] jmenu = new JMenu[jmenuStr.length];
		jitem = new JMenuItem[jitemStr.length][];
		jmenu[3] = new JMenu("格式");
		initFormat(jmenu[3]);// 初始化格式菜单
		menuBar.add(jmenu[3]);
		for (int i = 0; i < jmenu.length; i++) {
			if (i == 3)
				continue;
			jmenu[i] = new JMenu(jmenuStr[i]);
			jmenu[i].setCursor(cursor);
			jitem[i] = new JMenuItem[jitemStr[i].length];
			for (int j = 0; j < jitem[i].length; j++) {
				if ("".equals(jitemStr[i][j])) {
					jmenu[i].addSeparator();
				} else {
					jitem[i][j] = new JMenuItem(jitemStr[i][j]);
					jitem[i][j].setCursor(cursor);
					jitem[i][j].addActionListener(this);
					jmenu[i].add(jitem[i][j]);
					if (shortcut[i][j] == ' ') {
						continue;
					} else {
						jitem[i][j].setAccelerator(KeyStroke.getKeyStroke(
								shortcut[i][j], InputEvent.CTRL_MASK));
					}
				}
			}

			menuBar.add(jmenu[i]);
		}
		menuBar.setCursor(cursor);
		setJMenuBar(menuBar);
		initPopMenu();
		// initTextArea();

	}

	void initPanel3() {
		initPanel1();
		initPanel2();
		// 拆分窗格

		JSplitPane pane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel2,
				panel1);
		pane1.setResizeWeight(0.3);
		pane1.setVisible(true);
		add(pane1);

	}

	void initPopMenu() {
		pmenu = new JPopupMenu();
		JMenuItem[] jpopItem = new JMenuItem[jpopItemStr.length];
		for (int i = 0; i < jpopItem.length; i++) {
			jpopItem[i] = new JMenuItem(jpopItemStr[i]);
			jpopItem[i].addActionListener(this);
			pmenu.add(jpopItem[i]);

		}

	}

	void initPanel1() {
		// MyPanel.setImgPath(imgPath);
		panel1 = new MyPanel();
		panel1.setLayout(new BorderLayout());
		date = new JTextField(20);
		day = new JComboBox<String>();
		String[] week = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" };
		for (int i = 0; i < week.length; i++) {
			day.addItem(week[i]);
		}
		String[] wetherStr = { "晴", "阴", "多云", "雨", "雪" };
		wether = new JComboBox<String>();
		for (int i = 0; i < wetherStr.length; i++) {
			wether.addItem(wetherStr[i]);
		}
		boldAction.putValue(Action.NAME, "粗体");
		italicAction.putValue(Action.NAME, "斜体");
		underlineAction.putValue(Action.NAME, "下划线");
		buttonPic = new JButton("插入图片");
		buttonPic.addActionListener(this);
		boldButton = new JButton(boldAction);
		boldButton.setFont(new Font(strResource.getFont_Familly_Name().get(5),Font.PLAIN,14));
		italicButton = new JButton(italicAction);
		underlineButton = new JButton(underlineAction);
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("日期"));
		box.add(date);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("星期"));
		box.add(day);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("天气"));
		box.add(wether);
		Box box2 = Box.createHorizontalBox();
		box2.add(boldButton);
		box2.add(Box.createHorizontalStrut(30));
		box2.add(italicButton);
		box2.add(Box.createHorizontalStrut(30));
		box2.add(underlineButton);
		box2.add(Box.createHorizontalStrut(30));
		box2.add(buttonPic);
		box3 = Box.createVerticalBox();
		box3.add(box);
		box3.add(Box.createVerticalStrut(20));
		box3.add(box2);
		panel1.add(box3, BorderLayout.NORTH);
		panel1.setCursor(cursor);
		tabAdd();
		// initTextPane();
		// tabPane.add("pane0", new JScrollPane(textPane));
		// tabPane.setTabComponentAt(0, new ButtonTab(tabPane));// 用于设置可关闭Tab头部
		panel1.add(tabPane, BorderLayout.CENTER);
		panel1.setVisible(true);

	}

	// void initTextPane(){
	// textPane=new JTextPane();
	// htmldoc=new HTMLDocument();
	// textPane.setFont(new Font("黑体", 20, 20));
	// textPane.setEnabled(true);
	// textPane.setVisible(true);
	// editKit = new HTMLEditorKit();
	// // 实例化一个HTMLEditorkit工具包，用来编辑和解析用来显示在jtextpane中的内容。
	// // doci = (HTMLDocument) editKit.createDefaultDocument();
	// // 使用HTMLEditorKit类的方法来创建一个文档类，HTMLEditorKit创建的类型默认为htmldocument。
	// // 设置jtextpane组件的编辑器工具包，是其支持html格式。
	// // textPanei.setContentType("text/html");
	// // 设置编辑器要处理的文档内容类型，有text/html,text/rtf.text/plain三种类型。
	// textPane.setEditorKit(editKit);
	// textPane.setDocument(htmldoc);
	// // textPane.setTabSize(2);// 设置tab键大小
	//
	// textPane.setBorder(new LineBorder(Color.BLUE, 4, true));
	// textPane.getDocument().addUndoableEditListener(new UndoableEditListener()
	// {
	//
	// @Override
	// public void undoableEditHappened(UndoableEditEvent e) {
	// // TODO Auto-generated method stub
	// undoManager.addEdit(e.getEdit());
	// }
	// });// 添加可撤销、恢复编辑监听
	// textPane.setComponentPopupMenu(pmenu);
	// }
	public void tabAdd() {
		tabs = tabPane.getTabCount();
		// tabs = getTextList().size();
		if (tabs < 0)
			tabs = 0;
		int i = tabs;

		JTextPane textPanei = new JTextPane();
		HTMLDocument doci = new HTMLDocument();
//		textPanei.setFont(new Font("黑体", 20, 20));
		textPanei.setFont(new Font("方正黑体_GBK",Font.PLAIN,14));
		// textPane.setLineWrap(true);// 设置自动换行
		// textPane.setWrapStyleWord(true);// 激活断行不断字功能
		textPanei.setEnabled(true);
		textPanei.setVisible(true);
		HTMLEditorKit editKit = new HTMLEditorKit();
		// 实例化一个HTMLEditorkit工具包，用来编辑和解析用来显示在jtextpane中的内容。
		// doci = (HTMLDocument) editKit.createDefaultDocument();
		// 使用HTMLEditorKit类的方法来创建一个文档类，HTMLEditorKit创建的类型默认为htmldocument。
		// 设置jtextpane组件的编辑器工具包，是其支持html格式。
		// textPanei.setContentType("text/html");
		// 设置编辑器要处理的文档内容类型，有text/html,text/rtf.text/plain三种类型。
		textPanei.setEditorKit(editKit);
		// textPane.setTabSize(2);// 设置tab键大小

		JScrollPane scrollPanei = new JScrollPane(textPanei);
		textPanei.setBorder(new LineBorder(Color.BLUE, 4, true));
		// scrollpane = new JScrollPane(textArea);
		// panel1=uii.getPanel(imgPath);

		textList.add(textPanei);
		docList.add(doci);
		textPanei.setStyledDocument(docList.get(i));
		// textPanei.getDocument().addUndoableEditListener(this);// 添加可撤销、恢复编辑监听
		textPanei.getDocument().addUndoableEditListener(
				new UndoableEditListener() {

					@Override
					public void undoableEditHappened(UndoableEditEvent e) {
						// TODO Auto-generated method stub
						undoManager.addEdit(e.getEdit());
					}
				});// 添加可撤销、恢复编辑监听
		textPanei.setComponentPopupMenu(pmenu);
		tabPane.add("pane" + i, scrollPanei);
		if (tabs == 0) {
			tabPane.setTabComponentAt(0, null);
		} else {

			tabPane.setTabComponentAt(i, new ButtonTab(tabPane, this));// 用于设置可关闭Tab头部
			// tabPane.setSelectedIndex(i);
		}
		tabPane.setSelectedIndex(tabs);
		getChangedList().add(false);
		// htmldoc = textPane.getDocument();

	}

	void initPanel2() {
		// 设置背景的控件
		// panel2=uii.getPanel(imgPath);
		// MyPanel.setImgPath(imgPath);
		panel2 = new MyPanel();
		// 设置音乐的控件
		JPanel musicPanel = new JPanel();
		JPanel taskPanel = new JPanel();
		ButtonGroup musicGroup = new ButtonGroup();
		ButtonGroup taskGroup = new ButtonGroup();
		
		musicOn = new JRadioButton("开");
		musicOff = new JRadioButton("关");
		taskOn = new JRadioButton("幻灯片开");
		taskOff = new JRadioButton("幻灯片关");
		musicOn.addItemListener(this);
		musicOff.addItemListener(this);
		taskOn.addItemListener(this);
		taskOff.addItemListener(this);
		taskOn.setOpaque(false);
		taskOff.setOpaque(false);
		musicOn.setOpaque(false);
		musicOff.setOpaque(false);
		musicGroup.add(musicOn);
		musicGroup.add(musicOff);
		taskGroup.add(taskOn);
		taskGroup.add(taskOff);
		musicPanel.add(musicOn);
		musicPanel.add(musicOff);
		taskPanel.add(taskOn);
		taskPanel.add(taskOff);
		taskPanel.setOpaque(false);
		musicPanel.setOpaque(false);
		TitledBorder titleBorder1 = new TitledBorder(new BevelBorder(
				BevelBorder.RAISED), "音乐", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(
						51, 51, 51));
		musicPanel.setBorder(titleBorder1);
		/*
		 * music.setBorder(BorderFactory.createTitledBorder(null, "音乐",
		 * TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD,
		 * 12), new Color(51, 51, 51)));
		 */
		TitledBorder titleBorder2 = new TitledBorder(new EtchedBorder(
				EtchedBorder.RAISED), "幻灯片", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(
						51, 51, 51));
		/*
		 * taskPanel.setBorder(BorderFactory.createTitledBorder(null, "幻灯片",
		 * TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD,
		 * 12), new Color(51, 51, 51)));
		 */
		taskPanel.setBorder(titleBorder2);
		labelTime = new JLabel();//用于显示歌曲时间
		labelTime.setBorder(new BevelBorder(BevelBorder.RAISED));
		labelTime.setAutoscrolls(true);
//		labelTime.setForeground(Color.BLUE);
		labelTime.setFont(new Font("serif",Font.BOLD,16));
		labelTime.setAlignmentX(CENTER_ALIGNMENT);
		music.setLabelTime(labelTime);
		// 按钮
		Box panel1Box = Box.createVerticalBox();
//	    progressBar.setIndeterminate(true);
		panel1Box.add(labelTime);
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(musicPanel);
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(taskPanel);
		panel1Box.add(Box.createVerticalStrut(30));
//		 panel1Box.add(backPanel);
		
		JPanel listPanel = new JPanel();
		labelMusic = new JLabel();
		labelMusic.setAlignmentX(CENTER_ALIGNMENT);
		setLabelMusic(labelMusic);
		TitledBorder titleBorder3 = new TitledBorder(new EtchedBorder(
				EtchedBorder.RAISED), "播放列表", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(
						51, 51, 51));
		listPanel.setBorder(titleBorder3);
		listPanel.add(musicList);
		panel1Box.add(getLabelMusic());
		panel1Box.add(listPanel);
		panel1Box.add(Box.createVerticalStrut(150));
		panel2.add(panel1Box);
		panel2.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i = tabPane.getSelectedIndex();
		NoteFrameEvent event = new NoteFrameEvent(this);
		event.actionEvent(e);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		music.setLabel(labelMusic);
		// Thread thread=new Thread(new LabelThread(labelMusic));
		JRadioButton tempButton = (JRadioButton) e.getSource();
		if (tempButton.isSelected()) {
			String text = tempButton.getText();
			if (text.equals("开")) {
				music.musicOpen();
				// thread.start();
			} else if (text.equals("关")) {
				music.musicShut();
				// thread.interrupt();
			} else if (text.equals("幻灯片开")) {
				taskOpen();
			} else if (text.equals("幻灯片关")) {
				taskCancel();
			}
		}
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public JTextPane getJTextPane() {
		return getTextList().get(tabPane.getSelectedIndex());
	}

	public HTMLDocument getMyDocument() {
		return getDocList().get(tabPane.getSelectedIndex());
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	/**
	 * @return the changed
	 */
	public Boolean getChanged() {

		return getChangedList().get(tabPane.getSelectedIndex());
	}

	/**
	 * @param changed
	 *            the changed to set
	 */
	public void setChanged(Boolean changed) {
		getChangedList().set(tabPane.getSelectedIndex(), changed);
	}

	void backgroundChange(String text) {

	}

	public String getDate() {
		return date.getText() + "   " + day.getSelectedItem() + "   "
				+ wether.getSelectedItem();
	}

	TimerTask task = new TimerTask() {

		int i = 0;

		public void run() {
			if (i > imgPathStr.size() - 1) {
				i = 0;
			}
			imgPath = imgPathStr.get(i++);
			Logger.getLogger("com.xby.log").info("timer----->" + imgPath);
			setImg(imgPath);
			panel1.repaint();
			panel2.repaint();
		}
	};
	private JLabel labelTime;

	// private JRadioButton[] background;

	void taskOpen() {

		timer.schedule(task, 10000, 10000);
	}

	void taskCancel() {
		timer.cancel();
	}

	public int getSelectTabIndex() {
		return tabPane.getSelectedIndex();
	}

	private void setImg(String path) {
		this.imgPath = path;
		// img=new ImageIcon(NoteFrame.class.getResource(imgPath)).getImage();
		if (imgPath.equals(strResource.getDefaultIMG())) {
			img = new ImageIcon(NoteFrame.class.getResource(imgPath))
					.getImage();
		} else {
			try {
				img = ImageIO.read(new File(imgPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			setOpaque(false);// 设置false以便于设置背景
			// 启用图像缓存设置好图像。
			BufferedImage bufImg = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2d = bufImg.createGraphics();
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
			super.paint(g);
		}
	}

	 public static void main(String[] args) {
	 // 以下两句必须放在main（）方法中才能生效，且要在创建窗体之前执行
	 JFrame.setDefaultLookAndFeelDecorated(true);// 使标题栏装饰生效
	 JDialog.setDefaultLookAndFeelDecorated(true);// 使对话框装饰生效
	 new NoteFrame();
	 }
	/**
	 * @return the labelMusic
	 */
	public JLabel getLabelMusic() {
		return labelMusic;
	}

	/**
	 * @param labelMusic
	 *            the labelMusic to set
	 */
	public void setLabelMusic(JLabel labelMusic) {
		this.labelMusic = labelMusic;
	}

	/**
	 * @return the textList
	 */
	public ArrayList<JTextPane> getTextList() {
		return textList;
	}

	/**
	 * @param textList
	 *            the textList to set
	 */
	public void setTextList(ArrayList<JTextPane> textList) {
		this.textList = textList;
	}

	/**
	 * @return the docList
	 */
	public ArrayList<HTMLDocument> getDocList() {
		return docList;
	}

	/**
	 * @param docList
	 *            the docList to set
	 */
	public void setDocList(ArrayList<HTMLDocument> docList) {
		this.docList = docList;
	}

	/**
	 * @return the changedList
	 */
	public ArrayList<Boolean> getChangedList() {
		return changedList;
	}

	/**
	 * @param changedList
	 *            the changedList to set
	 */
	public void setChangedList(ArrayList<Boolean> changedList) {
		this.changedList = changedList;
	}
	

	/**
	 * @return the progressBar
	 */
	
}
