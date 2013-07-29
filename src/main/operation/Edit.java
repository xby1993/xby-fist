package main.operation;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import ui.NoteFrame;
public class Edit {
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static StringSelection contents;
  private  NoteFrame frame;
    public Edit(NoteFrame frame){
    	this.frame=frame;
    }
	 public  void cancel() {
	 if(frame.getUndoManager().canUndo())
     {
         frame.getUndoManager().undo();  
     }
	 }
	 public  void recover(){
	 if(frame.getUndoManager().canRedo())
     {
         frame.getUndoManager().redo();
     }
	 }
	 //实现剪切方法，包括右键菜单中的剪切方法
	    public  void Cut()
	    {
	        String str = frame.getJJTextPane().getSelectedText().replaceAll("\n", "");
	        contents = new StringSelection(str);//将string对象封装成StringSelection对象
	        clipboard.setContents(contents, null);
	        frame.getJJTextPane().replaceSelection("");      
	    }
	    //实现复制方法，包括右键菜单中的复制方法
	    public void Copy()
	    {
	        String str = frame.getJJTextPane().getSelectedText().replaceAll("\n", "");
	        contents = new StringSelection(str);
	        clipboard.setContents(contents, null);
	    }
	    //是按粘贴方法，包括右键菜单中的粘贴方法
	    public void Paste()
	    {     
	        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
	        {
	            try {
	                //取出剪切板中的内容
	                String content = (String)clipboard.getData(DataFlavor.stringFlavor);
	                if(frame.getJJTextPane().getSelectedText()!=null)//如果粘贴的时候选中了文字，则覆盖文字
	                {
	                    frame.getJJTextPane().replaceSelection(content);
	                }
	                else//如果没有选中文字，则插入文字
	                {
	                    frame.getJJTextPane().getDocument().insertString(frame.getJJTextPane().getCaretPosition(),content,null);
	                    
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            } 
	        }
	    }
	    //实现删除方法，包括右键菜单中的删除方法
	    public void Delete()
	    {
	        frame.getJJTextPane().replaceSelection("");//把选中的文字替换为空
	    }
	   
	    public void SwitchTo()
	    {
	        String [] text = frame.getJJTextPane().getText().split("\n");
	        String str = JOptionPane.showInputDialog(frame, "行数(L):","转到下列行",
	                JOptionPane.QUESTION_MESSAGE);      
	        if(str!=null)//str==null是点击了取消按钮触发的事件
	        {
	            Pattern pattern = Pattern.compile("[0-9]");
	            if(str.trim().length()!=0&&pattern.matcher(str).matches())
	            {
	                int row = Integer.parseInt(str);
	                if(row>=1&&row<=text.length)
	                {
	                    int pos = 0;
	                    for(int i = 0;i<row-1;i++)
	                    {
	                        pos += text[i].length() + 1;
	                    }
	                    frame.getJJTextPane().setCaretPosition(pos);
	                }
	                else
	                {
	                    JOptionPane.showMessageDialog(frame, "列数超出范围！", "错误提示", JOptionPane.ERROR_MESSAGE);
	                }
	            } 
	            else
	            {
	                JOptionPane.showMessageDialog(frame, "无效的字符！", "错误提示", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	    //实现全选方法，包括右键菜单中的全选方法
	    public void SelectAll()
	    {
	        frame.getJJTextPane().selectAll();
	    }
	    //实现日期/时间方法
	    public void insetDatetime()
	    {
	        Calendar calendar = Calendar.getInstance();
	        int pos = frame.getJJTextPane().getCaretPosition();//获取光标所在位置
//	        NoteFrame.getJJTextPane().insert(calendar.getTime().toString(), pos);
	        try {
				frame.getMyDocument().insertString(pos, calendar.getTime().toString(), null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
