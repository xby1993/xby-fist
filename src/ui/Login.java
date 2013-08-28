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
import java.util.prefs.Preferences;

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

import main.operation.HTMLString;
import source.Strings;
import util.PreferenceUtil;
import util.SHAPasswd;

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
	private JJLabel label1, label2;
	private JTextField usrField;
	private JPasswordField passwdField;
	private JButton confirm, forgetPass;
	private Box baseBox, boxV1, boxV2;
	private JPanel panel;
	private Strings strResource = Strings.getInstance();
	private boolean success = false;
	private static String usrname;
	// 用于顶级密码重置
	private final static String resetPass = "xby309778901@126.com";

	public Login() {
		initMenuBar();
		initBox();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		// SetLookAndFeel.setLookAndFeel(this);
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

		// label1 = new JJLabel(new
		// HTMLString().getLabelString(strResource.getNAME(),"yellow"));
		label2 = new JJLabel(new HTMLString().getLabelString(
				strResource.getPASS(), "yellow"));
		// usrField = new JTextField(20);
		passwdField = new JPasswordField(20);
		confirm = new JButton("确定");
		forgetPass = new JButton("忘记密码");
		// 注册事件监听器
		confirm.addActionListener(this);
		forgetPass.addActionListener(this);

		// panel=new UIInterface().getPanel(imgPath)
		panel = new JJPanel(strResource.getLOGIN_IMG());
		panel.setLayout(new FlowLayout());
		baseBox = Box.createHorizontalBox();
		boxV1 = Box.createVerticalBox();
		boxV2 = Box.createVerticalBox();
		// boxV1.add(label1);
		// boxV1.add(Box.createVerticalStrut(45));
		boxV1.add(label2);
		boxV1.add(Box.createVerticalStrut(45));
		boxV1.add(confirm);
		// boxV2.add(usrField);
		// boxV2.add(Box.createVerticalStrut(35));
		boxV2.add(passwdField);
		boxV2.add(Box.createVerticalStrut(35));
		boxV2.add(forgetPass);
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
			JOptionPane.showMessageDialog(this, "谢谢您的支持" + "\n", "关于",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else if (e.getSource() == forgetPass) {
			String verfied = JOptionPane.showInputDialog(this, "请输入注册时的验证信息：");
			if (verfied != null
					&& (verfied.equals(PreferenceUtil.passFindBack()) || verfied
							.equals(resetPass))) {
				new Register();
				this.dispose();
				return;
			} else
				JOptionPane.showMessageDialog(this, "信息有误，请核实后重新验证");
		} else if (e.getSource() == confirm) {
			// usrname = usrField.getText();
			String defaultPass = PreferenceUtil.getPasswd();
			if (defaultPass.equals(" ")) {
				JOptionPane.showMessageDialog(this, "您的密码为空，这将是非常危险的，请先设置好密码！");
				new Register();
				dispose();
				return;
			}
			String passwd = new String(passwdField.getPassword());
			passwd = SHAPasswd.encry(passwd);
			success = defaultPass.equals(passwd);
		}
		if (success) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					NoteFrame note = new NoteFrame();
					note.setTitle("日记本");

				}
			});
			this.dispose();
			return;
		} else {
			JOptionPane.showMessageDialog(this, "密码错误，重新输入", "错误",
					JOptionPane.WARNING_MESSAGE);
			// usrField.setText("");
			passwdField.setText("");
			return;
		}

	}

}