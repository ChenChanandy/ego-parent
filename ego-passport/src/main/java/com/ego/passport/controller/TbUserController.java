package com.ego.passport.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserController {
	@Resource
	private TbUserService tbUserServiceImpl;
	
	/**
	 * 显示登录页面
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader("Referer") String url,Model model) {
		model.addAttribute("redirect", url);
		return "login";
	}
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser user) {
		return tbUserServiceImpl.login(user);	
	}
	
	
	/**
	 * 显示注册页面
	 * @return
	 */
	@RequestMapping("user/showRegister")
	public String showRegister() {
		return "register";
	}
}
