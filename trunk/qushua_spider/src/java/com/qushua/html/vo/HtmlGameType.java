package com.qushua.html.vo;

import java.util.ArrayList;
import java.util.List;

public class HtmlGameType {
	private String typename;
	private String typeurl;
	private boolean selected = false;
	public static List<HtmlGameType> list = new ArrayList<HtmlGameType>();
	 
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypeurl() {
		return typeurl;
	}
	public void setTypeurl(String typeurl) {
		this.typeurl = typeurl;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	 
}
