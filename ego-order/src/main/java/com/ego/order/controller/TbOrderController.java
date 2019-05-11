package com.ego.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.commons.pojo.EgoResult;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;

@Controller
public class TbOrderController {
	@Resource
	private TbOrderService tbOrderServiceImpl;
	/**
	 * 显示订单确认页面
	 * @param ids
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("order/order-cart.html")
	public String showOrder(@RequestParam("id") List<Long> ids, Model model,HttpServletRequest request) {
		model.addAttribute("cartList", tbOrderServiceImpl.showOrder(ids, request));
		return "order-cart";
	}
	
	/**
	 * 创建订单
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("order/create.html")
	public String createOrder(MyOrderParam param, HttpServletRequest request) {
		EgoResult er = tbOrderServiceImpl.create(param, request);
		if(er.getStatus()==200) {
			return "my-orders";
		}else {
			request.setAttribute("message", "订单创建失败！");
			return "error/exception";
		}		
	}
}
