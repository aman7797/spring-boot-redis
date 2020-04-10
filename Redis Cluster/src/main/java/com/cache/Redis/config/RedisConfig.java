package com.cache.Redis.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.jedis.pool.max-total}")
	private int maxTotal;

	@Value("${redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${redis.jedis.pool.min-idle}")
	private int minIdle;

	@Bean
	public JedisCluster getRedisCluster(){
		Set<HostAndPort> jedisClusterNode = new HashSet<>();
		jedisClusterNode.add(new HostAndPort(host, port));
		JedisPoolConfig cfg = new JedisPoolConfig();
		cfg.setMaxTotal(maxTotal);
		cfg.setMaxIdle(maxIdle);
		cfg.setMaxWaitMillis(10000);
		cfg.setTestOnBorrow(true); 
		JedisCluster jc = new JedisCluster(jedisClusterNode, 10000, 1, cfg);
		return jc;
	}
}


