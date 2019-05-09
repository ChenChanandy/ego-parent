package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	EgoResult login(TbUser user); 
}
