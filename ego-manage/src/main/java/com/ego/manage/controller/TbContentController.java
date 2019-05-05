package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 分页显示内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentServiceImpl.showContent(categoryId, page, rows);
	}
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult save(TbContent content) {
		EgoResult er = new EgoResult();
		int index = tbContentServiceImpl.save(content);
		if(index>0) {
			er.setStatus(200);
		}
		return er;
	}
	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult edit(TbContent content) {
		EgoResult er = new EgoResult();
		int index = tbContentServiceImpl.edit(content);
		if(index>0) {
			er.setStatus(200);
		}
		return er;
	}
	/**
	 * 删除内容
	 * @param id
	 * @return
	 */
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er = new EgoResult();
		try {
			int index = tbContentServiceImpl.delete(ids);
			if(index>0) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			er.setData(e.getMessage());
		}
		return er;
	}
}
