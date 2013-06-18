package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.NoteFrame;

public class FindAndReplace extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private NoteFrame mf;
    private FindAndReplace far = this;
    private JLabel findwhat = new JLabel("查找内容(N):");
    private JLabel replaceto = new JLabel("  替换为(P):");
    private JTextField findtext = new JTextField("");
    private JTextField replacetext = new JTextField("");
    private JCheckBox distinguish = new JCheckBox("区分大小写");
    private JRadioButton up = new JRadioButton("向上(U)");
    private JRadioButton down = new JRadioButton("向下(D)",true);//默认向下找
    private JButton findnext = new JButton("查找下一个");
    private JButton findall = new JButton("查找全部");
    private JButton replace = new JButton("替换");
    private JButton replaceall = new JButton("全部替换"); 
    private JButton cancle = new JButton("取消");
    private int findnextcount = 0;//用于计数单击了多少次查找下一个
    public FindAndReplace(NoteFrame mf)//MainFrame mf
    {
        this.mf = mf;
    }
    public void init()
    {
        //标签、文本框、按钮是否可用
        findnext.setEnabled(false);
        findall.setEnabled(false);
        replace.setEnabled(false);
        replaceall.setEnabled(false);
        //监听器
        findtext.addKeyListener(new KeyAdapter()        
        {
            public void keyReleased(KeyEvent ke) 
            {
               if(findtext.getText().length()!=0)
               {
                   findnext.setEnabled(true);
                   findall.setEnabled(true);
               }
               else
               {
                   findnext.setEnabled(false);
                   findall.setEnabled(false);
               }      
            }    
        });
        replacetext.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                if(replacetext.getText().length()!=0)
               {
                   replace.setEnabled(true);
                   replaceall.setEnabled(true);
               }
               else
               {
                   replace.setEnabled(false);
                   replaceall.setEnabled(false);
               }
            }
        });
        findnext.setActionCommand("查找下一个");
        findall.setActionCommand("查找全部");
        replace.setActionCommand("替换");
        replaceall.setActionCommand("全部替换");
        cancle.setActionCommand("取消");
        findnext.addActionListener(new MyListener());
        findall.addActionListener(new MyListener());
        replace.addActionListener(new MyListener());
        replaceall.addActionListener(new MyListener());
        cancle.addActionListener(new MyListener());
        //添加查找标签和第一个文本框的box
        Box box1 = Box.createHorizontalBox();
        box1.add(Box.createHorizontalStrut(5));
        box1.add(findwhat);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(findtext);
        box1.add(Box.createHorizontalStrut(5));
        //添加替换标签第二个文本框box
        Box box2 = Box.createHorizontalBox();
        box1.add(Box.createHorizontalStrut(5));
        box2.add(replaceto);
        box2.add(Box.createHorizontalStrut(15));
        box2.add(replacetext);
        box2.add(Box.createHorizontalStrut(10));
        //添加两个单选按钮的Box
        up.setActionCommand("向上");
        down.setActionCommand("向下");
        up.addActionListener(new MyListener());
        down.addActionListener(new MyListener());
        ButtonGroup bg = new ButtonGroup();
        bg.add(up);
        bg.add(down);
        Box box3 = Box.createHorizontalBox();
        box3.setBorder(BorderFactory.createTitledBorder("方向"));
        box3.add(Box.createHorizontalStrut(5));
        box3.add(up);
        box3.add(Box.createHorizontalStrut(5));
        box3.add(down);
        box3.add(Box.createHorizontalStrut(5));
        //添加区分复选框和两个单选按钮的box
        Box box4 = Box.createHorizontalBox();
        box4.add(distinguish);
        box4.add(box3);
        //主窗体的Box
        Box mainbox = Box.createVerticalBox();
        mainbox.add(Box.createVerticalStrut(5));
        mainbox.add(box1);
        mainbox.add(Box.createVerticalStrut(15));
        mainbox.add(box2);
        mainbox.add(Box.createVerticalStrut(15));
        mainbox.add(box4);
        //右边区域的box
        Box box5 = Box.createHorizontalBox();
        box5.add(Box.createHorizontalStrut(5));
        box5.add(findnext);
        box5.add(Box.createHorizontalStrut(3));
        box5.add(findall);
        box5.add(Box.createHorizontalStrut(3));
        box5.add(replace);
        box5.add(Box.createHorizontalStrut(3));
        box5.add(replaceall);
        box5.add(Box.createHorizontalStrut(3));
        box5.add(cancle);
        box5.add(Box.createHorizontalStrut(5));
        mainbox.add(Box.createVerticalStrut(15));
        mainbox.add(box5);
        mainbox.add(Box.createVerticalStrut(5));
        this.add(mainbox);
        this.pack();
        this.setResizable(false);
    }    
private class MyListener implements ActionListener
{
    public void actionPerformed(ActionEvent ae) 
    {
        
        switch(ae.getActionCommand())
        {
            case "向上":
                break;
            case "向下":
                break;
            case "查找下一个":
                findnextcount++;
                findNext();
                break;
            case "查找全部":
                findAll();
                break;
            case "替换":
                replace();
                break;
            case "替换全部":
                replaceAll();
                break;
            case "取消":
                far.removeAll();
                far.dispose();
                break;
        }
    }  
}
   private int findNext()
   {
       //实现向上和向下方向搜素的比较简单，但是实现以光标当前的位置来上下搜素没有达到令人满意的效果，所以暂时没完善
       String find = findtext.getText();
       String main = NoteFrame.getJTextArea().getText().replaceAll("\n", "");
       int count = 1;
       if(!distinguish.isSelected())//不区分大小写
       {
           find = find.toLowerCase();
           main = main.toLowerCase();                  
       }
       //因为在单击按钮的时候光标就已经不在文本域中了，读取光标的位置不太靠谱
       int length = main.length() - find.length() + 1;
       if(length>0)
       {
           int [] start = new int[length];//标记匹配处开头位置，最多有Length个匹配的开头坐标值
            for(int i =0;i<length;i++)
            {
                if(main.substring(i, i+find.length()).equals(find))
                {
                     //要记下i的位置
                     start[count] = i;
                     count++;
                }
            }
            if(findnextcount<count)
            {
                NoteFrame.getJTextArea().setSelectedTextColor(Color.RED);
                if(up.isSelected())
                {
                    NoteFrame.getJTextArea().select(start[count-findnextcount], start[count-findnextcount]+find.length());
                }
                if(down.isSelected())
                {
                    NoteFrame.getJTextArea().select(start[findnextcount], start[findnextcount]+find.length());
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(this, "找不到"+find);
                NoteFrame.getJTextArea().select(0, 0);
                findnextcount = 0;
                count = 1;
                return 0;
            }
       }
       else
       {
           JOptionPane.showMessageDialog(this, "找不到"+find);
           NoteFrame.getJTextArea().select(0, 0);
           findnextcount = 0;
           count = 1;
           return 0;
       }
       return 0;
   }
   public void findAll()
   {
     
   } 
   private void replace()
   {
       NoteFrame.getJTextArea().replaceSelection(replacetext.getText());
   }
   private void replaceAll()
   {
       
   }
   public void showFindAndReplace(NoteFrame mf,String title)
   {
//       if(title.equals("查找")||title.equals("查找下一个"))
//       {
//           this.replacetext.setEnabled(false);
//           this.replace.setEnabled(false);
//           this.replaceall.setEnabled(false);
//       }
       this.setTitle(title);
       this.init();
       this.setLocationRelativeTo(mf);
//       this.setAlwaysOnTop(true);
       this.setVisible(true);
   }
}
