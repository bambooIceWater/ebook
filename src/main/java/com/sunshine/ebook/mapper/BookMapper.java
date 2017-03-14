package com.sunshine.ebook.mapper;

import java.util.HashMap;
import java.util.Map;

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

}
