package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;

import source.Strings;

public class JMusic {
	private static int i = 0;
	private Strings strRes = new Strings();
	private ArrayList<String> musicList = strRes.getListMusic();
	private static boolean state = false;
	private SourceDataLine line;
	private AudioInputStream ins;
	private Line.Info info = new Line.Info(Clip.class);
	private String defaultMusic = musicList.get(0);
	private boolean musicNotOver = true;
	private AudioFormat audioFormat;
	private DataLine.Info dataLineInfo;
	private JMusic.PlayThread playThread = new JMusic.PlayThread(this);
	private Thread thread=new Thread(playThread);
	private static boolean musicChaged = true;
	private static boolean select = false;
	private static String selectMusic = "";
	private JLabel label;
	private Strings resStr = new Strings();
	private String[] color={"red","blue","green","yellow"};
	private long playCount;
	private JLabel labelTime;
	public JMusic() {

		/*
		 * try { line = (SourceDataLine)AudioSystem.getLine(info); } catch
		 * (LineUnavailableException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } clip = (Clip) line; try { ins =
		 * AudioSystem.getAudioInputStream(new File(musicList.get(0))); } catch
		 * (UnsupportedAudioFileException | IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}
	void preparePlaying(){
		try {
			if (isDefaultMusic(defaultMusic)) {
				musicList.clear();
				musicList.add(defaultMusic);
				ins = AudioSystem.getAudioInputStream(JMusic.class
						.getResource(defaultMusic));
			} else {
				ins = AudioSystem
						.getAudioInputStream(new File(musicList.get(i)));
			}
			audioFormat = ins.getFormat();
			audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					audioFormat.getSampleRate(), 16, audioFormat.getChannels(),
					audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
					false);
			dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,
					AudioSystem.NOT_SPECIFIED);

			line = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			line.open(audioFormat);
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		line.start();
	}

	boolean isDefaultMusic(String str) {
		if (str.equals(strRes.getDefaultMusic()))
			return true;
		return false;
	}

	public void musicOpen() {
		++playCount;//用于统计播放次数,第一次播放需要启动线程,其他的只需修改statefield通知线程即可
		JMusic.state = true;
		musicNotOver = true;
		if(playCount==1){
			thread.start();
//			return;
		}
		/*
		 * new Thread(new Runnable() { public void run() { while (true) { if
		 * (!musicNotOver) { playThread.getThread().start(); } } } }).start();
		 */

	}

	public void musicShut() {
		JMusic.state = false;

	}

	class PlayThread implements Runnable {

		private int cnt = 0;
		private JMusic music;

		public PlayThread(JMusic music) {
			this.music = music;
//			thread=new Thread(this);
		}

		public void run() {
			byte tempBuffer[] = new byte[320];
			int colorindex=0;
			while (true) {
				if (!music.musicNotOver) {
					if (music.i >= music.musicList.size()) {
						music.i = 0;
					}
				}
				preparePlaying();
				if (getLabel() != null)
					
					if(colorindex>3){
						colorindex=0;
					}
					getLabel().setText(new HTMLString().getLabelString(musicList.get(i).substring(
							musicList.get(i).lastIndexOf(
									resStr.getPathSplit()) + 1,
							musicList.get(i).lastIndexOf(".")),color[colorindex++] )
							);
				try {
					Logger.getLogger("com.xby.log").info(i + ":" + 126);
					if (isDefaultMusic(musicList.get(i))) {
						ins = AudioSystem.getAudioInputStream(JMusic.class
								.getResource(defaultMusic));
					} else {
						music.ins = AudioSystem.getAudioInputStream(new File(
								music.musicList.get(i++)));
					}
					music.ins = AudioSystem.getAudioInputStream(
							music.audioFormat, music.ins);
					long startTime=System.currentTimeMillis();
					while (music.musicNotOver = ((cnt = music.ins.read(
							tempBuffer, 0, tempBuffer.length)) != -1)) {
						if (select) {
							select = false;
							line.drain();
							line.stop();
							Logger.getLogger("com.xby.log").info(i + ":" + 138);
							break;
						}
						while (!JMusic.state) {
//							System.out.println(JMusic.state);
//							line.drain();
//							line.stop();
								Thread.sleep(1000);
							
//								Thread.currentThread().wait();

							Logger.getLogger("com.xby.log").info("状态"+Thread.currentThread().getState());
//							break;
						}
						if (cnt > 0) {
							// 写入缓存数据
							long now=(System.currentTimeMillis()-startTime)/1000;
							long min=now/60;
							long sec=now%60;
							labelTime.setText("时间:"+min+":"+sec);
							music.line.write(tempBuffer, 0, cnt);
						} else {
							break;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// TODO Auto-generated catch block
				}catch(IndexOutOfBoundsException e){
					e.printStackTrace();
				} catch (UnsupportedAudioFileException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setMusicChaged(true);
			}
		}

			
			

	}

	public int getPlayingMusicIndex() {
		return i;
	}

	public String getPlayingMusic() {
		return musicList.get(i);
	}

	/*
	 * try { clip.open(ins); clip.start(); } catch (LineUnavailableException |
	 * IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 */

	/**
	 * @return the musicChaged
	 */
	public boolean isMusicChaged() {
		return musicChaged;
	}

	/**
	 * @param musicChaged
	 *            the musicChaged to set
	 */
	public void setMusicChaged(boolean musicChaged) {
		this.musicChaged = musicChaged;
	}

	/**
	 * @return the select
	 */
	public boolean isSelect() {
		return select;
	}

	/**
	 * @param select
	 *            the select to set
	 */
	public void setSelect(boolean select) {
		JMusic.select = select;
	}

	/**
	 * @return the selectMusic
	 */
	public String getSelectMusic() {
		return selectMusic;
	}

	public void setSelectMusic(int indexS) {
		i=indexS;
//		return index;
	}
	

	/**
	 * @return the label
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void setLabelTime(JLabel label1){
		labelTime = label1;
	}
	// Block等待临时数据被输出为空
	// sourceDataLine.drain();
	// sourceDataLine.close();
}
