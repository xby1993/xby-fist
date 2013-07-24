package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JJDialog extends JFrame{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/*private JJPanel panel=new JJPanel("");*/
//	private JPanel panel=new JPanel();
	private String msg;
	private JButton button=new JButton("确定");
	private JJTextPane text=new JJTextPane();
	public JJDialog(String msg){
//		super(owner,msg);
		this.msg=msg;
		initPanel();
		pack();
		setLocationRelativeTo(null);
		SetLookAndFeel.setLookAndFeel(this);
		SetLookAndFeel.setDecorated();
	}
		public void initPanel(){
		text.setEditable(false);
		text.setText(msg);
		text.setFont(new Font("",Font.BOLD,16));
		text.setForeground(Color.BLACK);
		add(text,BorderLayout.CENTER);
		button.setAlignmentX(CENTER_ALIGNMENT);
		add(button,BorderLayout.SOUTH);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				setVisible(false);
				close();
			}
			
		});
//		panel.add(text)
//		setVisible(true);
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		new ObjectRec(g2d).setObjectRec();
		super.paint(g2d);
	}
	public void look(){
		setVisible(true);
	}
	public void close(){
		this.dispose();
	}
}
