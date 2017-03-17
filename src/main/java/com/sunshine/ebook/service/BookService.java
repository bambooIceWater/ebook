package com.sunshine.ebook.service;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.Page;
import com.sunshine.ebook.entity.Book;

public interface BookService {
	
	/**
	 * 根据条件获取电子书信息
	 * @param map
	 * @return
	 */
	Book getBookinfoByCondition(HashMap<String, Object> map);
	
	/**
	 * 保存电子书信息
	 * @param map
	 */
	boolean saveBookinfo(HashMap<String, Object> map);
	
	/**
	 * 更新电子书信息
	 * @param map
	 */
	void updateBookinfo(HashMap<String, Object> map);

	/**
	 * 查询电子书列表
	 * @param bookName
	 * @param author
	 * @param categoryid
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	Page<Book> queryBookList(String bookName, String author, Integer categoryid, int startPage, int pageSize);

}
