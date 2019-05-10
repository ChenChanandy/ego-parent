package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbOrderServiceImpl implements TbOrderService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${cart.key}")
	private String cartKey;
	@Value("${passport.url}")
	private String passportUrl;
	
	@Override
	public List<TbItemChild> showOrder(List<Long> ids, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String result = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		String key = cartKey+((LinkedHashMap)er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		List<TbItemChild> listNew = new ArrayList<>();
		for (TbItemChild child : list) {
			for(Long id :ids) {
				if((long)child.getId()==(long)id) {
					//判断购买量是否大于库存
					TbItem tbItem = tbItemDubboServiceImpl.selById(id);
					if(tbItem.getNum()>=child.getNum()) {
						child.setEnough(true);
					}else {
						child.setEnough(false);
					}
					listNew.add(child);
				}
			}
		}
		return listNew;
	}

}
