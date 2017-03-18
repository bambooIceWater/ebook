package com.sunshine.ebook.common.response;

import java.util.ArrayList;
import com.github.pagehelper.Page;

/**
 * Created by LMG on 2017/3/17.
 */
public class ListResponse {
	
	private ArrayList<Object> list = new ArrayList<>();
	private long total;
	private int startPage;
	private int pageSize;
	private int totalPage;
	
	public void pushAll(Page<? extends Object> objects) {
		for(Object object : objects) {
			list.add(object);
		}
		this.setTotal(objects.getTotal());
		this.setStartPage(objects.getPageNum());
		this.setPageSize(objects.getPageSize());
		this.setTotalPage(objects.getPages());
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public ArrayList<Object> getList() {
		return list;
	}

}
