package com.ego.passport.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Service
public class TbUserServiceImpl implements TbUserService{
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	
	@Override
	public EgoResult login(TbUser user) {
		EgoResult er = new EgoResult();
		TbUser userSelect = tbUserDubboServiceImpl.selByUser(user);
		if(userSelect!=null) {
			er.setStatus(200);
		}else {
			er.setMsg("用户名或密码错误！");
		}
		return er;
	}

}
