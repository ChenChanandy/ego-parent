package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	/**
	 * 根据父id查询所有的子类目
	 * @param id
	 * @return
	 */
	List<TbContentCategory> selByPid(long id);
	/**
	 * 新增内容分类
	 * @param cate
	 * @return
	 */
	int insTbContentCategory(TbContentCategory cate);
	
	/**
	 * 通过id修改is_parent的值
	 * @param cate
	 * @return
	 */
	int updIsParent(TbContentCategory cate);
}
