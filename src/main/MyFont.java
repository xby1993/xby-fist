package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.MyFont;
import ui.NoteFrame;

public class MyFont extends JDialog {
	 /** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NoteFrame mf;
	    private Box box;
	    private JTextField fonttext;//字体文本框
	    private JTextField styletext;//字形文本框
	    private JTextField sizetext;//字体大小文本框
	    private JList<String> fontlist;//字体选择列表
	    private JList<String> stylelist;//字形选择列表
	    private JList<String> sizelist;//大小选择列表
	    private JTextField previewtext;//预览文本框
	    private JRadioButton chinaview;//中文预览
	    private JRadioButton westview;//西文预览
	    private JRadioButton numberview;//数字预览
	    private String Chinese = "小小记事本";
	    private String English = "NotePad";
	    private String Number = "0123456789";
	    private JButton ensure;//确定按钮
	    private JButton cancel;//取消按钮
	    public static int ensure_option = 1;//点击确定时返回1
	    public static int cancle_option = 0;
	    public int returnValue = 3;
	    private Font font;//预设字体和将返回选择的字体
	    //所有字体
	    private String [] fontarray;
	    //所有字形
	    private String [] stylearray = {"常规","斜体","粗体","粗斜体"};
	    //所有字体大小
	    private String [] sizearray = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72",
	        "初号","小号","一号","小一","二号","小二","三号","小三","四号","小四","五号","小五","六号","小六","七号","八号"};
	    //所有字体大小对应的数值
	    private int [] sizeofint = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 
	        42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};
	    public MyFont()
	    {
	    }
	    public MyFont(Font font,NoteFrame mf)
	    {
	        this.setTitle("字体选择器");        
	        this.mf = mf;        
	        this.font = font;     
	        init();
	        this.setResizable(false);
	        this.pack();
	    }
	    public void init()
	    {
	        //获得系统字体
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        fontarray = ge.getAvailableFontFamilyNames();
	        //主容器，垂直摆放其中的控件
	        box = Box.createVerticalBox();
	        box.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));//周边留空
	        fonttext = new JTextField("Fixedsys");
	        fonttext.setEditable(false);
	        fonttext.setBackground(Color.WHITE);
	        styletext = new JTextField("常规");
	        styletext.setEditable(false);
	        styletext.setBackground(Color.WHITE);
	        sizetext = new JTextField("12");
	        sizetext.setEditable(false);
	        sizetext.setBackground(Color.WHITE);
	        previewtext = new JTextField(10);
	        previewtext.setHorizontalAlignment(JTextField.CENTER);//中间对齐
	        previewtext.setText(Chinese);
	        previewtext.setFont(font);
	        previewtext.setEditable(false);
	        previewtext.setBackground(Color.WHITE);
	        chinaview = new JRadioButton("中文预览",true);
	        westview = new JRadioButton("西文预览");
	        numberview = new JRadioButton("数字预览");
	        ButtonGroup viewselect = new ButtonGroup();
	        viewselect.add(chinaview);
	        viewselect.add(westview);
	        viewselect.add(numberview);
	        fontlist = new JList<String>(fontarray);
	        stylelist = new JList<String>(stylearray);
	        sizelist = new JList<String>(sizearray);
	        ensure = new JButton("确定");
	        cancel = new JButton("取消");
	        Box fontbox = Box.createVerticalBox();
	        fontbox.setBorder(BorderFactory.createTitledBorder("字体(F):"));
	        fontbox.add(fonttext);
	        JScrollPane fontscrp = new JScrollPane(fontlist);
	        fontscrp.setPreferredSize(new Dimension(160,100));
	        fontscrp.setMinimumSize(new Dimension(160,100));
	        fontscrp.setMaximumSize(new Dimension(160,100));
	        fontbox.add(fontscrp);
	        Box stylebox = Box.createVerticalBox();
	        stylebox.setBorder(BorderFactory.createTitledBorder("字形(Y):"));
	        stylebox.add(styletext);
	        JScrollPane stylescrp = new JScrollPane(stylelist);
	        stylescrp.setPreferredSize(new Dimension(90,100));
	        stylescrp.setMinimumSize(new Dimension(90,100));
	        stylescrp.setMaximumSize(new Dimension(90,100));
	        stylebox.add(stylescrp);
	        Box sizebox = Box.createVerticalBox();
	        sizebox.setBorder(BorderFactory.createTitledBorder("大小(S):"));
	        sizebox.add(sizetext);
	        JScrollPane sizescrp = new JScrollPane(sizelist);
	        sizescrp.setPreferredSize(new Dimension(80,100));
	        sizescrp.setMinimumSize(new Dimension(80,100));
	        sizescrp.setMaximumSize(new Dimension(80,100));
	        sizebox.add(sizescrp);
	        Box northbox = Box.createHorizontalBox();//水平Box放置字体、字形和大小选择列表
	        northbox.add(Box.createHorizontalStrut(2));
	        northbox.add(fontbox);
	        northbox.add(Box.createHorizontalStrut(5));
	        northbox.add(stylebox);
	        northbox.add(Box.createHorizontalStrut(5));
	        northbox.add(sizebox);
	        northbox.add(Box.createHorizontalStrut(2));
	        box.add(northbox,BorderLayout.NORTH);      
	        Box charsetbox = Box.createVerticalBox();
	        charsetbox.setBorder(BorderFactory.createTitledBorder("字符集："));
	        charsetbox.add(chinaview);
	        charsetbox.add(westview);
	        charsetbox.add(numberview);
	        Box viewbox = Box.createVerticalBox();
	        viewbox.setBorder(BorderFactory.createTitledBorder("预览："));
	        viewbox.add(previewtext);
	        Box middbox = Box.createHorizontalBox();
	        middbox.add(Box.createHorizontalStrut(2));
	        middbox.add(charsetbox);
	        middbox.add(Box.createHorizontalStrut(5));
	        middbox.add(viewbox);
	        middbox.add(Box.createHorizontalStrut(2));
	        box.add(Box.createVerticalStrut(2));
	        box.add(middbox,BorderLayout.CENTER);       
	        Box btnbox = Box.createHorizontalBox();
	        btnbox.add(Box.createHorizontalStrut(235));
	        btnbox.add(ensure);
	        btnbox.add(Box.createHorizontalStrut(15));
	        btnbox.add(cancel);
	        btnbox.add(Box.createHorizontalStrut(2));
	        box.add(Box.createVerticalStrut(2));
	        box.add(btnbox,BorderLayout.SOUTH);  
	        this.add(box);
	        //事件监听
	        fontlist.addListSelectionListener(new ListSelectionListener()
	        {
	            public void valueChanged(ListSelectionEvent e)
	            {
	                fonttext.setText(String.valueOf(fontlist.getSelectedValue()));
	                //预览
	                setPreview();
	            }
	        });
	        stylelist.addListSelectionListener(new ListSelectionListener()
	        {
	            public void valueChanged(ListSelectionEvent e)
	            {
	                styletext.setText(String.valueOf(stylelist.getSelectedValue()));
	                //预览
	                setPreview();
	            }
	        });
	        sizelist.addListSelectionListener(new ListSelectionListener()
	        {
	            public void valueChanged(ListSelectionEvent e)
	            {
	                sizetext.setText(String.valueOf(sizelist.getSelectedValue()));
	                //预览
	                setPreview();
	            }
	        });
	        //单选按钮监听
	        chinaview.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                previewtext.setText(Chinese);
	            }
	        });
	        westview.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                previewtext.setText(English);
	            }
	        });
	        numberview.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                previewtext.setText(Number);
	            }
	        });
	        //确定和取消按钮监听
	        ensure.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {          
	                //设置字体的方法
	                NoteFrame.getJTextArea().setFont(groupFont());
	                //退出              
	                disposeDialog(mf);
	            }
	        });
	        cancel.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                disposeDialog(mf);
	            }
	        });
	    }       
	    //预览功能
	    private void setPreview()
	    {
	        Font f = groupFont();
	        previewtext.setFont(f);
	    }
	    //组合Font
	    public Font groupFont()
	    {
	            String fontname = fonttext.getText();
	            String style = styletext.getText();
	            String size = sizetext.getText();
	            int fontstyle = 0;  
	            for(int i = 0;i < stylearray.length;i++)
	            {
	                if(style.equals(stylearray[i]))
	                {
	                    fontstyle = i;
	                    break;
	                }
	            }
	            int fontsize = 0;
	            for(int i = 0;i < sizearray.length;i++)
	            {
	                if(size.equals(sizearray[i]))
	                {
	                    fontsize = sizeofint[i];
	                    break;
	                }
	            }
	            return new Font(fontname,fontstyle,fontsize);
	    }
	    /*显示字体选择器
	     * owner 为上层组件
	    */
	    public void showDialog(JFrame owner)
	    {
	        this.font = new Font("黑体",20,20);
	        this.setLocationRelativeTo(owner);
	        this.setVisible(true);    
	    }
	    //退出系统
	    public void disposeDialog(NoteFrame owner)
	    {  
	            this.removeAll();
	            this.dispose();
	    }
	    public void setFont()
	    {
	         new MyFont(new Font("黑体",20,20),mf).showDialog(mf);
	    }
}
