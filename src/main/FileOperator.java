package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ui.Login;
import ui.NoteFrame;

public class FileOperator {
	private NoteFrame frame;

	public FileOperator(NoteFrame frame) {
		this.frame = frame;
	}

	// 实现把目标文本内容读到当前文本框中的方法，需要把被打开的文件的路径保存下来
	public static void mkdir(String filepath) {
		File file=null;
		try {
			file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			}

		} catch (Exception e) {
			
		}finally{
			file=null;
		}
	}

	public void readTo() {
		JFileChooser chooser = new JFileChooser("diary/" + Login.getUser());
		chooser.setSelectedFile(new File("*.txt"));// 设置默认选中文件名称
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"txt & TXT文本文件", "txt", "TXT");// 设置可选文件后缀名
		chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
		chooser.setFileFilter(filter);
		while (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			// 此时文件肯定是.txt格式或者.TXT格式的，因此不需要再判断后缀名了,只需要判断是否选择了文档文件
			File file = chooser.getSelectedFile();
			frame.setTempFile(file);// 保存当前打开的文件的路径
			if (file.exists()) {
				frame.setTitle(file.getName());
				try {
					BufferedReader reader = new BufferedReader(new FileReader(
							file.getAbsoluteFile()));
					try {
						String tmp = reader.readLine();
						while (tmp != null) {
							NoteFrame.getJTextArea().append(tmp + "\n");
							tmp = reader.readLine();
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(frame, "读取文件失败！");
					}

					// 读出来
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(frame, "获取文件路径失败！");
				}
				break;
			}
		}
		// 成功读取文件之后让changed初始值为false
		frame.setChanged(false);
	}

	// 实现把当前文本框的文字写入到目标路径下的方法
	public Boolean writeBack(File file) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			BufferedWriter bufferwriter = new BufferedWriter(writer);
			bufferwriter.write(frame.getDate());
			bufferwriter.newLine();
			bufferwriter.write(NoteFrame.getJTextArea().getText());
			bufferwriter.flush();
			bufferwriter.close();
			writer.close();
			frame.setChanged(false);// 回写一次之后，此时当前文本没有被修改
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "保存文件失败！");
			return false;
		}
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
		if (frame.getChanged()) {
			int result = JOptionPane.showConfirmDialog(frame,
					"文件已经修改，是否保存当前文件？", "询问", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				// 调用保存方法
				saveFile();
				frame.setFile();// 新建文档
				NoteFrame.getJTextArea().setText("");
				frame.setChanged(false);
			} else if (result == JOptionPane.NO_OPTION) {
				frame.setFile();
				NoteFrame.getJTextArea().setText("");
				frame.setChanged(false);
			}
		} else {
			frame.setFile();
			NoteFrame.getJTextArea().setText("");
			frame.setChanged(false);
		}
	}

	// 实现打开文件
	public void openFile() {
		if (frame.getChanged()) {
			int result = JOptionPane.showConfirmDialog(frame,
					"文件已经修改，是否保存当前文件？", "询问", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == 0) {
				// 如果选择保存，则先保存当前文件，再把要打开的文件的文字读入到当前文本框中
				saveFile();
				NoteFrame.getJTextArea().setText("");
				readTo();
			} else if (result == 1) {
				NoteFrame.getJTextArea().setText("");
				readTo();
			}
		} else {
			// 如果当前文件没有修改，则直接打开目标文件
			NoteFrame.getJTextArea().setText("");
			readTo();
		}
	}

	// 实现保存文件
	public void saveFile() {
		File file=null;
		if(frame.getTempFile()==null){
			JFileChooser chooser = new JFileChooser("diary/" +Login.getUser());
			chooser.setSelectedFile(new File("*.txt"));// 设置默认选中文件名称
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"txt & TXT文本文件", "txt", "TXT");// 设置可选文件后缀名
			chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
			chooser.setFileFilter(filter);
			int reback=chooser.showSaveDialog(frame);
			if(reback==JFileChooser.APPROVE_OPTION){
				file=chooser.getSelectedFile();
				writeBack(file);
			}else{
				return;
			}
		}else{file=frame.getTempFile();
			writeBack(file);
		}
		
		int length = NoteFrame.getJTextArea().getText().length();
		NoteFrame.getJTextArea().setCaretPosition(length);
	}

	// 实现文件另存为
	public void saveasFile() {
		JFileChooser chooser = new JFileChooser("diary/"+Login.getUser());
		chooser.setSelectedFile(new File("*.txt"));// 设置默认选中文件名称
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"txt & TXT文本文件", "txt", "TXT");// 设置可选文件后缀名
		chooser.setAcceptAllFileFilterUsed(false);// 取消所有文件选项
		chooser.setFileFilter(filter);
		while (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			// 获得后缀名，从.开始到结束(lastIndexOf(".")返回.在字符串中最后一次出现的坐标值，而subString(int
			// beginIndex))
			String str = file.getName().substring(
					file.getName().lastIndexOf("."));
			if (str.equals(".txt") || str.equals(".TXT")) {
				if (file.exists()) {
					int result = JOptionPane.showConfirmDialog(frame,
							"文件已存在，是否覆盖文件？", "询问", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						// 先删除该目录下的原文件，再新建一个同名的文件，最后写入内容
						file.delete();
						if (writeBack(file)) {
							NoteFrame.getJTextArea().setText("");// 清空数据
							break; // 关闭窗口
						}
					}
				} else {
					if (writeBack(file)) {
						NoteFrame.getJTextArea().setText("");// 清空数据
						break;
					}
				}
			} else {
				JOptionPane.showMessageDialog(frame,
						"您输入的文件名格式不对，为了您能正常打开文件，请重新输入！");
			}
		}
		// 保存之后修改标题
		frame.setTitle("含羞草专属记事本");
		// 修改changed为false
		frame.setChanged(false);
	}

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
	public  void exit() {
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
			int result = JOptionPane.showConfirmDialog(null, "确定要退出吗?"," ",JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				frame.dispose();
				System.exit(0);
			}else{
				return;
			}
		}
	}
}
