package main.operation;


import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import source.Strings;

/**
 * 用于一打开软件呈现说明文档
 * 
 * @author xby64
 * 
 */
public class InitText extends Thread {
	JTextPane text;
	public InitText(JTextPane text) {
		this.text=text;
	}
	public void run(){
			try {
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setFontSize(attr, 16);
				StyledDocument doc=text.getStyledDocument();
				doc.setParagraphAttributes(0, doc.getLength(), attr, false);
				doc.insertString(0, Strings.getInstance().getAbout(),attr);
				
//				text.getStyledDocument().insertString(0,Strings.getManual(),null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
