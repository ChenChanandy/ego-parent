package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbContentService {
	/**
	 * 分页查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showContent(long categoryId,int page,int rows);
}
