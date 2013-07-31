package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.operation.HTMLString;
import source.Strings;


public class Policy extends JFrame implements ActionListener{
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	static Boolean start=true;
	private Boolean policyB=false;
	Strings strResource =new Strings();
	String imgPath =strResource.getPOLICY_IMG();
	
	public Policy(){
//		setUndecorated(true);
//		setShape(new Ellipse2D.Double(getLocation().x,getLocation().y,getWidth(),getHeight()));
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
		SetLookAndFeel.setLookAndFeel(this);
		setVisible(true);
	}
	void init(){ 
		JPanel panel1=new JJPanel(imgPath); 
		JTextArea textArea=new JTextArea();
		textArea.setEditable(false);
		ImageIcon img=new ImageIcon(Policy.class.getResource("/source/image/lazy.jpg"));
		JLabel label1=new JLabel(img);
		label1.setOpaque(false);
		String str="0.特别提醒,为了充分了解软件的功能及使用\n" +
				"1.登陆后查看帮助说明\n"+"" +
						"2.否则您将无法充分使用该软件\n"
				+"3.你会一直报告此软件BUG\n"+"4.本软件遵守GPL许可协议\n";
		textArea.append(str);
		textArea.setOpaque(false);
		textArea.setFont(new Font("微软雅黑",Font.ITALIC,20));
		Box box=Box.createVerticalBox();
		box.add(label1); 
		box.add(Box.createVerticalStrut(30));
		box.add(textArea);
		box.add(Box.createVerticalStrut(30));
		JCheckBox check=new JCheckBox(new HTMLString().getLabelString("我自愿同意以上协议", "blue"));
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
					new Login().setTitle("小白楊日記本");;
					this.dispose();
				}else{
				new Register().setTitle("小白杨日记本");
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
