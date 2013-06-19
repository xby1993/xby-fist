package main;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ui.NoteFrame;
public class Edit {
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static StringSelection contents;
   NoteFrame frame;
    public Edit(NoteFrame frame){
    	this.frame=frame;
    }
	 public static void cancel() {
	 if(NoteFrame.getUndoManager().canUndo())
     {
         NoteFrame.getUndoManager().undo();  
     }
	 }
	 public static void recover(){
	 if(NoteFrame.getUndoManager().canRedo())
     {
         NoteFrame.getUndoManager().redo();
     }
	 }
	 //实现剪切方法，包括右键菜单中的剪切方法
	    public static void Cut()
	    {
	        String str = NoteFrame.getJTextArea().getSelectedText().replaceAll("\n", "");
	        contents = new StringSelection(str);//将string对象封装成StringSelection对象
	        clipboard.setContents(contents, null);
	        NoteFrame.getJTextArea().replaceSelection("");      
	    }
	    //实现复制方法，包括右键菜单中的复制方法
	    public void Copy()
	    {
	        String str = NoteFrame.getJTextArea().getSelectedText().replaceAll("\n", "");
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
	                if(NoteFrame.getJTextArea().getSelectedText()!=null)//如果粘贴的时候选中了文字，则覆盖文字
	                {
	                    NoteFrame.getJTextArea().replaceSelection(content);
	                }
	                else//如果没有选中文字，则插入文字
	                {
	                    NoteFrame.getJTextArea().insert(content, NoteFrame.getJTextArea().getCaretPosition());
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            } 
	        }
	    }
	    //实现删除方法，包括右键菜单中的删除方法
	    public void Delete()
	    {
	        NoteFrame.getJTextArea().replaceSelection("");//把选中的文字替换为空
	    }
	   
	    public void SwitchTo()
	    {
	        String [] text = NoteFrame.getJTextArea().getText().split("\n");
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
	                    NoteFrame.getJTextArea().setCaretPosition(pos);
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
	        NoteFrame.getJTextArea().selectAll();
	    }
	    //实现日期/时间方法
	    public void insetDatetime()
	    {
	        Calendar calendar = Calendar.getInstance();
	        int pos = NoteFrame.getJTextArea().getCaretPosition();//获取光标所在位置
	        NoteFrame.getJTextArea().insert(calendar.getTime().toString(), pos);
	    }
}
