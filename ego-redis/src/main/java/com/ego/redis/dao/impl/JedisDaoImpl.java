package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.JedisCluster;

@Repository
public class JedisDaoImpl implements JedisDao{
	@Resource
	private JedisCluster jedisClient;
	
	@Override
	public Boolean exists(String key) {
		return jedisClient.exists(key);
	}

	@Override
	public Long del(String key) {
		return jedisClient.del(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisClient.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisClient.get(key);
	}

}
