package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Policy extends JFrame implements ActionListener{
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	static Boolean start=true;
	private Boolean policyB=false;
	String imgPath = new String("/source/image/login.jpg");
	
	public Policy(){
		init();
/*		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = this.getWidth();
		int height = this.getHeight();
//		setBounds(size.width/2 - width / 2, size.height/2 -height/ 2, width,
//				height);
		setLocation(size.width/2 - width / 2, size.height/2 - height/ 2);
 */
		pack();
		this.setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	void init(){ 
		JPanel panel1=new JJPanel(imgPath);
		JTextArea textArea=new JTextArea();
		textArea.setEditable(false);
		ImageIcon img=new ImageIcon(Policy.class.getResource("/source/image/lazy.jpg"));
		JLabel label1=new JLabel(img);
	
		String str="1.你是一个小胖子!\n"+"2.你是一个傻瓜\n"+"3.你是杨宝宝\n"+"4.你爱小白杨\n"+"5.你会一直报告此软件BUG\n"+
				"6.这属于商业机密,你保证不会泄密\n"+"7.如若违反上述协议,自愿承担相应法律责任";
		textArea.append(str);
		textArea.setOpaque(false);
		textArea.setFont(new Font("不知道",Font.ITALIC,20));
		Box box=Box.createVerticalBox();
		box.add(label1); 
		box.add(Box.createVerticalStrut(30));
		box.add(textArea);
		box.add(Box.createVerticalStrut(30));
		JCheckBox check=new JCheckBox("我自愿同意以上协议,自愿承担相应法律责任");
//		check.setOpaque(false);
		check.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				if(ie.getStateChange()==ItemEvent.SELECTED){
					policyB=true;
				}
			}
		});
		box.add(check);
		box.add(Box.createVerticalStrut(20));
		Box box1=Box.createHorizontalBox();
		JButton confirm=new JButton("确定");
		JButton cancel=new JButton("取消");
		box1.add(Box.createHorizontalStrut(50));
		box1.add(confirm);
		box1.add(Box.createHorizontalStrut(50));
		box1.add(cancel);
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		box.add(box1);
		panel1.add(box);
		add(panel1);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "确定":
			if(policyB){
				if(start){
					new Login().setTitle("小白楊日記本-含羞草專版");;
					this.dispose();
				}else{
				new Register().setTitle("小白杨日记本-含羞草专版");
				this.dispose();
				}
			}else{
				JOptionPane.showMessageDialog(this, "请先勾选同意协议");
			}
			break;
		case "取消":
			System.exit(0);
			break;
		default:
			break;
		}
	}
	
}
