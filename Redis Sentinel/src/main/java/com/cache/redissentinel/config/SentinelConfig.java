package com.cache.redissentinel.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import com.cache.redissentinel.RedisSentinelApplication;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class SentinelConfig {
	
	@Value("${redis.pool.max-active}")
	private int maxActive;

	@Value("${redis.pool.max-idle}")
	private int maxIdle;

	@Value("${redis.jedis.pool.min-idle}")
	private int minIdle;
	
	@Autowired
    private Environment env;

	private static Logger log = LoggerFactory.getLogger(SentinelConfig.class);
	
	/*// Construct the RedisSentinelNodes by getting the properties.
	RedisSentinelNode sentinelNodes() {
		List<RedisSentinelNode> nodes = new ArrayList<RedisSentinelNode>();

		for (int i=1; i<=2;i++) {
			String hostProp = "redis.sentinel.node."+i+".host";
			String portProp = "redis.sentinel.node."+i+".port";

			RedisSentinelNode redisSentinelNode = new RedisSentinelNode(
					env.getProperty(hostProp),
					env.getProperty(portProp, Integer.class));

			nodes.add(redisSentinelNode);
		}

		RedisSentinelNodes redisSentinelNodes = new RedisSentinelNodes(nodes);
		return redisSentinelNodes;
	}

	// Construct the RedisSentinelConfiguration using all the nodes in RedisSentinelNodes
	RedisSentinelConfiguration sentinelConfiguration () {
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
				.master("redis-cluster");

		List<RedisSentinelNode> nodes = sentinelNodes().getRedisSentinelNodes();
		for (RedisSentinelNode sn : nodes ) {
			sentinelConfig.sentinel(sn.getHost(),sn.getPort());
		}

		return sentinelConfig;
	}
	
	RedisNode sentinelNodes() {
		List<RedisSentinelNode> nodes = new ArrayList<RedisSentinelNode>();

		for (int i=1; i<=2;i++) {
			String hostProp = "redis.sentinel.node."+i+".host";
			String portProp = "redis.sentinel.node."+i+".port";

			RedisSentinelNode redisSentinelNode = new RedisSentinelNode(
					env.getProperty(hostProp),
					env.getProperty(portProp, Integer.class));

			nodes.add(redisSentinelNode);
		}

		RedisSentinelNodes redisSentinelNodes = new RedisSentinelNodes(nodes);
		return redisSentinelNodes;
	}
	
	@Bean
	RedisConnectionFactory jedisConnectionFactory() {

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfiguration());

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisConnectionFactory.setUsePool(true);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}*/
	
	Config config = new Config();
    config.useSentinelServers()
          .addSentinelAddress("redis://127.0.0.1:6379")
          .setMasterName("myMaster");

    RedissonClient redisson = Redisson.create(config);

    // perform operations

    // implements java.util.concurrent.ConcurrentMap
    RMap<String, String> map = redisson.getMap("simpleMap");
}
