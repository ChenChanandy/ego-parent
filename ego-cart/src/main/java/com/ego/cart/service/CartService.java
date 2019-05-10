package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.TbItemChild;

public interface CartService {
	/**
	 * 加入购物车
	 * @param id
	 * @param num
	 */
	void addCart(long id,int num,HttpServletRequest request);
	/**
	 * 显示购物车
	 * @param request
	 * @return
	 */
	List<TbItemChild> showCart(HttpServletRequest request);
}
