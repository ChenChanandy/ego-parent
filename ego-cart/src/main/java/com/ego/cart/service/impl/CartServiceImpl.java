package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${passport.url}")
	private String passportUrl;
	@Value("${cart.key}")
	private String cartKey;
	
	@Override
	public void addCart(long id, int num,HttpServletRequest request) {
		//集合中存放所有购物车商品
		List<TbItemChild> list = new ArrayList<>();
		//设置redis中购物车的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);		
		String key = cartKey+((LinkedHashMap)er.getData()).get("username");
		
		//如果redis中存在key
		if(jedisDaoImpl.exists(key)) {
			String json = jedisDaoImpl.get(key);
			if(json!=null&&!json.equals("")) {
				list = JsonUtils.jsonToList(json, TbItemChild.class);
				for (TbItemChild tbItemChild : list) {
					if(tbItemChild.getId()==id) {
						//购物车存在该商品
						tbItemChild.setNum(tbItemChild.getNum()+num);
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
						return ;
					}
				}
			}
		}
		//购物车没有商品
		TbItem item = tbItemDubboServiceImpl.selById(id);
		TbItemChild child = new TbItemChild();
		child.setId(item.getId());
		child.setTitle(item.getTitle());
		child.setImages(item.getImage()==null||item.getImage().equals("")?new String[1]:item.getImage().split(","));
		child.setNum(num);
		child.setPrice(item.getPrice());
		list.add(child);
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
	}

	@Override
	public List<TbItemChild> showCart(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);		
		String key = cartKey+((LinkedHashMap)er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		return JsonUtils.jsonToList(json, TbItemChild.class);
	}

}
