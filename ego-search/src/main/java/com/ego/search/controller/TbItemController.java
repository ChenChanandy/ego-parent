package com.ego.search.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
