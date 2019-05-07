package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemDubblService {
	/**
	 * 商品分页查询
	 * @param page 第几页
	 * @param rows 每页显示的数据数量
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	
	/**
	 * 根据id修改商品状态
	 * @param tbItem
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	
	/**
	 * 新增包含商品表和商品描述表及规格参数
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	int insItemDesc(TbItem tbItem,TbItemDesc desc,TbItemParamItem paramItem) throws Exception;
	/**
	 * 通过状态查询全部可用数据
	 * @param status
	 * @return
	 */
	List<TbItem> selAllByStatus(byte status);
}
