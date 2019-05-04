package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {
	/**
	 * 查询所有类目转化成easyuitree的属性要求
	 * @param id
	 * @return
	 */
	List<EasyUiTree> showCategory(long id);
	
	/**
	 * 新增内容分类类目
	 * @param cate
	 * @return
	 */
	EgoResult create(TbContentCategory cate);
	
	/**
	 * 类目重命名
	 * @param id
	 * @return
	 */
	EgoResult update(TbContentCategory cate);
	/**
	 * 删除类目
	 * @param cate
	 * @return
	 */
	EgoResult delete(TbContentCategory cate);
}
