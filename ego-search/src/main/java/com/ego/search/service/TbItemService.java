package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

public interface TbItemService {
	/**
	 * 初始化
	 */
	void init() throws SolrServerException, IOException;
	/**
	 * 分页查询
	 * @param query 查询的内容
	 * @param page 第几页
	 * @param rows 每页多少数据
	 * @return
	 */
	Map<String,Object> selByQuery(String query,int page,int rows) throws SolrServerException, IOException;
	/**
	 * 新增
	 * @param map
	 * @param desc
	 * @return
	 */
	int add(Map<String,Object> map,String desc) throws SolrServerException, IOException;
}
