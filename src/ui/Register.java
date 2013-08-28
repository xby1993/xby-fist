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
import java.util.prefs.Preferences;

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
import util.PreferenceUtil;
import util.SHAPasswd;

public class Register extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JJLabel label0, label1, label2, label3;
	private JTextField infoField;
	private JPasswordField passwdField, repeatField;
	private JButton confirm, cancel;
	private Box baseBox, boxV1, boxV2;
	private JPanel panel;
	private Strings strResource = Strings.getInstance();
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
		// SetLookAndFeel.setLookAndFeel(this);
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
		 label1 = new JJLabel("验证信息（用于密码找回）：");
		label2 = new JJLabel("密码");
		label3 = new JJLabel("再输一遍");
		 infoField = new JTextField(20);
		passwdField = new JPasswordField(20);
		repeatField = new JPasswordField(20);
		confirm = new JButton("确认");
//		cancel = new JButton("取消");
		confirm.addActionListener(this);
//		cancel.addActionListener(this);
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
		 boxV2.add(infoField);
		 boxV2.add(Box.createVerticalStrut(15));
		boxV2.add(passwdField);
		boxV2.add(Box.createVerticalStrut(15));
		boxV2.add(repeatField);
//		boxV2.add(Box.createVerticalStrut(15));
//		boxV2.add(cancel);
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

		} else {
			String strpasswd = new String(passwdField.getPassword());
			String str2 = new String(repeatField.getPassword());
			// usr = new String(usrField.getText());
			if (strpasswd.length() < 6) {
				JOptionPane.showMessageDialog(this, "密码长度不能少于六位");
				return;
			}else if(infoField.getText().equals("")||infoField.getText()==null){
				JOptionPane.showMessageDialog(this, "密码找回验证信息不能为空，这将是您的密码找回唯一凭证");
			}
			
			if (!strpasswd.equals(str2)) {
				JOptionPane.showMessageDialog(this, "两次输入密码不同", "警告",
						JOptionPane.QUESTION_MESSAGE);
				passwdField.setText("");
				repeatField.setText("");
			} else {
				strpasswd = SHAPasswd.encry(strpasswd);
//				props.setProperty(usr, strpasswd);
				PreferenceUtil.setPasswd(strpasswd);
				PreferenceUtil.setFirstUse(false);
				PreferenceUtil.setPassFindBack(infoField.getText());
				JOptionPane.showMessageDialog(this, "密码设置成功，请牢记密码和密码找回验证信息！");
				new Login().setTitle("验证");
				this.dispose();
			}

		}
	}

}
