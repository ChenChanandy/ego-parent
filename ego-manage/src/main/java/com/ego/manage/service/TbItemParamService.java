package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
	/**
	 * 分页显示商品规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showPage(int page,int rows);
	
	/**
	 * 批量删除商品规格参数
	 * @param id
	 * @return
	 */
	EgoResult delByIds(String ids);
	
	/**
	 * 根据类目id查询模板
	 * @param catId
	 * @return
	 */
	EgoResult showParam(long catId);
	
	/**
	 * 新增模板
	 * @param param
	 * @return
	 */
	EgoResult save(TbItemParam param);
}
