package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response); 
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	EgoResult getUserInfoByToken(String token);
}
