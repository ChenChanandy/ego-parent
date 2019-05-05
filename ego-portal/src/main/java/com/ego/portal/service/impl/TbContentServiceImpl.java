package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigpic.key}")
	private String key;
	
	@Override
	public String showBigPic() {
		//先判断redis中是否存在,存在就从redis里取
		if(jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")) {
				return value;
			}
		}
		
		//如果redis不存在就从mysql中取，并放入redis中
		//取最近前6个大广告
		List<TbContent> list = tbContentDubboServiceImpl.selByConut(6, true);
		List<Map<String,Object>> listResult = new ArrayList<>();
		for (TbContent content : list) {
			Map<String,Object> map = new HashMap<>();
			map.put("srcB", content.getPic2());
			map.put("height", 240);
			map.put("alt", "图片加载失败");
			map.put("width", 670);
			map.put("src", content.getPic());
			map.put("widthB", 550);
			map.put("href", content.getUrl());
			map.put("heightB", 240);
			
			listResult.add(map);
		}
		//把数据转换成json格式
		String listJson = JsonUtils.objectToJson(listResult);
		//把数据放入到redis中
		jedisDaoImpl.set(key, listJson);
		
		return listJson;
	}

}
