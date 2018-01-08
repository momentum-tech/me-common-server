package com.mmnttech.ma.merchant.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @类名 RedisConfiguration
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月20日 下午4:42:03
 * @版本 v1.0
 * 
 */

@Configuration
public class RedisConfiguration {
	
	@Bean(name = "jedis.pool")
	@Autowired
	public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
			@Value("${jedis.pool.host}") String host, @Value("${jedis.pool.port}") int port) {
		JedisPool jedisPool = new JedisPool(config, host, port, 5*1000, "laoniaoche0001");
		return jedisPool;
	}

	@Bean(name = "jedis.pool.config")
	public JedisPoolConfig jedisPoolConfig(@Value("${jedis.pool.config.maxTotal}") int maxTotal,
			@Value("${jedis.pool.config.maxIdle}") int maxIdle,
			@Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}

}
