package main;

import javax.swing.SwingUtilities;

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
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Strings().initlog();
			}
		});
		new Policy();
	}

}
  