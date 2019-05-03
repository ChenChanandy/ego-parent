package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper TbItemParamMapper;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		//先设置分页条件
		PageHelper.startPage(page, rows);
		//设置查询的 SQL 语句
		//XXXXExample() 设置了什么,相当于在 sql 中 where 从句中添加条件
		//如果表中有一个或一个以上的列是text类型. 生成的方法xxxxxxxxWithBlobs()
		//如果使用 xxxxWithBlobs() 查询结果中包含带有 text 类的列,如果没有使用 withblobs() 方法不带有 text 类型
		List<TbItemParam> list = TbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//根据程序员自己编写的 SQL 语句结合分页插件产生最终结果,封装到 PageInfo
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		//设置返回结果
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}

}