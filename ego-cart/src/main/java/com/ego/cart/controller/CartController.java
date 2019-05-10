package com.ego.cart.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.cart.service.CartService;

@Controller
public class CartController {
	@Resource
	private CartService cartServiceImpl;
	/**
	 * 添加购物车
	 * @param id
	 * @return
	 */
	@RequestMapping("cart/add/{id}.html")
	public String addCart(@PathVariable long id,int num,HttpServletRequest req) {
		cartServiceImpl.addCart(id, num, req);
		return "cartSuccess";
	}
}
