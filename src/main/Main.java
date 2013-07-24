package main;

import java.awt.EventQueue;

import source.Strings;
import ui.Policy;
import ui.SetLookAndFeel;

public class Main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SetLookAndFeel.setDecorated();
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new Strings().initlog();
				new Policy();
			}
		});
	}

}
  