package com.qushua.html.vo;

public class GameTypeTag {
	private int seqid;
	private int gametype;
	private String gametypename;
	private String pinyin;
	private String tag;

	public int getSeqid() {
		return seqid;
	}

	public void setSeqid(int seqid) {
		this.seqid = seqid;
	}

	public int getGametype() {
		return gametype;
	}

	public void setGametype(int gametype) {
		this.gametype = gametype;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getGametypename() {
		return gametypename;
	}

	public void setGametypename(String gametypename) {
		this.gametypename = gametypename;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
