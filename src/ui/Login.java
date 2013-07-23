/**
 * the dairy login frame
 * author xby
 * time 2013.6.9
 */
package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.HTMLString;

import source.Strings;

/**
 * @author xby64
 * 
 */
public class Login extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JLabel label0, label1, label2;
	private JTextField usrField;
	private JPasswordField passwdField;
	private JButton logIn, register;
	private Box baseBox, boxV1, boxV2;
	private JPanel panel;
	private Strings strResource = new Strings();
	private boolean success=false;
	private static String usrname;

	public Login() {
		initMenuBar();
		initBox();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		SetLookAndFeel.setLookAndFeel(this);
		setVisible(true);
	}

	/**
	 * @param void
	 * @return void
	 */
	private void initMenuBar() {
		menuItem = new JMenuItem("关于");
		menu = new JMenu("说明");
		menuBar = new JMenuBar();
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		menuItem.addActionListener(this);
	}

	/**
	 * @param void
	 * @return void
	 */
	private void initBox() {
		label0 = new JLabel("含羞草童鞋,欢迎你使用小白杨日记本含羞草专版");
		label0.setOpaque(false);
		label1 = new JLabel(new HTMLString().getLabelString(strResource.getNAME(),"yellow"));
		label2 = new JLabel(new HTMLString().getLabelString(strResource.getPASS(),"yellow"));
		usrField = new JTextField(20);
		passwdField = new JPasswordField(20);
		logIn = new JButton(strResource.getLOGIN());
		register = new JButton(strResource.getREGISTER());
		// 注册事件监听器
		logIn.addActionListener(this);
		register.addActionListener(this);

		// panel=new UIInterface().getPanel(imgPath)
		panel = new JJPanel(strResource.getLOGIN_IMG());
		panel.setLayout(new FlowLayout());
		baseBox = Box.createHorizontalBox();
		boxV1 = Box.createVerticalBox();
		boxV2 = Box.createVerticalBox();
		boxV1.add(label1);
		boxV1.add(Box.createVerticalStrut(45));
		boxV1.add(label2);
		boxV1.add(Box.createVerticalStrut(45));
		boxV1.add(logIn);
		boxV2.add(usrField);
		boxV2.add(Box.createVerticalStrut(35));
		boxV2.add(passwdField);
		boxV2.add(Box.createVerticalStrut(35));
		boxV2.add(register);
		add(label0, BorderLayout.NORTH);
		baseBox.add(boxV1);
		baseBox.add(Box.createHorizontalStrut(20));
		baseBox.add(boxV2);
		panel.add(baseBox);
		add(panel, BorderLayout.CENTER);

	}

	/*
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem) {
			JOptionPane.showMessageDialog(this, "小白杨第一个工程-含羞草专版日记本" + "\n"
					+ "献给我最爱的人", "关于", JOptionPane.INFORMATION_MESSAGE);
			return;

		} else if (e.getSource() == register) {
			Policy.start = false;
			new Policy();
			Login.this.setVisible(false);
			return;
		} else if (e.getSource() == logIn) {
			usrname = usrField.getText();
			String passwd = new String(passwdField.getPassword());
			String path=strResource.getPasswdPath();
			File file=new File(path);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				Properties props=new Properties();
				try {
					props.load(new BufferedReader(new FileReader(file)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(String usr:props.stringPropertyNames()){
					if(usr.equals(usrname)){
						success=props.getProperty(usr).equals(passwd);
						if(success){
							break;
						}
					}
				}
				if(success){
					EventQueue.invokeLater(new Runnable(){
						public void run(){
							NoteFrame note = new NoteFrame();
							note.setTitle("含羞草专属日记本");
							
						}
					});
					this.dispose();
					return;
				}else{
					JOptionPane.showMessageDialog(this, "不存在此用户或密码错误，请先注册或重新输入", "错误",
							JOptionPane.WARNING_MESSAGE);
					usrField.setText("");
					passwdField.setText("");
					return;
				}
				// 读取密码用户文件
//				String str;
//				try (Scanner scan = new Scanner(file)) {
//					while (scan.hasNext()) {
//						str = scan.next();
//						if (str.equals(usrname)) {
//							if (scan.hasNext()) {
//								str = scan.next();
//								if (str.equals(passwd)) {
//									usrStr = usrField.getText();
//									NoteFrame note = new NoteFrame();
//									note.setTitle("含羞草专属日记本");
//
//									this.dispose();
//									return;
//								}
//							}
//						}
//					}
//				} catch (Exception ioe) {
//					ioe.printStackTrace();
//				}
//			
		}
//		JOptionPane.showMessageDialog(this, "不存在此用户或密码错误，请先注册或重新输入", "错误",
//				JOptionPane.WARNING_MESSAGE);
//		usrField.setText("");
//		passwdField.setText("");
//		return;
	}

	public static String getUser() {
		return usrname;
	}
	
}