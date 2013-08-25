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
	private static final String LOGIN_IMG = "/source/image/login.jpg";// 登陆所需图片路径
	private static final String REGISTER_IMG = "/source/image/3.jpg";// 注册所需图片路径
	private static final String POLICY_IMG = "/source/image/2.jpg";// 协议框图片路径
	private ArrayList<String> listIMG = new ArrayList<String>();// 获得外部图片资源列表
	private ArrayList<String> listMusic = new ArrayList<String>();// 获得外部音乐资源列表
	private static final String passwdPath = String.class.getResource("/")
			.getPath() + ".xby.ini";// 密码文件路径
	// private String passwdPath="/source/xby.ini";
	private File outfile;// 获取外部图片资源路径
	private static final String defaultIMG = "/source/image/4.jpg";
	private static final String defaultMusic = "/source/Music/default.mp3";
	public static int count = 0;
	private String pathSplit = File.separator;
	private static final String[] font_Name = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private ArrayList<String> font_Familly_Name = new ArrayList<String>(15);
	private static Strings resString = new Strings();
	/*
	 * private static final String manual="\t\t\t欢迎使用小白杨作品-日记本软件RC1.0\n\n"+
	 * "\t1.支持音乐选择功能,断点续播功能,拖动滑块快进快退功能\n" + "\t1.2.支持单曲循环,列表循环,随机播放模式\n" +
	 * "\t1.3.支持自定义背景音乐\n"+
	 * "\t1.4只要你将音乐文件放入软件目录下的-music_dir-目录即可,"+"音乐格式仅支持mp3\n"+
	 * "\t1.5支持音乐标签霓虹灯效果\n"+ "\t2.支持自定义背景图片,并幻灯片播放,只需将自己的图片放入-img_dir-目录即可\n"+
	 * "\t3.采用kde风格界面,界面精致美观\n"+ "\t4.支持富文本编辑以及插入图片文件\n"+
	 * "\t5.采用二进制形式保存用户的日记文件,有效保证了安全性\n"+ "\t5.1.对用户密码采用SHA-1算法,有效保证安全性"+
	 * "\t6.支持日志记录\n"+ "\t7.下一版本将集成数据库,敬请期待\n"+ "\t8.支持启动闪屏\n"+ "\t9.支持系统托盘\n"+
	 * "\t10.点击窗口的x其实是最小化到系统托盘,您可以右击托盘图标选择关闭按钮\n"+ "\t11.添加水印签名"+
	 * "\t12.欢迎及时反馈BUG,及新想法";
	 */
	private StringBuilder builder = new StringBuilder(500);

	/**
	 * 一个获得文件列表的工具方法，以便代码复用
	 * 
	 * @return
	 */
	private Strings() {
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
		// if (System.getProperty("os.name").equals("Linux")) {
		// setPathSplit("/");
		// } else {
		// setPathSplit("\\");
		// }
		file1 = null;
		file2 = null;
		initFont();
		// readFile();
		initAbout();
	}

	/**
	 * 由于加载说明文件的次数比较稀少所以每次请求返回一个对象，但是必须对返回的对象及时进行解出引用
	 * 
	 * @return
	 */
	public void initAbout() {
		builder.append("\t\t\t欢迎使用小白杨作品-日记本软件RC1.0\n\n")
				.append("\t1.支持音乐选择功能,断点续播功能,拖动滑块快进快退功能\n")
				.append("\t1.2.支持单曲循环,列表循环,随机播放模式\n")
				.append("\t1.3.支持自定义背景音乐\n")
				.append("\t1.4只要你将音乐文件放入软件目录下的-music_dir-目录即可,"
						+ "音乐格式仅支持mp3\n").append("\t1.5支持音乐标签霓虹灯效果\n")
				.append("\t2.支持自定义背景图片,并幻灯片播放,只需将自己的图片放入-img_dir-目录即可\n")
				.append("\t3.采用kde风格界面,界面精致美观\n")
				.append("\t4.支持富文本编辑以及插入图片文件\n")
				.append("\t5.采用二进制形式保存用户的日记文件,有效保证了安全性\n")
				.append("\t5.1.对用户密码采用SHA-1算法,有效保证安全性\n")
				.append("\t6.支持日志记录\n").append("\t7.下一版本将集成数据库,敬请期待\n")
				.append("\t8.支持启动闪屏\n").append("\t9.支持系统托盘\n")
				.append("\t10.点击窗口的x其实是最小化到系统托盘,您可以右击托盘图标选择关闭按钮\n")
				.append("\t11.添加水印签名\n").append("\t12.欢迎及时反馈BUG,及新想法");
	}

	private ArrayList<String> getList(ArrayList<String> list,
			String defaultFile, String select) {
		// this.list=list;
		if (list != null)
			list.clear();
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
						|| str.endsWith(".jpeg") || str.endsWith(".JPEG")
						|| str.endsWith(".png") || str.endsWith(".PNG")
						|| str.endsWith(".gif")) {
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
//		log.info(getList(listIMG, getDefaultIMG(), "picture").get(0));
//		log.info(getList(listIMG, getDefaultIMG(), "picture").get(1));
		return getList(listIMG, getDefaultIMG(), "picture");
	}

	void initFont() {
		for (String str : font_Name) {
			if (str.contains("宋体") || str.contains("方正") || str.contains("文泉")
					|| str.contains("仿宋") || str.contains("黑体")
					|| str.contains("楷体") || str.contains("微软雅黑"))
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
	 * @return the defaultMusic
	 */
	public String getDefaultMusic() {
		return defaultMusic;
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
	 * @param font_Familly_Name
	 *            the font_Familly_Name to set
	 */
	public void setFont_Familly_Name(ArrayList<String> font_Familly_Name) {
		this.font_Familly_Name = font_Familly_Name;
	}

	// 工厂方法
	public static Strings getInstance() {
		return resString;
	}

	/**
	 * @return the manual
	 */
	/*
	 * public static String getManual() { return manual; }
	 */
	/**
	 * @return the builder
	 */
	public String getAbout() {
		return builder.toString();
	}

}
