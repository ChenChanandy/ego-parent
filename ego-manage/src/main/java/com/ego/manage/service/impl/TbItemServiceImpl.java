package com.ego.manage.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubblService;
import com.ego.manage.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	
	@Reference
	private TbItemDubblService tbItemDubblServiceImpl;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubblServiceImpl.show(page, rows);
	}

}
