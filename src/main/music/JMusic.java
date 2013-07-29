package main.music;

import java.awt.Color;
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
import javax.swing.JProgressBar;
import javax.swing.JSlider;

import main.thread.MusicProgressCount;
import main.thread.ThreadBean;
import source.Strings;
import ui.JJLabel;

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
	private Thread thread = new Thread(playThread);
	private static boolean musicChaged = true;
	private static boolean select = false;
	private static String selectMusic = "";
	private JJLabel label;
	private Strings resStr = new Strings();
	private long playCount;
	private JLabel labelTime;
	private JProgressBar progress;
	private JSlider slider;
	private long playLen;

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

	void preparePlaying() {
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
		++playCount;// 用于统计播放次数,第一次播放需要启动线程,其他的只需修改statefield通知线程即可
		JMusic.state = true;
		musicNotOver = true;
		if (playCount == 1) {
			thread.start();
			// return;
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
		private Random random = new Random();
		private long tempLen = 0L;
		private long time;
		private MusicSettings labelAnim1=new MusicSettings(1000, 5, 5);
		private MusicSettings labelAnim2=new MusicSettings(1000, 15, 8);
		
		public PlayThread(JMusic music) {
			this.music = music;
			// thread=new Thread(this);
		}

		/** 用户拖动滑块变化响应音乐快进快慢 */
		void sliderChanged() {
			if (ThreadBean.isSpliderChange()) {
				System.out.println("changed");
				// ThreadBean.setSpliderChange(false);
				int ch = 0;
				if (ThreadBean.getTotalLen() == 0L) {
					// slider.setValue(progress.getValue());
				} else {
					if (slider.getValue() > progress.getValue()) {
						ch = slider.getValue() - progress.getValue();
						long skip = (long) (ch * (ThreadBean.getTotalLen() / 100.0));
						tempLen = playLen + skip;
					} else {
						ch = progress.getValue() - slider.getValue();
						long skip = (long) ((long) ch * (ThreadBean
								.getTotalLen() / 100));
						tempLen = playLen - skip;
						System.out.println("ch:" + ch + "   skip:" + skip
								+ "  temp:" + tempLen);
					}
					// progress.setValue(slider.getValue());
				}
			}
		}
		/** 用于设置播放时间 */
		void setTime() {
			time = (progress.getValue() + 1)
					* (long) (ThreadBean.getTimeLen() / 100.0 + 1);
			labelTime.setText("时间:" + time / 60 + ":" + time % 60 + " | "
					+ "总体时间: " + ThreadBean.getTimeLen() / 60 + ":"
					+ ThreadBean.getTimeLen() % 60);
			labelAnim1.setAnim();
		}

		// 设置正在播放的音乐文件名显示标签
		void setPlayName() {
			String name=musicList.get(i-1);
			name=name.substring(
					name.lastIndexOf(
							resStr.getPathSplit()) + 1,
					name.lastIndexOf("."));
/*			if(name.length()>12)
				name=name.substring(0, 11);*/
			labelAnim2.setPlayname(name);
		}

		/** 音乐播放主意逻辑 */
		public void run() {
			byte tempBuffer[] = new byte[320];// 存放临时音乐数据
			boolean breakFlag = false;// 用于多重循环跳出标志
			Thread threadCount_len = new Thread(new MusicProgressCount());// 音乐字节长度计算线程
			threadCount_len.start();
			labelAnim1.setLabel(labelTime);
			labelAnim2.setLabel(getLabel());
			while (true) {
				if (!music.musicNotOver) {
					if (music.i >= music.musicList.size()) {
						music.i = 0;
					}
				}
				// 如果是单曲循环
				if (ThreadBean.isSinglePlay()) {
					--i;
				}
				// 如果为随机播放
				if (ThreadBean.isRandomPlay()) {
					random.setSeed(System.currentTimeMillis());
					i = random.nextInt(musicList.size() - 1);
				}
				// 如果超出范围
				if (i >= musicList.size() || i < 0) {
					i = 0;
				}
				// 播放前的准备
				preparePlaying();
				try {
					// 相关线程通信设置
					ThreadBean.setLenOver(false);
					ThreadBean.setChangeMusic(true);
					ThreadBean.setAudioIns(musicFormatSelect());
					ThreadBean.setTimeLen(0L);
					ThreadBean.setTotalLen(0L);
					// 重置已播放长度
					playLen = 0L;
					// 重置进度
					slider.setValue(0);
					progress.setValue(0);
					progress.setString("请稍等,正在计算歌曲长度...");
					// Logger.getLogger("com.xby.log").info("totalLen: "+totalLen);
					ins = musicFormatSelect();
					i++;
					long startTime = System.currentTimeMillis();
					tempLen = 0L;// 用于暂存快进快退时导致的歌曲长度
					ArrayList<byte[]> arrayByte = new ArrayList<byte[]>();// 每次都重新生成已播放歌曲字节存放数组以便于快退时提取
					while (music.musicNotOver = ((cnt = music.ins.read(
							tempBuffer, 0, tempBuffer.length)) != -1)) {
						//用于重复调用设置歌曲名以产生动态效果
						setPlayName();
						playLen += (long) tempBuffer.length;
						// System.out.println(tempBuffer[14]+" "+tempBuffer[100]);
						byte[] tempp = new byte[320];
						System.arraycopy(tempBuffer, 0, tempp, 0,
								tempBuffer.length);
						arrayByte.add(tempp);
						// arrayByte.add(new ByteArrayInputStream(tempp));
						// k++;
						// System.out.println("K: "+k);
						// arrayByte.get(k++).read(tempp);
						// System.out.println(tempp[14]+"  "+tempp[100]);
						// arrayByte.get(k-2).read(tempp);
						// System.out.println(tempp[14]+"  "+tempp[100]);
						// 如果歌曲长度计算完毕
						if (ThreadBean.isLenOver()) {
							sliderChanged();
							// 若为快进
							if (playLen < tempLen) {
								ThreadBean.setSpliderChange(false);
								progress.setValue(slider.getValue());
								progress.setString("播放中: "
										+ progress.getValue() + "%");
								setTime();
								// time=progress.getValue()*(ThreadBean.getTimeLen()/100);
								continue;
								// 若为快退
							} else if (tempLen != 0L
									&& ThreadBean.isSpliderChange()) {
								progress.setValue(slider.getValue());
								ThreadBean.setSpliderChange(false);
								// System.out.println("进入退");
								long len = tempLen;
								long oldPlayLen = playLen;
								playLen = tempLen;
								// System.out.println("len: "+len+"n: "+len/320L+"jM:  "+(int)
								// (arrayByte.size() - len / 320L));
								tempLen = 0L;
								for (int j = 0; j < (int) (arrayByte.size() - len / 320L); j++) {
									System.out.println(arrayByte.size() + ":"
											+ (j + len / 320L));
									// System.exit(1);
									byte[] bytetmp = new byte[320];
									// arrayByte.get((j+(int)len/320)).read(bytetmp);
									bytetmp = arrayByte
											.get(j + (int) len / 320);
									byte[] tmp2 = new byte[320];
									System.arraycopy(bytetmp, 0, tmp2, 0,
											bytetmp.length);
									arrayByte.set(j + (int) len / 320, tmp2);
									// arrayByte.set(j+(int)len/320, new
									// ByteArrayInputStream(tmp2));
									System.out.println(bytetmp[17] + " "
											+ bytetmp[100]);
									// System.out.println(tempBuffer.length+":"+tempBuffer[319]);
									playLen += bytetmp.length;
									music.line
											.write(bytetmp, 0, bytetmp.length);
									int percent = (int) (playLen * 100.0 / ThreadBean
											.getTotalLen());
									progress.setValue(percent);
									// 若前面已产生一次快退,用户又拖动滑块,进行处理
									if (ThreadBean.isSpliderChange()) {
										sliderChanged();
										// 如果用户此次的拖动事件没有超出上次快退范围
										if (tempLen < oldPlayLen) {
											ThreadBean.setSpliderChange(false);
											len = tempLen;
											playLen = tempLen;
											tempLen = 0L;
										} else {
											break;
										}
									}
									slider.setValue(percent);
									progress.setString("播放中: " + percent + "%");
									setTime();
									if (select) {
										select = false;
										if (ThreadBean.isSinglePlay())
											i = i + 1;
										line.drain();
										line.stop();
										Logger.getLogger("com.xby.log").info(
												i + ":" + 138);
										breakFlag = true;
										break;
									}
									if (ThreadBean.isBackSong()) {
										ThreadBean.setBackSong(false);
										if (ThreadBean.isSinglePlay()) {
											i = i - 1;
										} else {
											i = i - 2;
										}
										breakFlag = true;
										break;
									}
									if (ThreadBean.isFowardSong()) {
										ThreadBean.setFowardSong(false);
										if (ThreadBean.isSinglePlay())
											i = i + 1;
										breakFlag = true;
										break;
									}
									while (!JMusic.state) {
										Thread.sleep(1000);
									}
								}
								if (breakFlag) {
									breakFlag = false;
									break;
								}
								continue;
								// continue;
							}
							if (ThreadBean.isLenOver()) {
								int percent = (int) (playLen * 100 / ThreadBean
										.getTotalLen());
								progress.setValue(percent);
								slider.setValue(percent);
								progress.setString("播放中: " + percent + "%");
							}
							// setTime();
						}
						if (select) {
							select = false;
							if (ThreadBean.isSinglePlay())
								i = i + 1;
							line.drain();
							line.stop();
							Logger.getLogger("com.xby.log").info(i + ":" + 138);
							break;
						}
						if (ThreadBean.isBackSong()) {
							ThreadBean.setBackSong(false);
							if (ThreadBean.isSinglePlay()) {
								i = i - 1;

							} else {
								i = i - 2;
							}
							break;
						}
						if (ThreadBean.isFowardSong()) {
							ThreadBean.setFowardSong(false);
							if (ThreadBean.isSinglePlay())
								i = i + 1;
							break;
						}
						while (!JMusic.state) {
							// System.out.println(JMusic.state);
							// line.drain();
							// line.stop();
							Thread.sleep(1000);

							// Thread.currentThread().wait();

							/*
							 * Logger.getLogger("com.xby.log").info( "状态" +
							 * Thread.currentThread().getState());
							 */
							// break;
						}
						if (cnt > 0) {
							// 写入缓存数据
							music.line.write(tempBuffer, 0, cnt);
							// System.out.println(cnt+":"+tempBuffer[319]);
							long now = (System.currentTimeMillis() - startTime) / 1000;
							// long min = now / 60;
							long sec = now % 60;
							if (sec > 0) {
								if (ThreadBean.getTimeLen() == 0L
										&& ThreadBean.getTotalLen() != 0L)
									ThreadBean
											.setTimeLen((now + 1)
													* (ThreadBean.getTotalLen() / playLen));
							}
							setTime();
						} else {
							break;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// TODO Auto-generated catch block
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArithmeticException e) {
					e.printStackTrace();
					continue;
				}
				setMusicChaged(true);
			}
		}

	}

	AudioInputStream musicFormatSelect() {
		try {
			Logger.getLogger("com.xby.log").info(i + ":" + 126);
			if (isDefaultMusic(musicList.get(i))) {
				ins = AudioSystem.getAudioInputStream(JMusic.class
						.getResource(defaultMusic));
			} else {
				ins = AudioSystem
						.getAudioInputStream(new File(musicList.get(i)));
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AudioSystem.getAudioInputStream(audioFormat, ins);

	}

	public int getPlayingMusicIndex() {
		return i;
	}

	public void setPlayingMusicIndex(int index) {
		i = index;
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
		i = indexS;
		// return index;
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
	public void setLabel(JJLabel label) {
		this.label = label;
	}

	public void setLabelTime(JLabel label1) {
		labelTime = label1;
	}

	/**
	 * @return the progress
	 */
	public JProgressBar getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}

	/**
	 * @return the slider
	 */
	public JSlider getSlider() {
		return slider;
	}

	/**
	 * @param slider
	 *            the slider to set
	 */
	public void setSlider(JSlider slider) {
		this.slider = slider;
	}

	// Block等待临时数据被输出为空
	// sourceDataLine.drain();
	// sourceDataLine.close();
}
