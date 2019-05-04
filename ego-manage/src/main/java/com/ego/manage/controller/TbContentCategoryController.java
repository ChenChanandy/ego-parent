package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUiTree> showCategory(@RequestParam(defaultValue ="0") long id){
		return tbContentCategoryServiceImpl.showCategory(id);
	}
	/**
	 * 新增
	 * @param cate
	 * @return
	 */
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory cate) {
		return tbContentCategoryServiceImpl.create(cate);
	}
	
	/**
	 * 重命名
	 * @param cate
	 * @return
	 */
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory cate) {
		return tbContentCategoryServiceImpl.update(cate);
	} 
}
