package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.container.page.PageHandler;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubblService;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubblService {
	@Resource
	private TbItemMapper tbItemMapper;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		PageHelper.startPage(page, rows);
		//查询全部
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		
		//分页代码
		//设置分页条件
		
		PageInfo<TbItem> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		
		return datagrid;
	}

	@Override
	public int updItemStatus(TbItem tbItem) {
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}

}
