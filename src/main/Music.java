package main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class Music {
	private URL cb;// 音乐文件路颈
	private AudioClip aau;// 音频剪辑播放

	public Music(){
		File file = new File("src/source/Music/清晨.wav");
		try {
			cb = file.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aau = Applet.newAudioClip(cb);
		}
	public void musicOpen() {
		try {
		  	
			aau.play();// 循环播放 aau.play() 单曲 aau.stop()停止播放
						// //aau.loop();
		} catch (Exception eee) {
			eee.printStackTrace();
		}
	}
	public void musicShut(){
		aau.stop();
	}
	
}
