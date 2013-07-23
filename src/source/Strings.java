/**
 * this class is main use for String resource management to provide strings for other class
 * 2013.7.8
 * xby      
 */
package source;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class Strings {
	Logger log = Logger.getLogger("com.xby.log");
	FileHandler hander;
	private String NAME = "用户";
	private String PASS = "密码";
	private String LOGIN = "登陆";
	private String REGISTER = "注册";
	private String LOGIN_IMG = "/source/image/login.jpg";// 登陆所需图片路径
	private String REGISTER_IMG = "/source/image/3.jpg";// 注册所需图片路径
	private String POLICY_IMG = "/source/image/2.jpg";// 协议框图片路径
	private ArrayList<String> listIMG = new ArrayList<String>();// 获得外部图片资源列表
	private ArrayList<String> listMusic = new ArrayList<String>();// 获得外部音乐资源列表
	private String passwdPath = String.class.getResource("/").getPath()
			+ ".xby.ini";// 密码文件路径
	// private String passwdPath="/source/xby.ini";
	private File outfile;// 获取外部图片资源路径
	private String defaultIMG = "/source/image/4.jpg";
	private String defaultMusic = "/source/Music/default.mp3";
	public static int count = 0;
	private String pathSplit = "";
	private String[] font_Name = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private ArrayList<String> font_Familly_Name=new ArrayList<String>(15);

	/**
	 * 一个获得文件列表的工具方法，以便代码复用
	 * 
	 * @return
	 */
	public Strings() {
		// initlog();

		File file1 = new File(String.class.getResource("/").getPath()
				+ "img_dir");
		File file2 = new File(String.class.getResource("/").getPath()
				+ "img_dir");
		if (!file1.exists()) {
			file1.mkdir();
		}
		if (!file2.exists()) {
			file2.mkdir();
		}
		if (System.getProperty("os.name").equals("Linux")) {
			setPathSplit("/");
		} else {
			setPathSplit("\\");
		}
		file1 = null;
		file2 = null;
		initFont();
	}

	private ArrayList<String> getList(ArrayList<String> list,
			String defaultFile, String select) {
		// this.list=list;
		// 如果目录不存在
		if (!outfile.exists()) {
			outfile.mkdir();
		}
		File[] filelist = outfile.listFiles();
		// 如果里面没有文件，采用默认文件取代
		log.info("filelist.size--->" + filelist.length);
		if (filelist.length == 0) {
			// filelist[0]=new File(defaultFile);
			list.add(defaultFile);
			// count=-1;
			return list;
		}
		// list=new String[filelist.length];
		String str;
		log.info("filelist.leng--->" + filelist.length);
		for (int i = 0; i < filelist.length; i++) {
			str = filelist[i].getPath();
			log.info("str.getName-->" + str);
			// str=Strings.class.getResource("/").getPath()+"img_dir/"+str;
			// log.info("newstr.getName-->" + str);
			if (select.equals("picture")) {
				if (str.endsWith(".jpg") || str.endsWith(".JPG")
						|| str.endsWith(".png") || str.endsWith(".PNG")) {
					list.add(str);
				}
			} else if (select.equals("music")) {
				if (str.endsWith(".MP3") || str.endsWith(".mp3")) {
					list.add(str);
				}
			}
		}
		return list;
	}

	/**
	 * 获取图片列表
	 * 
	 * @return
	 */
	public ArrayList<String> getListIMG() {
		outfile = new File(String.class.getResource("/").getPath() + "img_dir");
		log.info(getList(listIMG, getDefaultIMG(), "picture").get(0));
		log.info(getList(listIMG, getDefaultIMG(), "picture").get(1));
		return getList(listIMG, getDefaultIMG(), "picture");
	}

	void initFont() {
		for(String str:font_Name){
			if(str.contains("宋体")||str.contains("方正")||str.contains("文泉")||
					str.contains("仿宋")||str.contains("黑体")||str.contains("楷体")||str.contains("微软雅黑"))
				font_Familly_Name.add(str);
			
		}
		font_Familly_Name.trimToSize();
	}

	public void initlog() {
		String logPath = Strings.class.getResource("/").getPath() + "xbyLog";
		File logFile = new File(logPath);
		if (!logFile.exists()) {
			logFile.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// logPath.append("//"+sdf.format(new Date())+".log");
		String filePath = logPath + "/" + sdf.format(new Date())
				+ "-%g-xby.log";
		// logFile = new File(filePath);
		// try {
		// if (logFile.exists()) {
		// logFile.delete();

		// logFile.createNewFile();
		//
		// } else {
		// logFile.createNewFile();
		// }

		try {
			hander = new FileHandler(filePath);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hander.setFormatter(new SimpleFormatter());

		log.addHandler(hander);
	}

	/**
	 * 获取音乐列表
	 * 
	 * @return
	 */
	public ArrayList<String> getListMusic() {
		outfile = new File(String.class.getResource("/").getPath()
				+ "music_dir");
		return getList(listMusic, getDefaultMusic(), "music");
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPASS() {
		return PASS;
	}

	public void setPASS(String pASS) {
		PASS = pASS;
	}

	public String getLOGIN() {
		return LOGIN;
	}

	public void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}

	public String getREGISTER() {
		return REGISTER;
	}

	/**
	 * @return the lOGIN_IMG
	 */
	public String getLOGIN_IMG() {
		return LOGIN_IMG;
	}

	/**
	 * @return the rEGISTER_IMG
	 */
	public String getREGISTER_IMG() {
		return REGISTER_IMG;
	}

	/**
	 * @return the pOLICY_IMG
	 */
	public String getPOLICY_IMG() {
		return POLICY_IMG;
	}

	/**
	 * @return the passwdPath
	 */
	public String getPasswdPath() {
		return passwdPath;
	}

	// /**
	// * @param passwdPath the passwdPath to set
	// */
	// public void setPasswdPath(String passwdPath) {
	// this.passwdPath = passwdPath;
	// }

	/**
	 * @return the defaultIMG
	 */
	public String getDefaultIMG() {
		return defaultIMG;
	}

	/**
	 * @param defaultIMG
	 *            the defaultIMG to set
	 */
	public void setDefaultIMG(String defaultIMG) {
		this.defaultIMG = defaultIMG;
	}

	/**
	 * @return the defaultMusic
	 */
	public String getDefaultMusic() {
		return defaultMusic;
	}

	/**
	 * @param defaultMusic
	 *            the defaultMusic to set
	 */
	public void setDefaultMusic(String defaultMusic) {
		this.defaultMusic = defaultMusic;
	}

	/**
	 * @return the pathSplit
	 */
	public String getPathSplit() {
		return pathSplit;
	}

	/**
	 * @param pathSplit
	 *            the pathSplit to set
	 */
	public void setPathSplit(String pathSplit) {
		this.pathSplit = pathSplit;
	}

	/**
	 * @return the font_Familly_Name
	 */
	public ArrayList<String> getFont_Familly_Name() {
		return font_Familly_Name;
	}

	/**
	 * @param font_Familly_Name the font_Familly_Name to set
	 */
	public void setFont_Familly_Name(ArrayList<String> font_Familly_Name) {
		this.font_Familly_Name = font_Familly_Name;
	}
}
