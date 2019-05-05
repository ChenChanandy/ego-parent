package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 分页查询内容管理
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selContentByPage(long categoryId,int page,int rows);
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	int insContent(TbContent content);
	
	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	int updContent(TbContent content);
	
	/**
	 * 批量删除内容
	 * @param id
	 * @return
	 */
	int delContentByIds(String ids) throws Exception;
	/**
	 * 查询出最近的前count个
	 * @param count
	 * @param isSort 
	 * @return
	 */
	List<TbContent> selByConut(int count,boolean isSort);
}
