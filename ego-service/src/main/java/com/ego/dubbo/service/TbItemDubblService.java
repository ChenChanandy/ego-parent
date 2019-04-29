package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemDubblService {
	/**
	 * 商品分页查询
	 * @param page 第几页
	 * @param rows 每页显示的数据数量
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
}
