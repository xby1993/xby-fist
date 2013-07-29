package main.thread;

import javax.sound.sampled.AudioInputStream;

/**
 * use for thread communication
 * @author xby64
 *
 */
public class ThreadBean {
	/** 用于控制单曲循环*/
	private static boolean singlePlay=false;
	/** 用于控制JMusic的上一首歌曲*/
	private static boolean backSong=false;
	/** 用于JMusic的下一首歌曲*/
	private static boolean fowardSong=false;
	/** 用于控制随机播放*/
	private static boolean randomPlay=false;
	/**用于计算歌曲长度 */
	private static long totalLen=0L;
	/**说明歌曲长度是否计算完毕 */
	private static boolean lenOver=false;
	/**用于说明是否更换了歌曲 */
	private static boolean changeMusic=false;
	/**储存用于歌曲长度计算的流 */
	private static AudioInputStream audioIns;
	/** 储存用于确定滑块是否变化*/
	private static boolean spliderChange=false;
	/** 用于存放已经播放的歌曲字节*/
	private static byte[] playedBytes;
	/**歌曲时间长度储存 */
	private static long timeLen=0L;
	
	/**
	 * @return the singlePlay
	 */
	public static boolean isSinglePlay() {
		return singlePlay;
	}

	/**
	 * @param singlePlay the singlePlay to set
	 */
	public static void setSinglePlay(boolean singlePlay) {
		ThreadBean.singlePlay = singlePlay;
	}

	/**
	 * @return the backSong
	 */
	public static boolean isBackSong() {
		return backSong;
	}

	/**
	 * @param backSong the backSong to set
	 */
	public static void setBackSong(boolean backSong) {
		ThreadBean.backSong = backSong;
	}

	/**
	 * @return the fowardSong
	 */
	public static boolean isFowardSong() {
		return fowardSong;
	}

	/**
	 * @param fowardSong the fowardSong to set
	 */
	public static void setFowardSong(boolean fowardSong) {
		ThreadBean.fowardSong = fowardSong;
	}

	/**
	 * @return the randomPlay
	 */
	public static boolean isRandomPlay() {
		return randomPlay;
	}

	/**
	 * @param randomPlay the randomPlay to set
	 */
	public static void setRandomPlay(boolean randomPlay) {
		ThreadBean.randomPlay = randomPlay;
	}

	/**
	 * @return the totalLen
	 */
	public static long getTotalLen() {
		return totalLen;
	}

	/**
	 * @param totalLen the totalLen to set
	 */
	public static void setTotalLen(long totalLen) {
		ThreadBean.totalLen = totalLen;
	}

	/**
	 * @return the lenOver
	 */
	public static boolean isLenOver() {
		return lenOver;
	}

	/**
	 * @param lenOver the lenOver to set
	 */
	public static void setLenOver(boolean lenOver) {
		ThreadBean.lenOver = lenOver;
	}

	/**
	 * @return the changeMusic
	 */
	public static boolean isChangeMusic() {
		return changeMusic;
	}

	/**
	 * @param changeMusic the changeMusic to set
	 */
	public static void setChangeMusic(boolean changeMusic) {
		ThreadBean.changeMusic = changeMusic;
	}

	/**
	 * @return the audioIns
	 */
	public static AudioInputStream getAudioIns() {
		return audioIns;
	}

	/**
	 * @param audioIns the audioIns to set
	 */
	public static void setAudioIns(AudioInputStream audioIns) {
		ThreadBean.audioIns = audioIns;
	}

	/**
	 * @return the timeLen
	 */
	public static long getTimeLen() {
		return timeLen;
	}

	/**
	 * @param timeLen the timeLen to set
	 */
	public static void setTimeLen(long timeLen) {
		ThreadBean.timeLen = timeLen;
	}

	/**
	 * @return the spliderChange
	 */
	public static boolean isSpliderChange() {
		return spliderChange;
	}

	/**
	 * @param spliderChange the spliderChange to set
	 */
	public static void setSpliderChange(boolean spliderChange) {
		ThreadBean.spliderChange = spliderChange;
	}

	/**
	 * @return the playedBytes
	 */
	public static byte[] getPlayedBytes() {
		return playedBytes;
	}

	/**
	 * @param playedBytes the playedBytes to set
	 */
	public static void setPlayedBytes(byte[] playedBytes) {
		ThreadBean.playedBytes = playedBytes;
	}

}
