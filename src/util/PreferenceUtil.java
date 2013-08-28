package util;

import java.util.prefs.Preferences;

public class PreferenceUtil {
	private static Preferences root = Preferences.userRoot();
	public static String getPasswd(){
		return root.node("com.xby.info").get("passwd", " ");
	}
	public static void setPasswd(String passwd){
		 root.node("com.xby.info").put("passwd", passwd);
	}
	public static String passFindBack(){
		return root.node("com.xby.info").get("passwdFindInfo", " ");
	}
	public static void setPassFindBack(String info){
		root.node("com.xby.info").put("passwdFindInfo", info);
	}
	public static Boolean isFirstUse(){
		return root.node("com.xby.info").getBoolean("isFirstUse", true);
	}
	public static void setFirstUse(boolean b){
		root.node("com.xby.info").putBoolean("isFirstUse", b);
	}
}
