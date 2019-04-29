package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemService {
	
	/**
	 * 显示商品
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
}
