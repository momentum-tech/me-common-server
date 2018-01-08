package com.mmnttech.me.common.server.database.mappers;

import com.mmnttech.me.common.server.database.entity.SvcUser;
import com.mmnttech.me.common.server.database.entity.SvcUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SvcUserMapper {
    int countByExample(SvcUserExample example);

    int deleteByExample(SvcUserExample example);

    int deleteByPrimaryKey(String userId);

    int insert(SvcUser record);

    int insertSelective(SvcUser record);

    List<SvcUser> selectByExample(SvcUserExample example);

    SvcUser selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") SvcUser record, @Param("example") SvcUserExample example);

    int updateByExample(@Param("record") SvcUser record, @Param("example") SvcUserExample example);

    int updateByPrimaryKeySelective(SvcUser record);

    int updateByPrimaryKey(SvcUser record);
}