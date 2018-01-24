package com.mmnttech.me.common.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmnttech.me.common.server.database.entity.AreaCode;
import com.mmnttech.me.common.server.database.mappers.AreaCodeMapper;

/**
 * @类名 AreaService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月24日 上午11:14:22
 * @版本 v1.0
 * 
 */
@Service("areaService")
public class AreaService {
	
	@Autowired
	private AreaCodeMapper areaCodeMapper;

	public AreaCode queryAreaCode(String recId) {
		return areaCodeMapper.selectByPrimaryKey(recId);
	}
	
}
