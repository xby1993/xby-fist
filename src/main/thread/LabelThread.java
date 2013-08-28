package main.thread;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;

import main.music.JMusic;
import main.music.MusicList;

public class LabelThread implements Runnable{
	private JLabel label;
	private JMusic music=JMusic.getJMusic();
	private MusicList musicList=new MusicList();
	public LabelThread(JLabel label){
		this.label=label;
	}
	public void run(){
		while(true){
				int index=music.getPlayingMusicIndex();
				if(label.getText().equals(musicList.getListStr().get(index)))
					continue;
				label.setText(musicList.getListStr().get(index));
				JList list=musicList.getList();
				list.setSelectedIndex(index);
				list.setSelectionForeground(Color.GREEN);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}



