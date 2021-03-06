package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentService {
	/**
	 * 分页查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showContent(long categoryId,int page,int rows);
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	int save(TbContent content);
	
	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	int edit(TbContent content);
	/**
	 * 删除内容
	 * @param id
	 * @return
	 */
	int delete(String ids) throws Exception;
}
