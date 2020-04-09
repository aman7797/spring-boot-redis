package com.cache.rediscluster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cache.rediscluster.service.ItemService;
import com.cache.rediscluster.service.ProgrammerService;

import redis.clients.jedis.JedisCluster;

@RestController
public class Controller {

	@Autowired
	ProgrammerService programmerService;

	@Autowired
	ItemService itemService;
	
	@Autowired
	private JedisCluster getRedisCluster;

	private static Logger log = LoggerFactory.getLogger(Controller.class);

	@PostMapping("/progrmmer-string")
	public void setKeyInRedis(String key, String value) {
		getRedisCluster.set("wala", "oh wala");
	}

	public String getValueByKey(String key) {
		return (String) getRedisCluster.get(key);
	}
}
