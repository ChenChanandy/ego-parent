package com.ego.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
	@RequestMapping("cart/add/{id}.html")
	public String addCart(@PathVariable long id) {
		return "cartSuccess";
	}
}
