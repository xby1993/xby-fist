package ui;

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
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import main.Edit;
import main.FileOperator;
import main.FindAndReplace;
import main.Help;
import main.Music;
import main.MyFont;

public class NoteFrame extends JFrame implements UndoableEditListener,
		ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File tempFile;

	// 菜单条
	private JMenuBar menuBar;
	private String[] jmenuStr = { "文件", "编辑", "搜索", "格式", "帮助" };
	private String[][] jitemStr = {
			{ "新建", "打开", "保存", "另存为", "", "页面设置", "打印", "", "退出" },
			{ "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" },
			{ "查找", "查找下一个", "替换", "转到" }, { "字体" }, { "说明", "关于" } };
	// 设置快捷键绑定
	private char[][] shortcut = { { 'N', 'O', 'S', ' ', ' ', ' ', 'P', ' ', 'E' },
			{ 'U', 'Y', 'X', 'C', 'V', 'D', 'A' }, { 'F', 'G', 'R', 'L' },
			{ ' ' }, { ' ', ' ' } };
	// 右键菜单
	private JPopupMenu pmenu;
	private String[] jpopItemStr = { "撤销", "恢复", "剪切", "复制", "粘贴", "删除", "全选" };

	// 编辑文字区
	private static JJTextArea textArea;
	private Document doc;
	@SuppressWarnings("unused")
	private JScrollPane scrollpane;

	// 标示状态的工具条
	private JToolBar status;
	private JLabel currentstatus;
	String statusinfo = "";
	Timer timer=new Timer();
	private JTextField date;// 日期输入框
	 String[] imgPathStr = { "src/source/image/1.jpg", "src/source/image/2.jpg",
			"src/source/image/3.jpg", "src/source/image/4.jpg","src/source/image/5.jpg","src/source/image/6.jpg" };
	 String imgPath = imgPathStr[4];
	JPanel panel1, panel2;
	private JRadioButton musicOn, musicOff,taskOn,taskOff;// 背景音乐开关
	private JComboBox<String> day, wether;// 时间,天气选择下拉列表.

	private File tempfile=null;// 临时存储文件
	// 文本是否改变标志
	private Boolean changed = false;
	private static UndoManager undoManager = new UndoManager();// 新建可撤销、恢复列表类
	private JMenuItem[][] jitem;

	// 撤销和恢复

	public NoteFrame() {
		initBar();
		initPanel3();
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = size.width / 2;
		int height = size.height / 2;
		setBounds((size.width - width) / 2, (size.height - height) / 2, width,
				height);

		pack();
		//先关闭默认操作
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				new FileOperator(NoteFrame.this).exit();
			}
		});

//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	void initBar() {
		// 初始化文件菜单
		menuBar = new JMenuBar();
		JMenu[] jmenu = new JMenu[jmenuStr.length];
		jitem = new JMenuItem[jitemStr.length][];
		for (int i = 0; i < jmenu.length; i++) {
			jmenu[i] = new JMenu(jmenuStr[i]);
			jitem[i] = new JMenuItem[jitemStr[i].length];
			for (int j = 0; j < jitem[i].length; j++) {
				if ("".equals(jitemStr[i][j])) {
					jmenu[i].addSeparator();
				} else {
					jitem[i][j] = new JMenuItem(jitemStr[i][j]);
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
		setJMenuBar(menuBar);
		initTextArea();
	}

	void initTextArea() {
		textArea = new JJTextArea(imgPath);
		 textArea.setFont(new Font("黑体", 20, 20));
		textArea.setLineWrap(true);// 设置自动换行  
		textArea.setWrapStyleWord(true);// 激活断行不断字功能
		textArea.setEnabled(true);
		textArea.setVisible(true);
		textArea.setTabSize(2);//设置tab键大小
		scrollpane = new JScrollPane(textArea);
		// scrollpane = new JScrollPane(textArea);
		// panel1=uii.getPanel(imgPath);
		textArea.getDocument().addUndoableEditListener(this);// 添加可撤销、恢复编辑监听
		initPopMenu();
		textArea.setComponentPopupMenu(pmenu);
	
		doc = textArea.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent de) {
				changed = true;
				if (textArea.getText().length() == 0) {
					jitem[2][0].setEnabled(false);
					jitem[2][1].setEnabled(false);
					jitem[2][2].setEnabled(false);
					jitem[2][3].setEnabled(false);
				} else {
					jitem[2][0].setEnabled(true);
					jitem[2][1].setEnabled(true);
					jitem[2][2].setEnabled(true);
					jitem[2][3].setEnabled(true);
				}
			}

			public void removeUpdate(DocumentEvent de) {
				changed = true;
				if (textArea.getText().length() == 0) {
					jitem[2][0].setEnabled(false);
					jitem[2][1].setEnabled(false);
					jitem[2][2].setEnabled(false);
					jitem[2][3].setEnabled(false);
				} else {
					jitem[2][0].setEnabled(true);
					jitem[2][1].setEnabled(true);
					jitem[2][2].setEnabled(true);
					jitem[2][3].setEnabled(true);
				}
			}

			public void changedUpdate(DocumentEvent de) {
				changed = true;
				if (textArea.getText().length() == 0) {
					jitem[2][0].setEnabled(false);
					jitem[2][1].setEnabled(false);
					jitem[2][2].setEnabled(false);
					jitem[2][3].setEnabled(false);
				} else {
					jitem[2][0].setEnabled(true);
					jitem[2][1].setEnabled(true);
					jitem[2][2].setEnabled(true);
					jitem[2][3].setEnabled(true);
				}
			}
		});
		textArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent ce) {
				try {
					int offset = ce.getDot();// 获取插入符的位置
					int row = textArea.getLineOfOffset(offset);// 将组件文本中的偏移量转换为行号。
					int colum = offset - textArea.getLineStartOffset(row);// 确定给定行起始处的偏移量
					Calendar calendar = Calendar.getInstance();
					statusinfo = "当前状态：" + calendar.getTime().toString()
							+ "，  Row: " + String.valueOf(row + 1) + "   Col: "
							+ String.valueOf(colum + 1);
					currentstatus.setText(statusinfo);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});
		status = new JToolBar();// 下面的状态栏
		status.setFloatable(false);// 不可浮动，默认是可以浮动的
		currentstatus = new JLabel();
		status.add(currentstatus);
		status.setVisible(true);
		add(status,BorderLayout.SOUTH);
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
		JJPanel.setImgPath(imgPath);
		panel1 = new JJPanel(imgPath);
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
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("日期"));
		box.add(date);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("星期"));
		box.add(day);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("天气"));
		box.add(wether);
		panel1.add(box, BorderLayout.NORTH);
		panel1.add(new JScrollPane(textArea), BorderLayout.CENTER);
		panel1.setVisible(true);

	}

	void initPanel2() {
		// 设置背景的控件
		// panel2=uii.getPanel(imgPath);
//		JJPanel.setImgPath(imgPath);
		panel2 = new JJPanel(imgPath); 
		ButtonGroup setBackground = new ButtonGroup();
		JRadioButton[] background = new JRadioButton[6];
		JPanel backPanel = new JPanel();
		String backgroundName[] = { "背景图片1", "背景图片2", "背景图片3", "背景图片4","背景图片5","背景图片6" };
		Box backgroundBox = Box.createVerticalBox();
		for (int i = 0; i < background.length; i++) {
			background[i] = new JRadioButton(backgroundName[i]);
			background[i].setOpaque(false);// 设置为非透明或者非纯白，很有用。
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
		JPanel taskPanel=new JPanel();
		ButtonGroup musicGroup = new ButtonGroup();
		ButtonGroup taskGroup = new ButtonGroup();
		musicOn = new JRadioButton("开");
		musicOff = new JRadioButton("关");
		taskOn=new JRadioButton("幻灯片开");
		taskOff=new JRadioButton("幻灯片关");
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
		music.add(musicOn);
		music.add(musicOff);
		taskPanel.add(taskOn);
		taskPanel.add(taskOff);
		taskPanel.setOpaque(false);
		music.setOpaque(false);
		music.setBorder(BorderFactory.createTitledBorder(null, "音乐",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog",
						Font.BOLD, 12), new Color(51, 51, 51)));
		taskPanel.setBorder(BorderFactory.createTitledBorder(null, "幻灯片",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog",
						Font.BOLD, 12), new Color(51, 51, 51)));
		// 按钮
		Box panel1Box = Box.createVerticalBox();
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(music);
		panel1Box.add(Box.createVerticalStrut(20));
		panel1Box.add(taskPanel);
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
			new MyFont(new Font("黑体",20,20),this).showDialog(this);
			break;
		case "查找":
		case "查找下一个":
		case "替换":
		case "转到":
			new FindAndReplace(this).showFindAndReplace(this, "查找");
			break;
		case "撤销":
			Edit.cancel();
			break;
		case "恢复":
			Edit.recover();
			break;
		case "剪切":
			Edit.Cut();
			break;
		case "复制":
			new Edit(this).Copy();
			break;
		case "粘贴":
			new Edit(this).Paste();
			break;
		case "删除":
			new Edit(this).Delete();
			break;
			
		case "全选":
			new Edit(this).SelectAll();
			break;
		case "新建":
			new FileOperator(this).createFile();
			break;
		case "打开":
			new FileOperator(this).openFile();
			break;
		case "保存":
			new FileOperator(this).saveFile();
			break;
		case "另存为":
			new FileOperator(this).saveasFile();
			break;
		case "页面设置":
			new FileOperator(this).pageSetup();
			break;
		case "打印":
			new FileOperator(this).print();
			break;
		case "退出":
			new FileOperator(this).exit();
			break;
		default:
			break;
		}
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// TODO Auto-generated method stub
		undoManager.addEdit(e.getEdit());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Music music = new Music();
		JRadioButton tempButton = (JRadioButton) e.getSource();
		if (tempButton.isSelected()) {
			String text = tempButton.getText();
			if (text.equals("开")) {
				music.musicOpen();
			} else if (text.equals("关")) {
				music.musicShut();
			}else if (text.equals("幻灯片开")) {
				taskOpen();
			}else if (text.equals("幻灯片关")) {
				taskCancel();
			}else if (text.equals("背景图片1")) {
				imgPath = imgPathStr[0];
//				JJTextArea.setImgPath(imgPath);
				JJPanel.setImgPath(imgPath);
//				textArea.repaint();
				panel1.repaint();
				panel2.repaint();
			} else if (text.equals("背景图片2")) {
				imgPath = imgPathStr[1];
				JJTextArea.setImgPath(imgPath);
				JJPanel.setImgPath(imgPath);
				textArea.repaint();
				panel1.repaint();
				panel2.repaint();
			} else if (text.equals("背景图片3")) {
				imgPath = imgPathStr[2];
				JJPanel.setImgPath(imgPath);
				JJTextArea.setImgPath(imgPath);
				textArea.repaint();
				panel1.repaint();
				panel2.repaint();
			} else if (text.equals("背景图片4")) {
				imgPath = imgPathStr[3];
				JJPanel.setImgPath(imgPath);
				JJTextArea.setImgPath(imgPath);
				textArea.repaint();
				panel2.repaint();
				panel1.repaint();
			}else if (text.equals("背景图片5")) {
				imgPath = imgPathStr[4];
				JJPanel.setImgPath(imgPath);
				JJTextArea.setImgPath(imgPath);
				textArea.repaint();
				panel2.repaint();
				panel1.repaint();
			}else if (text.equals("背景图片6")) {
				imgPath = imgPathStr[5];
				JJPanel.setImgPath(imgPath);
				JJTextArea.setImgPath(imgPath);
				textArea.repaint();
				panel2.repaint();
				panel1.repaint();
			}
		}
	}

	public static UndoManager getUndoManager() {
		return undoManager;
	}

	public static JJTextArea getJTextArea() {
		return textArea;
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
		return changed;
	}

	/**
	 * @param changed
	 *            the changed to set
	 */
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}

	public void setFile() {
		tempfile = new File("diary/" + Register.getUsr());
		try {
			tempfile.createNewFile();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "新建文本文档失败！");
		}

		this.setTitle(tempfile.getName());

	}

	void backgroundChange(String text) {

	}

	public String getDate() {
		return date.getText() + "   " + day.getSelectedItem() + "   "
				+ wether.getSelectedItem();
	}
	TimerTask task=new TimerTask(){
		   int i=0;
		   public void run(){
			if(i>=6){
				i=0;
			}
			imgPath=imgPathStr[i++];
			JJPanel.setImgPath(imgPath);
			panel1.repaint();
			panel2.repaint();
		 }
	   };
   void taskOpen(){
	   
	   timer.schedule(task, 10000,10000);
   }
   
   void taskCancel() {
	   timer.cancel();
   }
   
   
}

