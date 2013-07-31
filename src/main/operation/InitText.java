package main.operation;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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
				text.getStyledDocument().setParagraphAttributes(0, Strings.getManual().length(), attr, false);
				text.getStyledDocument().insertString(0,Strings.getManual(),null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
