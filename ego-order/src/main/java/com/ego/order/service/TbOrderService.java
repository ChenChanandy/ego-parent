package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderService {
	/**
	 * 确认订单信息包含的商品
	 * @param id
	 * @param request
	 * @return
	 */
	List<TbItemChild> showOrder(List<Long> ids,HttpServletRequest request);
	/**
	 * 创建订单
	 * @param param
	 * @param request
	 * @return
	 */
	EgoResult create(MyOrderParam param, HttpServletRequest request);
}
