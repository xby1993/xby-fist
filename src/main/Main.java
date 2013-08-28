package main;

import java.awt.EventQueue;

import source.Strings;
import ui.Login;
import ui.Register;
import util.PreferenceUtil;

public class Main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		SetLookAndFeel.setDecorated();
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Strings.getInstance().initlog();
				if(PreferenceUtil.isFirstUse())
					new Register().setTitle("欢迎新用户使用本软件，注册界面");
				else
					new Login().setTitle("欢迎老用户登陆");
			}
		});
	}

}
  