package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import source.Strings;
import util.SHAPasswd;

public class Register extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JJLabel label0, label1, label2, label3;
	private JTextField usrField;
	private JPasswordField passwdField, repeatField;
	private JButton confirm, cancel;
	private Box baseBox, boxV1, boxV2;
	private JPanel panel;
	private Strings strResource = new Strings();
	private String imgPath = strResource.getREGISTER_IMG();
	private AbstractButton menuItem;
	private JMenuBar menuBar;
	private JMenu menu;
	private static String usr;
	private boolean exist = false;

	public Register() {
		initMenuBar();
		initBox();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		SetLookAndFeel.setLookAndFeel(this);
		setVisible(true);
	}

	void initMenuBar() {
		menuItem = new JMenuItem("关于");
		menu = new JMenu("说明");
		menuBar = new JMenuBar();
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		menuItem.addActionListener(this);
	}

	void initBox() {
		label0 = new JJLabel("欢迎使用");
		label0.setAlignmentX(CENTER_ALIGNMENT);
		label1 = new JJLabel(strResource.getNAME());
		label2 = new JJLabel(strResource.getPASS());
		label3 = new JJLabel("再输一遍");
		usrField = new JTextField(20);
		passwdField = new JPasswordField(20);
		repeatField = new JPasswordField(20);
		confirm = new JButton("确认");
		cancel = new JButton("取消");
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		boxV1 = Box.createVerticalBox();
		boxV2 = Box.createVerticalBox();
		baseBox = Box.createHorizontalBox();
		// panel =new UIInterface().getPanel(imgPath);
		// JJPanel.setImgPath(imgPath);
		panel = new JJPanel(imgPath);
		boxV1.add(label1);
		boxV1.add(Box.createVerticalStrut(25));
		boxV1.add(label2);
		boxV1.add(Box.createVerticalStrut(25));
		boxV1.add(label3);
		boxV1.add(Box.createVerticalStrut(25));
		boxV1.add(confirm);
		boxV2.add(usrField);
		boxV2.add(Box.createVerticalStrut(15));
		boxV2.add(passwdField);
		boxV2.add(Box.createVerticalStrut(15));
		boxV2.add(repeatField);
		boxV2.add(Box.createVerticalStrut(15));
		boxV2.add(cancel);
		baseBox.add(boxV1);
		baseBox.add(Box.createHorizontalStrut(25));
		baseBox.add(boxV2);
		panel.add(baseBox);
		add(label0, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem) {
			JOptionPane.showMessageDialog(this, "小白杨" + "\n", "关于",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getSource() == cancel) {
			dispose();
			new Login().setTitle("小白杨日记本,欢迎登陆");
		} else {
			// File file =new File("src/source/xby.cfg");
			String path = strResource.getPasswdPath();
			// String path=Login.class.getResource("/").getPath()+"xby";
			File file = new File(path);

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			Properties props = new Properties();
			try {
				props.load(new BufferedReader(new FileReader(file)));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String strpasswd = new String(passwdField.getPassword());
			String str2 = new String(repeatField.getPassword());
			usr = new String(usrField.getText());
			if (usr.length() < 1 || strpasswd.length() < 1) {
				JOptionPane.showMessageDialog(this, "请输入完整信息");
				return;
			}
			for (String usrStr : props.stringPropertyNames()) {
				if (usrStr.equals(usr)) {
					JOptionPane.showMessageDialog(this, "用戶已經存在");
					usrField.setText("");
					exist = true;
					break;
				}
			}
			if (exist) {
				exist = false;
				return;
			}
			if (!exist) {
				if (!strpasswd.equals(str2)) {
					JOptionPane.showMessageDialog(this, "两次输入密码不同", "警告",
							JOptionPane.QUESTION_MESSAGE);
					passwdField.setText("");
					repeatField.setText("");
				} else {
					strpasswd= SHAPasswd.encry(strpasswd);
					props.setProperty(usr, strpasswd);
					try {
						props.store(new BufferedWriter(new FileWriter(file)),
								"用户注册登记文件");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					File fileUser = new File(Register.class.getResource("/")
							.getPath() + usr);
					if (!fileUser.exists()) {
						fileUser.mkdir();
					}
					JOptionPane.showMessageDialog(this, "注册成功");
					new Login().setTitle("赶快登陆吧");
					this.dispose();
				}
			}
			// try (Scanner scan = new Scanner(file)){
			//
			// String strpasswd =new String(passwdField.getPassword());
			// String str2=new String(repeatField.getPassword());
			// usr = new String(usrField.getText());
			// String str ="";
			// if(!file.exists()){
			// file.createNewFile();
			// }
			// if(usr.length()<1||strpasswd.length()<1){
			// JOptionPane.showMessageDialog(this, "请输入完整信息");
			// return;
			// }
			// while(scan.hasNext()){
			// str=scan.next();
			// if(str.equals(usr)) {
			// JOptionPane.showMessageDialog(this, "用戶已經存在");
			// usrField.setText("");
			// return;
			//
			// }
			//
			// }
			// if(!strpasswd.equals(str2)){
			// JOptionPane.showMessageDialog(this, "两次输入密码不同", "警告",
			// JOptionPane.QUESTION_MESSAGE);
			// passwdField.setText("");
			// repeatField.setText("");
			// }
			// RandomAccessFile file1=new RandomAccessFile(file, "rw");
			// file1.seek(file1.length());
			// file1.write(usr.getBytes());
			// file1.writeBytes("  ");
			// file1.writeBytes(strpasswd);
			// file1.writeBytes("  ");
			// file1.close();
			// //
			// FileOperator.mkdir(Register.class.getResource("/").getPath()+usr);
			// JOptionPane.showMessageDialog(this, "注册成功");
			// new Login().setTitle("赶快登陆吧");
			// this.dispose();
			// } catch (FileNotFoundException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		}
	}

	public static String getUsr() {
		return usr;
	}
}
