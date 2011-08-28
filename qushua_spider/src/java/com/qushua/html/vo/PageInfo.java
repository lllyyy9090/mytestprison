package com.qushua.html.vo;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {
	private int allnum;
	private int currentpage = 1;
	private int pagesize = 66;
	private int pagenum;
	private List<Integer> pages = new ArrayList<Integer>();

	public int getAllnum() {
		return allnum;
	}

	public void setAllnum(int allnum) {
		this.allnum = allnum;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPagenum() {
		this.pagenum = this.allnum / this.pagesize + 1;
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public List<Integer> getPages() {
		this.pages = new ArrayList<Integer>();
		int bidx = this.currentpage - 5 <= 0 ? 1 : this.currentpage - 5;
		int eidx = this.getPagenum() - this.currentpage < 4 ? this.getPagenum() : this.currentpage + 4;
		for (int i = bidx; i <= eidx; i++) {
			this.pages.add(i);
		}
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

}
