package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;

public interface TbContentCategoryService {
	/**
	 * 查询所有类目转化成easyuitree的属性要求
	 * @param id
	 * @return
	 */
	List<EasyUiTree> showCategory(long id);
}
