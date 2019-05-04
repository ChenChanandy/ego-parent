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
}
