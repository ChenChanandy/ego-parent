package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.TbItemChild;

public interface TbOrderService {
	/**
	 * 确认订单信息包含的商品
	 * @param id
	 * @param request
	 * @return
	 */
	List<TbItemChild> showOrder(List<Long> ids,HttpServletRequest request);
}
