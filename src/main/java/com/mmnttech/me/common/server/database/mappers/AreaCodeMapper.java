package com.mmnttech.me.common.server.database.mappers;

import com.mmnttech.me.common.server.database.entity.AreaCode;
import com.mmnttech.me.common.server.database.entity.AreaCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaCodeMapper {
    int countByExample(AreaCodeExample example);

    int deleteByExample(AreaCodeExample example);

    int deleteByPrimaryKey(String recId);

    int insert(AreaCode record);

    int insertSelective(AreaCode record);

    List<AreaCode> selectByExample(AreaCodeExample example);

    AreaCode selectByPrimaryKey(String recId);

    int updateByExampleSelective(@Param("record") AreaCode record, @Param("example") AreaCodeExample example);

    int updateByExample(@Param("record") AreaCode record, @Param("example") AreaCodeExample example);

    int updateByPrimaryKeySelective(AreaCode record);

    int updateByPrimaryKey(AreaCode record);
}