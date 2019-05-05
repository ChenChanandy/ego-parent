package com.ego.manage.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	
	@Override
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentDubboServiceImpl.selContentByPage(categoryId, page, rows);
	}

	@Override
	public int save(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		return tbContentDubboServiceImpl.insContent(content);
	}

	@Override
	public int edit(TbContent content) {
		Date date = new Date();
		content.setUpdated(date);
		return tbContentDubboServiceImpl.updContent(content);
	}

	@Override
	public int delete(String ids) throws Exception {
		return tbContentDubboServiceImpl.delContentByIds(ids);
	}

}