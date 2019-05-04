package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{
	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
	
	@Override
	public List<EasyUiTree> showCategory(long id) {
		List<EasyUiTree> listTree = new ArrayList<>(); 
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
		for (TbContentCategory category : list) {
			EasyUiTree tree = new EasyUiTree();
			tree.setId(category.getId());
			tree.setText(category.getName());
			tree.setState(category.getIsParent()?"closed":"open");
			listTree.add(tree);
		}
		return listTree;
	}
	
	/**
	 * 新增类目
	 */
	@Override
	public EgoResult create(TbContentCategory cate) {
		EgoResult er = new EgoResult();
		//判断当前节点名称是否存在
		List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
		for (TbContentCategory child : children) {
			if(child.getName().equals(cate.getName())) {
				er.setData("该分类名已存在");
				return er;
			}
		}
		
		Date date = new Date();
		cate.setCreated(date);
		cate.setUpdated(date);
		cate.setStatus(1);
		cate.setIsParent(false);
		cate.setSortOrder(1);
		long id = IDUtils.genItemId();
		cate.setId(id);
		int index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cate);
		if(index>0) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(cate.getParentId());
			parent.setIsParent(true);
			parent.setCreated(date);
			parent.setUpdated(date);
			tbContentCategoryDubboServiceImpl.updIsParent(parent);
		}
		er.setStatus(200);
		Map<String,Long> map = new HashMap<>();
		map.put("id", id);
		er.setData(map);
		return er;
	}

}
