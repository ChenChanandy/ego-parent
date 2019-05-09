package com.ego.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TbUserController {
	@RequestMapping("user/showLogin")
	public String login() {
		return "login";
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("user/showRegister")
	public String register() {
		return "register";
	}
}
