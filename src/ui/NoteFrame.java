package ui;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

import main.event.NoteFrameEvent;
import main.music.JMusic;
import main.music.MusicList;
import main.operation.FileOperator;
import main.operation.FontFrame;
import main.operation.FontSizeFrame;
import main.operation.InitText;
import main.thread.ThreadBean;
import source.Strings;
import util.ButtonProvider;
import util.LoopButtonProvider;
import util.ToolButtonProvider;

public class NoteFrame extends JFrame implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 托盘相关
	private final String trayHints = "温馨提示:感谢您的使用,您可以右击或者双击系统托盘进行选择";
	private final String trayIconPath = "/source/image/splash.jpg";
	// 首选项相关
	private Preferences root = Preferences.userRoot();
	private Preferences node = root.node("com.xby.preferen");
	// 字体按钮相关
	private JButton boldButton, italicButton, underlineButton;
	// 音乐列表相关
	private MusicList musicListdata = new MusicList();
	private JList<String> musicList = musicListdata.getMusicList();
	// 多标签式浏览相关
	private JTabbedPane tabPane = new JTabbedPane(JTabbedPane.NORTH,
			JTabbedPane.SCROLL_TAB_LAYOUT);
	private File tempFile;
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);// 设置光标
	private JMusic music = new JMusic();// 音乐类实例
	private int tabs;
	private JButton buttonPic;// 插入图片按钮
	/** 用于控制歌曲上下播放按钮 */
	private JButton fowardButton, backButton;
	/** 用于显示音乐播放进度 */
	private JProgressBar progress = new JProgressBar(0, 100);
	// 菜单条
	private JJMenuBar menuBar;
	// 菜单设置字符资源
	private String[] jmenuStr = { "文件", "编辑", "搜索", "格式", "帮助" };
	private String[][] jitemStr = {
			{ "新建", "打开", "保存", "另存为", "", "页面设置", "打印", "", "退出" },
			{ "插入图片", "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" },
			{ "查找", "替换" }, { "" }, { "说明", "关于" } };
	// 设置快捷键绑定
	private char[][] shortcut = {
			{ 'N', 'O', 'S', ' ', ' ', ' ', 'P', ' ', 'E' },
			{ ' ', 'U', 'Y', 'X', 'C', 'V', 'D', 'A' }, { 'F', 'R' }, { ' ' },
			{ ' ', ' ' } };
	// 右键菜单
	private JPopupMenu pmenu;
	private String[] jpopItemStr = { "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" };

	// 编辑文字区
	public JJTextPane textPane;
	// 使用列表储存标签中的组件
	private ArrayList<JJTextPane> textList = new ArrayList<JJTextPane>();
	public HTMLDocument htmldoc;
	private ArrayList<HTMLDocument> docList = new ArrayList<HTMLDocument>();
	// private HTMLEditorKit editKit;
	@SuppressWarnings("unused")
	private JScrollPane scrollpane;
	private Box box3;
	private Image img;
	// 标示状态的工具条
	// 用于背景图片变化的定时器
	private Timer timer = new Timer();
	// 字符串资源实例
	private Strings strResource = Strings.getInstance();
	private JTextField date;// 日期输入框
	// 使用列表储存图片资源索引
	private ArrayList<String> imgPathStr = strResource.getListIMG();
	// private ArrayListMusic<String> music
	// 面板背景图片
	private String imgPath = imgPathStr.get(0);
	// 主要面板
	private JPanel panel1, panel2;
	private JRadioButton musicOn, musicOff, taskOn, taskOff;// 背景音乐开关
	private JComboBox<String> day, wether;// 时间,天气选择下拉列表.

	private File tempfile = null;// 临时存储文件
	// 文本是否改变标志
	private Boolean changed = false;
	private ArrayList<Boolean> changedList = new ArrayList<Boolean>();
	// 设置正在播放的歌曲
	private JJLabel labelMusic;
	private UndoManager undoManager = new UndoManager();// 新建可撤销、恢复列表类
	private JJMenuItem[][] jitem;
	// private String[] playControl = { "列表循环", "单曲循环", "随机播放" };
	// private JComboBox<String> playBox = new JComboBox<String>(playControl);
	private ButtonProvider bProvider = new LoopButtonProvider();

	/*
	 * private ImageIcon[] iconsSingle = UIUtils.getMusicIcon("single", 3);
	 * private ImageIcon[] iconsRandom = UIUtils.getMusicIcon("random", 3);
	 * private ImageIcon[] iconsLoop = UIUtils.getMusicIcon("loop", 3);
	 */
	// private final NoteFrame frame=new NoteFrame();
	// 用于重新加载音乐与图片资源
	private JButton reload;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource(trayIconPath)));
		// setFont(new
		// Font(strResource.getFont_Familly_Name().get(5),Font.PLAIN,14));
		if (textList.get(0) != null) {
			new InitText(textList.get(0)).start();
		}
		pack();
		setLocationRelativeTo(null);
		// SetLookAndFeel.setLookAndFeel(this);
		music.setPlayingMusicIndex(node.getInt("i", 0) - 1);
		// music.setMusicList(musicList);
		// SetLookAndFeel.setDecorated();
		setVisible(true);

	}

	/**
	 * 设置系统托盘
	 */
	void initSystemTray() {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			// Image trayImg = new
			// ImageIcon(NoteFrame.class.getResource("/source/image/Loading.ico")).getImage();?
			Image trayImg = Toolkit.getDefaultToolkit().getImage(
					NoteFrame.class.getResource(trayIconPath));
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
					node.putInt("i", music.getPlayingMusicIndex());
					new FileOperator(NoteFrame.this).exit();
				}
			});
			trayPop.add(open);
			trayPop.add(close);
			final TrayIcon trayIcon = new TrayIcon(trayImg, "小白杨日记本", trayPop);
			trayIcon.setImageAutoSize(true);// 为了让系统托盘正常显示，需要这样设置

			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					trayIcon.displayMessage("小白杨日记本", trayHints,
							TrayIcon.MessageType.INFO);
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

	/**
	 * 初始化格式菜单
	 * 
	 * @param menu
	 */
	void initFormat(JMenu menu) {
		// Action action = new StyledEditorKit.BoldAction();
		// action.putValue(Action.NAME, "Bold");
		// menu.add(action);
		JMenuItem sizeMenu = new JJMenuItem("字体大小");
		sizeMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FontSizeFrame(getJJTextPane());
			}
		});
		JMenuItem colorItem = new JJMenuItem("颜色选择");
		colorItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color reply = JColorChooser.showDialog(null, "颜色选择", Color.red);
				if (reply != null) {
					// colorMenu.add(new
					// StyledEditorKit.ForegroundAction(reply.toString(),
					// reply));
					MutableAttributeSet attr = new SimpleAttributeSet();
					StyleConstants.setForeground(attr, reply);
					// setCharacterAttributes(getJJTextPane(), attr, false);
					getJJTextPane().setCharacterAttributes(attr, false);
					try {
						getJJTextPane().getStyledDocument().insertString(
								getJJTextPane().getSelectionEnd(), " ", null);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					StyleConstants.setForeground(attr, Color.BLACK);
					getJJTextPane().getStyledDocument().setCharacterAttributes(
							getJJTextPane().getSelectionEnd(), 1, attr, false);

				}
			}
		});
		JMenuItem fontItem = new JJMenuItem("字体风格选择");
		fontItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FontFrame(getJJTextPane());
			}
		});
		menu.add(fontItem);
		menu.add(colorItem);
		menu.add(sizeMenu);
		// menu.add(new StyledEditorKit.FontSizeAction("14", 14));
		// menu.add(new StyledEditorKit.FontFamilyAction("SansSerif",
		// "SansSerif"));
		// menu.add(new StyledEditorKit.ForegroundAction("Black", Color.black));
	}

	/**
	 * 初始化菜单栏
	 */
	void initBar() {
		// 初始化文件菜单
		Logger.getLogger("com.xby.log").info(0 + "-->" + imgPathStr.get(0));
		Logger.getLogger("com.xby.log").info(1 + "imgPath-->" + imgPath);
		menuBar = new JJMenuBar();
		JMenu[] jmenu = new JMenu[jmenuStr.length];
		jitem = new JJMenuItem[jitemStr.length][];
		jmenu[3] = new JMenu("格式");
		initFormat(jmenu[3]);// 初始化格式菜单
		menuBar.add(jmenu[3]);
		for (int i = 0; i < jmenu.length; i++) {
			if (i == 3)
				continue;
			jmenu[i] = new JJMenu(jmenuStr[i]);
			jmenu[i].setCursor(cursor);
			jitem[i] = new JJMenuItem[jitemStr[i].length];
			for (int j = 0; j < jitem[i].length; j++) {
				if ("".equals(jitemStr[i][j])) {
					jmenu[i].addSeparator();
				} else {
					jitem[i][j] = new JJMenuItem(jitemStr[i][j]);
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

	/**
	 * 初始化总体面板
	 */
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

	/**
	 * 初始化弹出菜单
	 */
	void initPopMenu() {
		pmenu = new JPopupMenu();
		JJMenuItem[] jpopItem = new JJMenuItem[jpopItemStr.length];
		for (int i = 0; i < jpopItem.length; i++) {
			jpopItem[i] = new JJMenuItem(jpopItemStr[i]);
			jpopItem[i].addActionListener(this);
			pmenu.add(jpopItem[i]);

		}

	}

	/**
	 * 初始化面板一
	 */
	void initPanel1() {
		// MyPanel.setImgPath(imgPath);
		panel1 = new MyPanel();
		panel1.setLayout(new BorderLayout());
		date = new JTextField(20);
		SimpleDateFormat simpleData = new SimpleDateFormat("yyy-MM-dd");
		date.setText(simpleData.format(new Date()) + "");
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
		// buttonPic = new JButton("插入图片");
		tbProvider = new ToolButtonProvider();
		boldButton = tbProvider.getButton("粗体");
		// italicButton = new JJButton("斜体");
		italicButton = tbProvider.getButton("斜体");
		// underlineButton = new JJButton("下划线");
		underlineButton = tbProvider.getButton("下划线");
		// 添加标签
		buttonPic = tbProvider.getButton("插入图片");
		buttonPic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"jpg & gif&png图像文件", "jpg", "JPG", "jpeg", "JPEG",
						"GIF", "gif", "png", "PNG");// 设置可选文件后缀名
				chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
				chooser.setFileFilter(filter);
				// final JLabel label=new JLabel();
				final PaintPanel panel = new PaintPanel();
				panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
						null, null, null));
				panel.setPreferredSize(new Dimension(300, 300));
				chooser.setAccessory(panel);
				chooser.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY == e
								.getPropertyName()) {
							File picfile = (File) e.getNewValue();
							if (picfile != null && picfile.isFile()) {

								// String imagePath = picfile.getPath();
								// panel.setImgPath(imagePath);
								Image image;
								try {
									image = Toolkit.getDefaultToolkit()
											.getImage(picfile.toURI().toURL());
									panel.setImage(image);
									panel.repaint();
								} catch (MalformedURLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						}
					}
				});
				if (chooser.showOpenDialog(null) == 0) {
					File file = chooser.getSelectedFile();
					if (file.exists()) {

						ImageIcon icon = new ImageIcon(file.getPath());
						getJJTextPane().insertIcon(icon);
					}
				}

			}
		});
		tabAdd();
		boldButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setBold(attr, true);
				getJJTextPane().setCharacterAttributes(attr, false);
				try {
					getJJTextPane().getStyledDocument().insertString(
							getJJTextPane().getSelectionEnd(), " ", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StyleConstants.setBold(attr, false);
				getJJTextPane().getStyledDocument().setCharacterAttributes(
						getJJTextPane().getSelectionEnd(), 1, attr, false);
			}
		});
		italicButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setItalic(attr, true);
				getJJTextPane().setCharacterAttributes(attr, false);
				try {
					getJJTextPane().getStyledDocument().insertString(
							getJJTextPane().getSelectionEnd(), " ", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StyleConstants.setItalic(attr, false);
				getJJTextPane().getStyledDocument().setCharacterAttributes(
						getJJTextPane().getSelectionEnd(), 1, attr, false);
			}
		});
		underlineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setUnderline(attr, true);
				getJJTextPane().setCharacterAttributes(attr, false);
				try {
					getJJTextPane().getStyledDocument().insertString(
							getJJTextPane().getSelectionEnd(), " ", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StyleConstants.setUnderline(attr, false);
				getJJTextPane().getStyledDocument().setCharacterAttributes(
						getJJTextPane().getSelectionEnd(), 1, attr, false);
			}
		});
		reload = new JButton("重新加载");
		// final ResourceReload load=new ResourceReload();
//		final MusicList mlist = new MusicList();
		reload.addActionListener(new ActionListener() {

			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// load.reload();
				DefaultListModel<String> model=(DefaultListModel<String>)musicList.getModel();
				model.clear();
				Vector<String> listStr=new MusicList().getListStr();
				for (String str : listStr) {
					model.addElement(str);
				}
				imgPathStr = strResource.getListIMG();
				ThreadBean.setReload(true);
			}
		});

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
		box2.add(reload);
		box2.add(Box.createHorizontalStrut(30));
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
		// initTextPane();
		// tabPane.add("pane0", new JScrollPane(textPane));
		// tabPane.setTabComponentAt(0, new ButtonTab(tabPane));// 用于设置可关闭Tab头部
		panel1.add(tabPane, BorderLayout.CENTER);
		panel1.setVisible(true);

	}

	/**
	 * 添加标签方法
	 */
	public void tabAdd() {
		tabs = tabPane.getTabCount();
		// tabs = getTextList().size();
		if (tabs < 0)
			tabs = 0;
		int i = tabs;

		JJTextPane textPanei = new JJTextPane();
		HTMLDocument doci = new HTMLDocument();
		// textPanei.setFont(new Font("黑体", 20, 20));

		// textPane.setLineWrap(true);// 设置自动换行
		// textPane.setWrapStyleWord(true);// 激活断行不断字功能
		textPanei.setEnabled(true);
		textPanei.setVisible(true);
		HTMLEditorKit editKit = new HTMLEditorKit();
		// 实例化一个HTMLEditorkit工具包，用来编辑和解析用来显示在JJTextPane中的内容。
		// doci = (HTMLDocument) editKit.createDefaultDocument();
		// 使用HTMLEditorKit类的方法来创建一个文档类，HTMLEditorKit创建的类型默认为htmldocument。
		// 设置JJTextPane组件的编辑器工具包，是其支持html格式。
		// textPanei.setContentType("text/html");
		// 设置编辑器要处理的文档内容类型，有text/html,text/rtf.text/plain三种类型。
		textPanei.setEditorKit(editKit);
		// textPane.setTabSize(2);// 设置tab键大小
		docList.add(doci);
		textPanei.setDocument(docList.get(i));
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setFontSize(attr, 16);
		textPanei.setCharacterAttributes(attr, false);
		textPanei.setFont(new Font("仿宋", Font.PLAIN, 16));
		JScrollPane scrollPanei = new JScrollPane(textPanei);
		textPanei.setBorder(new LineBorder(Color.BLUE, 4, true));
		// scrollpane = new JScrollPane(textArea);
		// panel1=uii.getPanel(imgPath);

		textList.add(textPanei);
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
		tabPane.add(getTabTitle(), scrollPanei);
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

	/**
	 * 初始化面板2
	 */
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
		musicOn = UIUtils.createJRadioButton("play", "开");
		musicOff = UIUtils.createJRadioButton("stop", "关");
		taskOn = new JRadioButton("幻灯片开");
		taskOff = new JRadioButton("幻灯片关");
		backButton = UIUtils.createMusicJButton("pre", "上一首");
		fowardButton = UIUtils.createMusicJButton("next", "下一首");
		progress.setStringPainted(true);
		backButton.addActionListener(this);
		slider = UIUtils.createSlider(SwingConstants.HORIZONTAL);
		slider.setBounds(10, 108, 270, 15);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if (Math.abs(slider.getValue() - progress.getValue()) > 2) {
					ThreadBean.setSpliderChange(true);
				}

			}
		});
		fowardButton.addActionListener(this);
		loopControll = bProvider.getButton("列表循环");
		final ImageIcon[] iconsSingle = UIUtils.getMusicIcon("single", 3);
		final ImageIcon[] iconsRandom = UIUtils.getMusicIcon("random", 3);
		final ImageIcon[] iconsLoop = UIUtils.getMusicIcon("loop", 3);
		loopControll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				switch (e.getActionCommand()) {
				case "列表循环":
					ThreadBean.setRandomPlay(false);
					ThreadBean.setSinglePlay(true);
					// ImageIcon[] iconsSingle = UIUtils.getMusicIcon("single",
					// 3);
					// loopControll=bProvider.getButton("单曲循环");
					UIUtils.setButton(loopControll, iconsSingle, "单曲循环");
					loopControll.removeActionListener(this);
					loopControll.addActionListener(this);
					break;
				case "单曲循环":
					ThreadBean.setSinglePlay(false);
					ThreadBean.setRandomPlay(true);
					// loopControll=bProvider.getButton("随机播放");
					UIUtils.setButton(loopControll, iconsRandom, "随机播放");
					loopControll.removeActionListener(this);
					loopControll.addActionListener(this);
					break;
				case "随机播放":
					ThreadBean.setSinglePlay(false);
					ThreadBean.setRandomPlay(false);
					// loopControll=bProvider.getButton("列表循环");
					UIUtils.setButton(loopControll, iconsLoop, "列表循环");
					loopControll.removeActionListener(this);
					loopControll.addActionListener(this);
					break;
				default:
					break;
				}
			}
		});
		/*
		 * playBox.addItemListener(new ItemListener() {
		 * 
		 * @Override public void itemStateChanged(ItemEvent e) { // TODO
		 * Auto-generated method stub switch ((String)
		 * playBox.getSelectedItem()) { case "列表循环":
		 * ThreadBean.setSinglePlay(false); ThreadBean.setRandomPlay(false);
		 * break; case "单曲循环": ThreadBean.setRandomPlay(false);
		 * ThreadBean.setSinglePlay(true); break; case "随机播放":
		 * ThreadBean.setSinglePlay(false); ThreadBean.setRandomPlay(true);
		 * break; default: break; }
		 * 
		 * } });
		 */

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
		Box boxh = Box.createHorizontalBox();
		boxh.add(musicOn);
		boxh.add(Box.createHorizontalStrut(10));
		boxh.add(musicOff);
		boxh.add(Box.createHorizontalStrut(10));
		// boxh.add(playBox);
		boxh.add(loopControll);
		Box boxh2 = Box.createHorizontalBox();
		boxh2.add(backButton);
		boxh2.add(Box.createHorizontalStrut(5));
		boxh2.add(fowardButton);
		Box box = Box.createVerticalBox();

		box.add(boxh);
		box.add(Box.createVerticalStrut(10));
		box.add(boxh2);
		musicPanel.add(box);
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
		labelTime = new JJLabel();// 用于显示歌曲时间
		labelTime.setBackground(Color.WHITE);
		labelTime.setOpaque(true);
		labelTime.setBorder(new BevelBorder(BevelBorder.RAISED));
		labelTime.setAutoscrolls(true);
		// labelTime.setForeground(Color.BLUE);
		labelTime.setFont(new Font("serif", Font.BOLD, 16));
		labelTime.setAlignmentX(CENTER_ALIGNMENT);
		music.setLabelTime(labelTime);
		// 按钮
		Box panel1Box = Box.createVerticalBox();
		// progressBar.setIndeterminate(true);
		panel1Box.add(labelTime);
		panel1Box.add(Box.createVerticalStrut(5));
		panel1Box.add(progress);
		panel1Box.add(slider);
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(musicPanel);
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(taskPanel);
		panel1Box.add(Box.createVerticalStrut(15));
		// panel1Box.add(backPanel);

		listPanel = new JPanel();
		labelMusic = new JJLabel();
		labelMusic.setAlignmentX(CENTER_ALIGNMENT);
		setLabelMusic(labelMusic);
		TitledBorder titleBorder3 = new TitledBorder(new EtchedBorder(
				EtchedBorder.RAISED), "播放列表", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(
						51, 51, 51));
		listPanel.setBorder(titleBorder3);
		listPanel.add(new JScrollPane(musicList));
		panel1Box.add(getLabelMusic());
		panel1Box.add(Box.createVerticalStrut(15));
		panel1Box.add(listPanel);
		panel1Box.add(Box.createVerticalStrut(50));
		panel2.add(panel1Box);
		panel2.setVisible(true);

	}

	/**
	 * 主要动作处理
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i = tabPane.getSelectedIndex();
		NoteFrameEvent event = new NoteFrameEvent(this);

		event.actionEvent(e);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// labelMusic.setBackground(Color.WHITE);
		music.setLabel(labelMusic);
		music.setProgress(progress);
		music.setSlider(slider);
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

	public JJTextPane getJJTextPane() {
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

	/**
	 * 用于背景图片变化的定时器任务
	 */
	/*
	 * TimerTask task = new TimerTask() {
	 * 
	 * int i = 0; Random ran = new Random(imgPathStr.size());
	 * 
	 * public void run() { // * if (i > imgPathStr.size() - 1) { i = 0; }
	 * i=ran.nextInt()-1; // setAlpha(true);
	 * 
	 * panel1.repaint(); panel2.repaint(); try { Thread.sleep(1000); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * // setAlpha(false); imgPath =
	 * imgPathStr.get(ran.nextInt(imgPathStr.size() - 1));
	 * Logger.getLogger("com.xby.log").info("timer----->" + imgPath);
	 * setImg(imgPath); panel1.repaint(); panel2.repaint(); } };
	 */
	private JJLabel labelTime;
	private String tabTitle = "pane";
	private MySlider slider;
	private JButton loopControll;
	private ToolButtonProvider tbProvider;
	private boolean timerWaited = false;
	private JPanel listPanel;

	// private JRadioButton[] background;
	/**
	 * 打开关闭定时器相关方法
	 */
	void taskOpen() {
		// timer与task只能使用一次
		timer = new Timer();
		timer.schedule(new TimerTask() {

			int i = 0;
			Random ran = new Random(imgPathStr.size());

			public void run() {
				// * if (i > imgPathStr.size() - 1) { i = 0; }
				// i=ran.nextInt()-1;
				// setAlpha(true);
				/*
				 * panel1.repaint(); panel2.repaint(); try { Thread.sleep(1000);
				 * } catch (InterruptedException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); }
				 */
				// setAlpha(false);
				imgPath = imgPathStr.get(ran.nextInt(imgPathStr.size() - 1));
				Logger.getLogger("com.xby.log").info("timer----->" + imgPath);
				File file=new File(imgPath);
				while(!file.exists()){
					imgPathStr = strResource.getListIMG();
					imgPath = imgPathStr.get(ran.nextInt(imgPathStr.size() - 1));
					file=new File(imgPath);
				}
				setImg(imgPath);
				panel1.repaint();
				panel2.repaint();
			}
		}, 10000, 10000);
	}

	void taskCancel() {
		timer.cancel();

	}

	/**
	 * 返回正在编辑的标签索引
	 * 
	 * @return
	 */
	public int getSelectTabIndex() {
		return tabPane.getSelectedIndex();
	}

	/**
	 * 设置面板图片
	 * 
	 * @param path
	 */
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

	/*
	 * private boolean isAlpha = false;
	 * 
	 * public void setAlpha(boolean b) { isAlpha = b; }
	 * 
	 * public boolean isAlpha() { return isAlpha; }
	 */
	/**
	 * 带有背景图片的面板内部类
	 * 
	 * @author xby64
	 * 
	 */
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
			/*
			 * if (isAlpha()) g2d.setComposite(AlphaComposite.getInstance(
			 * AlphaComposite.SRC_OVER, 0.5F));
			 */
			g2d.drawImage(img, 0, 0, null);
			// g2d.dispose();// 释放资源
			g2d.rotate(Math.toRadians(-30));// 旋转绘图上下文对象
			Font font = new Font("楷体", Font.BOLD, 72);// 创建字体对象
			g2d.setFont(font);// 指定字体
			g2d.setColor(Color.GREEN);// 指定颜色
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);// 获得表示透明度的AlphaComposite对象
			g2d.setComposite(alpha);// 指定AlphaComposite对象
			g2d.drawString("小白杨", -30, 240);// 绘制文本,实现水印
			// 准备矩形，用来创建一个纹理填充
			Rectangle rectan = new Rectangle(0, 0, img.getWidth(null),
					img.getHeight(null));
			TexturePaint tu = new TexturePaint(bufImg, rectan);
			// 用创建的纹理填充来填充整个面板

			g2d = (Graphics2D) g;
			// 用于抗锯齿
			/*
			 * RenderingHints render=new RenderingHints(null);
			 * render.put(RenderingHints.KEY_ANTIALIASING,
			 * RenderingHints.VALUE_ANTIALIAS_ON);
			 * render.put(RenderingHints.KEY_TEXT_ANTIALIASING
			 * ,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			 * g2d.setRenderingHints(render);
			 */
			new ObjectRec(g2d).setObjectRec();

			g2d.setPaint(tu);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

			super.paint(g);
		}
	}

//	public static void main(String[] args) {
		// 以下两句必须放在main（）方法中才能生效，且要在创建窗体之前执行
		// JFrame.setDefaultLookAndFeelDecorated(true);// 使标题栏装饰生效
		// JDialog.setDefaultLookAndFeelDecorated(true);// 使对话框装饰生效
//		new NoteFrame();
//	}

	/**
	 * @return the labelMusic
	 */
	public JJLabel getLabelMusic() {
		return labelMusic;
	}

	/**
	 * @param labelMusic
	 *            the labelMusic to set
	 */
	public void setLabelMusic(JJLabel labelMusic) {
		this.labelMusic = labelMusic;
	}

	/**
	 * @return the textList
	 */
	public ArrayList<JJTextPane> getTextList() {
		return textList;
	}

	/**
	 * @param textList
	 *            the textList to set
	 */
	public void setTextList(ArrayList<JJTextPane> textList) {
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

	public void setTabTitle(String str) {
		tabTitle = str;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	/**
	 * @return the progress
	 */
	public JProgressBar getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}

}
