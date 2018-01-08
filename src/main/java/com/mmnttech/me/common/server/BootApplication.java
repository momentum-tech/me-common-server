package com.mmnttech.me.common.server;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BootApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));// 用户名
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));// 密码
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(20);
		dataSource.setMinIdle(0);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setPoolPreparedStatements(false);
		return dataSource;
	}
	
}
