package com.ego.search.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubblService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubblService tbItemDubblServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient;
	
	@Override
	public void init() throws SolrServerException, IOException {
		//查询所有正常状态的商品
		List<TbItem> listItem = tbItemDubblServiceImpl.selAllByStatus((byte)1);
		for (TbItem item : listItem) {
			//商品对应的类目信息
			TbItemCat cat = tbItemCatDubboServiceImpl.selById(item.getCid());
			//商品对应的描述信息
			TbItemDesc desc = tbItemDescDubboServiceImpl.selByItemId(item.getId());
			
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name", cat.getName());
			doc.addField("item_desc", desc.getItemDesc());
			solrClient.add(doc);
		}
		solrClient.commit();
	}

}
