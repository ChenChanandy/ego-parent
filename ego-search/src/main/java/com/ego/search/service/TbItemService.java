package com.ego.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

public interface TbItemService {
	/**
	 * 初始化
	 */
	void init() throws SolrServerException, IOException;
}
