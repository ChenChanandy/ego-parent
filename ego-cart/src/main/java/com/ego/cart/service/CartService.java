package com.ego.cart.service;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
	/**
	 * 加入购物车
	 * @param id
	 * @param num
	 */
	void addCart(long id,int num,HttpServletRequest request);
}
