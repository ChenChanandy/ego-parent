package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemParamDubboService {
	/**
	 * 分页查询数据
	 * @param page
	 * @param rows
	 * @return 包含：当前显示的数据和总条数
	 */
	EasyUIDataGrid showPage(int page,int rows);
	/**
	 * 批量删除规格参数
	 * @param ids
	 * @return
	 */
	int delByIds(String ids) throws Exception;
}
