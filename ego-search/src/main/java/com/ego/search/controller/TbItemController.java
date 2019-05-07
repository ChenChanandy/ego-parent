package com.ego.search.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	/**
	 * 初始化
	 * @return
	 */
	@RequestMapping(value = "solr/init",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String init() {
		//初始化开始时间
		long start = System.currentTimeMillis();
		try {
			tbItemServiceImpl.init();
			//初始化结束时间
			long end = System.currentTimeMillis();
			return "初始化总时间："+(end-start)/1000+"秒";
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败！";
		}
	}
	
	/**
	 * 搜索功能
	 * @param model
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 * search.html伪静态页面，ajax中不能使用.html
	 */
	@RequestMapping("search.html")
	public String search(Model model,String q,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue = "12") int rows) {
		try {
			q = new String(q.getBytes("iso-8859-1"),"utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("query",q);
			model.addAttribute("itemList",map.get("itemList"));
			model.addAttribute("totalPages",map.get("totalPages"));
			model.addAttribute("page",page);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "search";		
	}
}
