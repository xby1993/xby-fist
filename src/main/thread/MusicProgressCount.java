package main.thread;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

/**
 * 用于计算播放歌曲的长度
 * 
 * @author xby64
 * 
 */
public class MusicProgressCount implements Runnable {
	private int READ_LEND = 2048;
	private long len = 0L;
	private AudioInputStream audioIns;
	private byte[] tempBuffer = new byte[READ_LEND];
	private byte[] playedBytes;
	int cnt = 0;

	public void run() {
		while (true) {
			try {
				audioIns = ThreadBean.getAudioIns();
				while (audioIns == null) {
					Thread.sleep(10);
					audioIns=ThreadBean.getAudioIns();
//					break;
				}
//				ThreadBean.setLenOver(false);
				while ((cnt = audioIns.read(tempBuffer, 0, tempBuffer.length)) != -1) {
					if (ThreadBean.isChangeMusic()) {
						ThreadBean.setChangeMusic(false);
						len = -1L;
						break;
					}
					len += (long) tempBuffer.length;
				}
				if (len > 0L) {
					ThreadBean.setTotalLen(len);
					playedBytes=new byte[(int)len];
					ThreadBean.setLenOver(true);
				}
				while (!ThreadBean.isChangeMusic() && len != -1) {
					Thread.sleep(1000);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
