package com.mmnttech.me.common.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mmnttech.me.common.server.common.entity.QueryEntity;
import com.mmnttech.me.common.server.util.Validator;

/**
 * @类名 CommonService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月18日 下午3:17:16
 * @版本 v1.0
 * 
 */
@Service("commonService")
public class CommonService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> queryAreaCodeLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT area_code, area, level, rec_id FROM t_area_code WHERE 1 = 1");
		
		if(Validator.isNotBlank(queryEntity.getParentId())) {
			sql.append(" AND parent_area_id = ?");
			paramLst.add(queryEntity.getParentId());
		}
		sql.append(" ORDER BY area_code");
		
		return jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
	}
	
	public List<Map<String, Object>> queryIndustryCodeLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT industry_code, industry, rec_id FROM t_industry_code WHERE 1 = 1");
		
		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		
		sql.append(" ORDER BY industry_code LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		
		return jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
	}
	
	public List<Map<String, Object>> queryCategoryCodeLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cc.category_code, cc.category, cc.rec_id FROM (SELECT * FROM t_category_code)cc LEFT JOIN ");
		sql.append("(SELECT * FROM t_industry_code WHERE industry_code = ? ) ic ON cc.industry_id = ic.rec_id ");
		paramLst.add(queryEntity.getIndustryCode());
		
		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		
		sql.append(" ORDER BY cc.category_code LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		
		return jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
	}
}
