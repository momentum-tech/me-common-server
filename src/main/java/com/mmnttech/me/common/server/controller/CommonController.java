package com.mmnttech.me.common.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mmnttech.me.common.server.common.entity.RtnMessage;

@Controller
public class CommonController {

	private Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private JedisPool jedisPool;

	@ResponseBody
	@RequestMapping(value = "updateEasepayBaseInfo")
	public RtnMessage updateEasepayBaseInfo(HttpServletRequest request, HttpServletResponse response) {
		RtnMessage rtnMsg = new RtnMessage();
		
		Jedis jedis = null;
		try {
			String value = request.getParameter("value");
			String key = request.getParameter("key");
			
			if(value != null && key != null) {
				jedis = jedisPool.getResource();
				
				jedis.set(key, value);
			}
		}catch (Exception e) {
			logger.error("updateEasepayBaseInfo异常", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("修改基础数据错误：请稍后再试");
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		return rtnMsg;
	}
	
	
	
}
