package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.swing.AbstractButton;
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

import main.FileOperator;

import source.Strings;

public class Register extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label0,label1,label2,label3;
	JTextField usrField;
	JPasswordField passwdField,repeatField;
	JButton confirm, cancel;
	Box baseBox, boxV1, boxV2;
	JPanel panel;
	Strings strResource = new Strings();
	String imgPath=new String("src/source/image/2.jpg");
	private AbstractButton menuItem;
	private JMenuBar menuBar;
	private JMenu menu;
	private static String usr;
	public Register() {
		initMenuBar();
		initBox();
//		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setBounds(500, 300, 500, 300);
//		setLocationRelativeTo(null);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		int width = screensize.width/2;
        int height = screensize.height/2;
        setBounds((screensize.width-width)/2, (screensize.height-height)/2, width, height);
		pack();
		
		setVisible(true);
	}
	void initMenuBar(){
		menuItem = new JMenuItem("关于");
		menu = new JMenu("说明");
		menuBar = new JMenuBar();
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		menuItem.addActionListener(this);
	}
	void initBox(){
		label0 = new JLabel("含羞草童鞋"+"\n"+"欢迎你使用小白杨日记本含羞草专版");
		label1 = new JLabel(strResource.getNAME());
		label2 = new JLabel(strResource.getPASS());
		label3=new JLabel("再输一遍");
		usrField=new JTextField(20);
		passwdField=new JPasswordField(20);
		repeatField=new JPasswordField(20);
		confirm =new JButton("确认");
		cancel=new JButton("取消");
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		boxV1 = Box.createVerticalBox();
		boxV2 =Box.createVerticalBox();
		baseBox=Box.createHorizontalBox();
//		panel =new UIInterface().getPanel(imgPath);
//		JJPanel.setImgPath(imgPath);
		panel=new JJPanel(imgPath);
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
		add(label0,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem) {
			JOptionPane.showMessageDialog(this, "小白杨第一个工程-含羞草专版日记本" + "\n"
					+ "献给我最爱的人", "关于", JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getSource() == cancel) {
			dispose();
			new Login().setTitle("含羞草专属记事本");
		}else {
			File file =new File("src/source/xby.cfg");
			try (Scanner scan = new Scanner(file)){
				
				String strpasswd =new String(passwdField.getPassword());
				String str2=new String(repeatField.getPassword());
				usr = new String(usrField.getText());
				String str ="";
				if(!file.exists()){
					file.createNewFile();
				}
				if(usr.length()<1||strpasswd.length()<1){
					JOptionPane.showMessageDialog(this, "请输入完整信息");
					return;
				}
				while(scan.hasNext()){
					str=scan.next();
					if(str.equals(usr)) {
						JOptionPane.showMessageDialog(this, "用戶已經存在");
						usrField.setText("");
						return;
						
					}
					  
				}
				if(!strpasswd.equals(str2)){
					JOptionPane.showMessageDialog(this, "两次输入密码不同", "警告", JOptionPane.QUESTION_MESSAGE);
					passwdField.setText("");
					repeatField.setText("");
				}
				RandomAccessFile file1=new RandomAccessFile(file, "rw");
				file1.seek(file1.length());
				file1.writeBytes(usr);
				file1.writeBytes("  ");
				file1.writeBytes(strpasswd);
				file1.writeBytes("  ");
				file1.close();
				FileOperator.mkdir("diary/"+usr);
				JOptionPane.showMessageDialog(this, "注册成功");
				new Login().setTitle("赶快登陆吧");
				this.dispose();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static String getUsr() {
		return usr; 
	}
}
