package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;

@Service
public class TbOrderServiceImpl implements TbOrderService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
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
	
	/**
	 * 创建订单
	 */
	@Override
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {
		
		//订单表数据
		TbOrder order = new TbOrder();
		Date date = new Date();		
		order.setPayment(param.getPayment()); 	//实付金额		
		order.setPaymentType(param.getPaymentType());	//付款类型
		long id = IDUtils.genItemId();		
		order.setOrderId(id+"");	//订单id		
		order.setCreateTime(date);	//订单创建、更新时间
		order.setUpdateTime(date);		
		//获取请求Cookie中key为TT_TOKEN的值
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//使用HttpClient获得passport中用户信息
		String result = HttpClientUtil.doPost(passportUrl+token);
		//把json数据转换成EgoResult对象
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		//获取到EgoResult中的用户信息
		Map user = (LinkedHashMap)er.getData();		
		order.setUserId(Long.parseLong(user.get("id").toString()));	//订单的用户ID 		
		order.setBuyerNick(user.get("username").toString()); //买家昵称		
		order.setBuyerRate(0); //买家是否已评价，0未评价
		
		//订单-商品表
		for(TbOrderItem item : param.getOrderItems()) {			
			item.setId(IDUtils.genItemId()+""); //订单商品表id			
			item.setOrderId(id+""); //订单id
		}
		
		//收货人信息表
		TbOrderShipping shipping = new TbOrderShipping();
		shipping.setOrderId(id+"");
		shipping.setCreated(date);
		shipping.setUpdated(date);
		
		EgoResult erResult = new EgoResult();
		try {
			int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
			if(index>0) {
				erResult.setStatus(200);
				//删除购物车中被购买的商品
				String json = jedisDaoImpl.get(cartKey+user.get("username"));
				List<TbItemChild> listCart = JsonUtils.jsonToList(json, TbItemChild.class);
				List<TbItemChild> listNew = new ArrayList<>();
				for (TbItemChild child : listCart) {
					for(TbOrderItem item : param.getOrderItems()) {
						if(child.getId().longValue()==Long.parseLong(item.getItemId())) {
							listNew.add(child);
						}
					}
				}
				//删除
				for(TbItemChild mynew : listNew) {
					listCart.remove(mynew);
				}
				
				jedisDaoImpl.set(cartKey+user.get("username"), JsonUtils.objectToJson(listCart));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return erResult;
	}

}
