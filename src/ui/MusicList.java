package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import main.JMusic;
import source.Strings;

public class MusicList {
	private int i = 0;
	private ArrayList<String> list = new Strings().getListMusic();
	private String selectMusic = "";
	private JList<String> musicList;
	private Vector<String> listStr = new Vector<String>(list.size());
	private JScrollPane scroll;
	private Strings resStr = new Strings();
	private String split = resStr.getPathSplit();

	public MusicList() {
		initList();
		Logger.getLogger("com.xby.log").info("list--------------\n"+list+"\n"+"listStr---------------\n"+listStr);

	}

	private void initList() {
		for (String str : list) {
			str = str.substring(str.lastIndexOf(split) + 1,
					str.lastIndexOf("."));
			listStr.add(str);
		}
		scroll = new JScrollPane();
		musicList = new JList<String>();
		musicList.setFont(new Font("微软雅黑", Font.BOLD, 14));
		musicList.setBackground(new Color(100, 200, 250));
		musicList.setFixedCellWidth(200);
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setListData(getListStr());
		scroll.setViewportView(musicList);
		/*
		 * musicList.addListSelectionListener(new ListSelectionListener() {
		 * 
		 * @Override public void valueChanged(ListSelectionEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * @SuppressWarnings("unchecked") JList<String>
		 * list=(JList<String>)e.getSource();
		 * list.setSelectionForeground(Color.GREEN); selectMusic =
		 * list.getSelectedValue();
		 * Logger.getLogger("com.xby.log").info(selectMusic);
		 * Logger.getLogger("com.xby.log").info(i+""); JMusic music = new
		 * JMusic(); music.setSelectMusic(selectMusic); music.setSelect(true); }
		 * });
		 */
		musicList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					@SuppressWarnings("unchecked")
					JList<String> list = (JList<String>) e.getSource();
					list.setSelectionForeground(Color.GREEN);
					selectMusic = list.getSelectedValue();
					Logger.getLogger("com.xby.log").info(selectMusic);
					JMusic music = new JMusic();
					music.setSelectMusic(getSelectMusicIndex());
					music.setSelect(true);
					// music.musicOpen();
				}
			}
		});
	}

	public JScrollPane getMusicList() {
		return scroll;
	}

	/**
	 * @return the selectMusic
	 */
	public JList getList() {
		return musicList;
	}

	public String getSelectMusic() {
		return selectMusic;
	}

	/**
	 * @param selectMusic
	 *            the selectMusic to set
	 */
	public void setSelectMusic(String selectMusic) {
		this.selectMusic = selectMusic;
	}

	/**
	 * @return the listStr
	 */
	public Vector<String> getListStr() {
		return listStr;
	}

	public int getSelectMusicIndex() {
		return listStr.indexOf(selectMusic);
	}

	/**
	 * @param listStr
	 *            the listStr to set
	 */
	public void setListStr(Vector<String> listStr) {
		this.listStr = listStr;
	}
	public void setLabelTime(JLabel label1){
		JLabel labelTime=label1;
	}
}
