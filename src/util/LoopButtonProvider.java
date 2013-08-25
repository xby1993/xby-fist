package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ui.UIUtils;

public class LoopButtonProvider implements ButtonProvider{
	
	private final JButton loop;
	private final JButton random;
	private final JButton single;
	public LoopButtonProvider(){
		loop=UIUtils.createMusicJButton("loop","列表循环");
		random=UIUtils.createMusicJButton("random", "随机播放");
		single=UIUtils.createMusicJButton("single", "单曲循环");
	}
	public JButton getButton(String cmd){
		switch (cmd) {
		case "列表循环":
			return loop;
		case "随机播放":
			return random;
		case "单曲循环":
			return single;
		default:
			break;
		}
		return null;
	}

	
}
