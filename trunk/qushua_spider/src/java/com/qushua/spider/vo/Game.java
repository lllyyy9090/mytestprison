package com.qushua.spider.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Game {
	private int gameid;
	private short gametype;
	private String gametypename;
	private String pinyin = "";
	private String gamename = "";
	private String size = "";
	private String smallpic = "";
	private String pic = "";
	private String swfhost = "";
	private String swf = "";
	private String intro = "";
	private String tag = "";
	private String inputtime;
	private List<String> tags = new ArrayList<String>();
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public short getGametype() {
		return gametype;
	}
	public void setGametype(short gametype) {
		this.gametype = gametype;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	 
	public String getSwf() {
		return swf;
	}
	public void setSwf(String swf) {
		this.swf = swf;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	 
	 
	public String toString(){
		return this.gamename + " " +  this.size + " " + this.swf;
	}
	public String getSwfhost() {
		return swfhost;
	}
	public void setSwfhost(String swfhost) {
		this.swfhost = swfhost;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tags) {
		this.tag = tags;
	}
	public String getSmallpic() {
		return smallpic;
	}
	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}
	public String getInputtime() {
		return inputtime;
	}
	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}
	public List<String> getTags() {
		this.tags = new ArrayList<String>();
		String []tmp = this.getTag().split(" ");
		for (int i = 0; i < tmp.length; i++) {
			if(null == tmp[i] || tmp[i].trim().length() <=0){
				continue;
			}
			this.tags.add(tmp[i]);
		}
		return tags;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getGametypename() {
		return gametypename;
	}
	public void setGametypename(String gametypename) {
		this.gametypename = gametypename;
	}
	 
}
