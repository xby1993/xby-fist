package main.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class FontStyleAction implements ActionListener{
	/**
	 * 
	 */
	private JTextPane text;
	public FontStyleAction(JTextPane text){
		this.text=text;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 SimpleAttributeSet attr = new SimpleAttributeSet();
		String com=e.getActionCommand();
		switch (com) {
		case "Bold":
		case "粗体":
			StyleConstants.setBold(attr, true);
			break;
		case "Italic":
		case "斜体":
			StyleConstants.setItalic(attr, true);
		case "Underline":
		case "下划线":
			StyleConstants.setUnderline(attr, true);
		default:
			break;
		}
		text.setCharacterAttributes(attr, false);
		try {
			text.getStyledDocument().insertString(text.getSelectionEnd(), " ", null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StyleConstants.setBold(attr, false);
		StyleConstants.setItalic(attr, false);
		StyleConstants.setUnderline(attr, false);
		text.getStyledDocument().setCharacterAttributes(text.getSelectionEnd(), 1, attr, false);
		
	}

}
