package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubblService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
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
	/**
	 * 分页查询
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException{
		SolrQuery params = new SolrQuery();
		//设置分页条件
		params.setStart(rows*(page-1));
		params.setRows(rows);
		//设置条件
		params.setQuery("item_keywords:"+query);
		//设置高亮
		params.setHighlight(true); //开启高亮
		params.addHighlightField("item_title"); //需要高亮的类型
		params.setHighlightSimplePre("<span style='color:red'>");//高亮标签头
		params.setHighlightSimplePost("</span>");//高亮标签尾
		
		QueryResponse response = solrClient.query(params);
		List<TbItemChild> listChild = new ArrayList<>();
		//未高亮内容
		SolrDocumentList listSolr = response.getResults();
		//高亮内容
		Map<String, Map<String, List<String>>> hh = response.getHighlighting();
		for (SolrDocument doc : listSolr) {
			TbItemChild child = new TbItemChild();
			child.setId(Long.parseLong(doc.getFieldValue("id").toString()));		
			
			//Map<String, List<String>> map = hh.get(doc.getFieldValue("id"));
			//map.get("item_title");
			//等同于下面代码
			List<String> list = hh.get(doc.getFieldValue("id")).get("item_title");
			
			if(list!=null&&list.size()>0) {
				child.setTitle(list.get(0));
			}else {
				child.setTitle(doc.getFieldValue("item_title").toString());
			}
			
			child.setPrice((Long)doc.getFieldValue("item_price"));
			Object image = doc.getFieldValue("item_image");
			child.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));
			child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
			
			listChild.add(child);
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("itemList",listChild);
		resultMap.put("totalPages",listSolr.getNumFound()%rows==0?listSolr.getNumFound()/rows:listSolr.getNumFound()/rows+1);
		return resultMap;
	}

}
