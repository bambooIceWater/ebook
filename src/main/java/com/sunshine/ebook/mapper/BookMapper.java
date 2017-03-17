package com.sunshine.ebook.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.sunshine.ebook.entity.Book;

public interface BookMapper {
	
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
	void saveBookinfo(Map<String, Object> map);
	
	/**
	 * 更新电子书信息
	 * @param map
	 */
	void updateBookinfo(HashMap<String, Object> map);

	/**
	 * 查询电子书列表
	 * @param map
	 * @return
	 */
	Page<Book> queryBookList(HashMap<String, Object> map);

}
