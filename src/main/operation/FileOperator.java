package main.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

import ui.Login;
import ui.NoteFrame;
import util.Command;
import util.MyCommand;
import util.Reciver;
import util.Work;

public class FileOperator {
	private NoteFrame frame;
//	private HTMLDocument doc;
//	private HTMLEditorKit editorKit;
	private File tfile;
	String defaultPath = FileOperator.class.getResource("/").getPath()
			+ Login.getUser();
	private File savefile;
	private File openfile;
	private File readfile;
//	private File savefile;
	private  String[] ext={"xby 文件", "XBY", "xby"};
	
	public FileOperator(NoteFrame frame) {
		this.frame = frame;
//		doc = (HTMLDocument) frame.getMyDocument();
//		editorKit = (HTMLEditorKit) frame.getJJTextPane().getEditorKit();
		File path = new File(defaultPath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	public void readTo() {
		JFileChooser chooser = new JFileChooser(defaultPath);
		// chooser.setSelectedFile(new File("*.txt"));// 设置默认选中文件名称
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				ext[0],ext[1],ext[2]);// 设置可选文件后缀名
		chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			readfile = chooser.getSelectedFile();
//			frame.setTempFile(file);// 保存当前打开的文件的路径
			if (readfile.exists()) {
//				frame.setTitle(readfile.getName());
				frame.setTabTitle(readfile.getName());
				frame.tabAdd();
//				BufferedReader bufin = new BufferedReader(new FileReader(file));
//				try {
//					frame.getJJTextPane().getEditorKit()
//							.read(bufin, frame.getMyDocument(), 0);
//					bufin.close();
//				} catch (IOException | BadLocationException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				Command com=new MyCommand(new Reciver(){
					public void action(){
						open(readfile);
					}
				});
				new Work(com).execute();
			}

		}
		// 成功读取文件之后让changed初始值为false
		frame.setChanged(false); 
	}
	void open(File file){
		openfile = file;
		
				try {
					FileInputStream in = new FileInputStream(openfile);
					ObjectInputStream input = new ObjectInputStream(in);
					HTMLDocument doc=(HTMLDocument) input.readObject();
					doc.addUndoableEditListener(new UndoableEditListener() {
					
					@Override
					public void undoableEditHappened(UndoableEditEvent e) {
						// TODO Auto-generated method stub
						frame.getUndoManager().addEdit(e.getEdit());
					}
				});// 添加可撤销、恢复编辑监听
					frame.getDocList().set(frame.getSelectTabIndex(),doc);
					frame.getJJTextPane().setDocument(frame.getMyDocument());
//					frame.htmldoc=doc;
//					frame.textPane.setDocument(frame.htmldoc);
					input.close();
					in.close();
				}catch( StreamCorruptedException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "错误,不支持的文件格式,请重新打开支持的文件", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}catch(java.io.EOFException  e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "错误,不支持的文件格式,请重新打开支持的文件", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();

				} 
	}
	// 实现把当前文本框的文字写入到目标路径下的方法
	public Boolean writeBack(File file) {
		tfile = file;
		// try {
		// BufferedWriter bufout = new BufferedWriter(new FileWriter(tfile));
		// editorKit.write(bufout, doc, 0, doc.getEndPosition().getOffset());
		// bufout.close();
	
		try {
				frame.getJJTextPane().getDocument().insertString(0, frame.getDate() + "\n", null);
			
			FileOutputStream out = new FileOutputStream(tfile);
			ObjectOutputStream output = new ObjectOutputStream(out);
//			output.writeObject(frame.htmldoc);
			output.writeObject(frame.getJJTextPane().getDocument());
			output.flush();
			output.close();
			out.close();
//			frame.textPane.setText("");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
//		}catch (BadLocationException e) {
			// // TODO Auto-generated catch block
//			e.printStackTrace();
		}catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getJJTextPane().setText("");
		// writer.close();

		frame.setChanged(false);// 回写一次之后，此时当前文本没有被修改
		return true;

	}

	// 实现删除文件
	public void deleteFile(File file) {
		if (!file.delete()) {
			JOptionPane.showMessageDialog(frame, "退出时删除文件出错", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// 实现新建文件
	public void createFile() {
//		if (frame.getChanged()) {
//			int result = JOptionPane.showConfirmDialog(frame,
//					"文件已经修改，是否保存当前文件？", "询问", JOptionPane.YES_NO_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE);
//			if (result == JOptionPane.YES_OPTION) {
//				// 调用保存方法
//				saveFile();
//				frame.getJJTextPane().setText("");
//				frame.setChanged(false);
//			} else if (result == JOptionPane.NO_OPTION) {
//				frame.getJJTextPane().setText("");
//				frame.setChanged(false);
//			}
//		} else {
//			frame.getJJTextPane().setText("");
//			frame.setChanged(false);
//		}
		frame.tabAdd();
	}

	// 实现打开文件
	public void openFile() {
		
		/*
		 * if (frame.getChanged()) { int result =
		 * JOptionPane.showConfirmDialog(frame, "文件已经修改，是否保存当前文件？", "询问",
		 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE); if
		 * (result == JOptionPane.YES_OPTION) { //
		 * 如果选择保存，则先保存当前文件，再把要打开的文件的文字读入到当前文本框中 saveFile();
		 * frame.getJJTextPane().setText(""); try { readTo(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } else if (result == JOptionPane.NO_OPTION) {
		 * frame.getJJTextPane().setText(""); try { readTo(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } } else { // 如果当前文件没有修改，则直接打开目标文件
		 * frame.getJJTextPane().setText(""); try { readTo(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
	}

	// 实现保存文件
	public void save(File file) {
		savefile = file;
		Command com=new MyCommand(new Reciver(){
			public void action(){
				writeBack(savefile);
			}
		});
		new Work(com).execute();
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				writeBack(savefile);
//
//			}
//		});
	}

	public void saveFile() {

		File file ;
//		if (frame.getTempFile() == null) {
			JFileChooser chooser = new JFileChooser(defaultPath);
			chooser.setSelectedFile(new File(".xby"));// 设置默认选中文件名称
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					ext[0],ext[1],ext[2]);// 设置可选文件后缀名
			chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
			chooser.setFileFilter(filter);
			if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				if(!file.getName().endsWith(".xby"))
					file=new File(file.getPath()+".xby");
				if (file.exists()) {
					int confirm = JOptionPane.showConfirmDialog(null,
							"是否覆盖已有文件？");
					if (confirm == JOptionPane.YES_OPTION){
						
//						writeBack(file);
						save(file);
					}else{
						return;
					}
				} else {
					/* writeBack(file); */
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					writeBack(file);
					save(file);
				}
			} 
//		} else {
//			file = frame.getTempFile();
//			writeBack(file);
//		}

//		int length = doc.getLength();
//		frame.getJJTextPane().setCaretPosition(length);
	}

//	// 实现文件另存为
//	public void saveasFile() {
//		JFileChooser chooser = new JFileChooser(defaultPath);
//		chooser.setSelectedFile(new File("*.html"));// 设置默认选中文件名称
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(
//				"html & HTML文件", "html", "HTML");// 设置可选文件后缀名
//		chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
//		chooser.setFileFilter(filter);
//		if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
//			File file = chooser.getSelectedFile();
//			// 获得后缀名，从.开始到结束(lastIndexOf(".")返回.在字符串中最后一次出现的坐标值，而subString(int
//			// beginIndex))
//			String str = file.getName().substring(
//					file.getName().lastIndexOf("."));
//			if (str.equals(".html") || str.equals(".HTML")) {
//				if (file.exists()) {
//					int result = JOptionPane.showConfirmDialog(frame,
//							"文件已存在，是否覆盖文件？", "询问", JOptionPane.YES_NO_OPTION,
//							JOptionPane.QUESTION_MESSAGE);
//					if (result == JOptionPane.YES_OPTION) {
//						// 先删除该目录下的原文件，再新建一个同名的文件，最后写入内容
//						// writeBack(file);
//						save(file);
//						// frame.getJJTextPane().setText("");// 清空数据
//					}
//				} else {
//					// writeBack(file);
//					save(file);
//				}
//			} else {
//				JOptionPane.showMessageDialog(frame,
//						"您输入的文件名格式不对，为了您能正常打开文件，请重新输入！");
//			}
//		}
//		// 保存之后修改标题
//		frame.setTitle("含羞草专属记事本");
//		// 修改changed为false
//		frame.setChanged(false);
//	}

	// 实现页面设置方法
	public void pageSetup() {
		// 暂且跟Windows下的记事本中此功能一样弹出对话框，需要进一步改进
		JOptionPane.showConfirmDialog(null, "在您可以执行与打印机相关的任务（例如页面设置或打印一个"
				+ "文档）之前，您必须已经安装打印机。您想现在安装打印机吗？", "询问",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	// 实现打印方法
	public void print() {
		// 查看有没有安装打印机
		JOptionPane.showConfirmDialog(frame, "在您可以执行与打印机相关的任务（例如页面设置或打印一个"
				+ "文档）之前，您必须已经安装打印机。您想现在安装打印机吗？", "询问",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	// 实现退出方法
	public void exit() {
		if (frame.getChanged() == true) {
			int result = JOptionPane.showConfirmDialog(null,
					"文件已经修改，是否在退出之前保存文件？", "询问",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveFile();
				frame.dispose();
				System.exit(0);
			} else if (result == JOptionPane.NO_OPTION) {
				// 不保存，则不往回写这个文件
				frame.dispose();
				System.exit(0);
			}
		} else {
			int result = JOptionPane.showConfirmDialog(null, "确定要退出吗?", " ",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				frame.dispose();
				System.exit(0);
			} else {
				return;
			}
		}
	}
}
