package com.cache.redissentinel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class SentinelConfig {

	@Value("${redis.sentinel.node.1.port}")
	private int node1;

	@Value("${redis.sentinel.node.2.port}")
	private int node2;

	@Value("${redis.host}")
	private String host;

	private static Logger log = LoggerFactory.getLogger(SentinelConfig.class);

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("master")
				.sentinel(host, node1).sentinel(host, node2);
		return new JedisConnectionFactory(sentinelConfig);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

}
