package main.operation;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ui.JJButton;
import ui.JJList;
import ui.JJPanel;
import ui.SetLookAndFeel;

public class FontFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane text;
	private JPanel panel = new JJPanel();
	private JList<String> list = new JJList<String>();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JTextField filed = new JTextField("小白杨日记本");
	private JScrollPane scroll;
	private JButton b1, b2, b3;

	public FontFrame(JTextPane text) {
		this.text = text;
		init();
		pack();
		setLocationRelativeTo(null);
//		SetLookAndFeel.setLookAndFeel(this);
		setVisible(true);
	}

	void init() {
		filed.setEditable(false);
		String[] strList = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		for (String str : strList) {
			model.addElement(str);
		}
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(model);
		list.setVisibleRowCount(10);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				String fontName = list.getSelectedValue();
				filed.setFont(new Font(fontName, Font.PLAIN, 16));
			}
		});
		// list.setAutoscrolls(true);
		scroll = new JScrollPane();
		scroll.setViewportView(list);
		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(filed, BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
		b1 = new JJButton("局部生效");
		b2 = new JJButton("全局生效");
		b3 = new JJButton("返回");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		JPanel panel2 = new JJPanel();
		panel2.add(b1);
		panel2.add(b2);
		panel2.add(b3);
		add(panel2, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String com = e.getActionCommand();
		if (com.equals("局部生效") || com.equals("全局生效")) {
			MutableAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setFontFamily(attr, list.getSelectedValue());
			int startpos = text.getSelectionStart();
			int endpos = text.getSelectionEnd();
			StyledDocument doc = text.getStyledDocument();
			if (com.equals("局部生效")){
				doc.setCharacterAttributes(startpos, endpos - startpos, attr,
						false);
				StyleConstants.setFontFamily(attr, "宋体");
				try {
					doc.insertString(endpos, " ", null);
					doc.setCharacterAttributes(endpos, 1, attr, false);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
				text.setCharacterAttributes(attr, false);
		// setParagraphAttributes(startpos,endpos-startpos , attr, false);
	}
		this.dispose();
}
}
