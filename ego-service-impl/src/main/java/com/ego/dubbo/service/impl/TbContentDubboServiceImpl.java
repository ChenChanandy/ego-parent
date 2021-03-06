package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	@Resource
	private TbContentMapper tbContentMapper;
	/**
	 * 分页查询
	 */
	@Override
	public EasyUIDataGrid selContentByPage(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		if(categoryId!=0) {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}
	
	/**
	 *新增内容
	 */
	@Override
	public int insContent(TbContent content) {
		return tbContentMapper.insertSelective(content);
	}
	
	/**
	 * 修改内容
	 */
	@Override
	public int updContent(TbContent content) {		
		return tbContentMapper.updateByPrimaryKeySelective(content);
	}

	@Override
	public int delContentByIds(String ids) throws Exception {
		int index = 0;
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			index += tbContentMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if(index==idsStr.length) {
			return 1;
		}else {
			throw new Exception("该数据可能已经不存在");
		}
	}

	@Override
	public List<TbContent> selByConut(int count, boolean isSort) {
		TbContentExample example = new TbContentExample();
		//排序
		if(isSort) {
			//asc升序(默认), desc降序
			example.setOrderByClause("updated desc");
		}
		if(count!=0) {
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		}else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}

}
