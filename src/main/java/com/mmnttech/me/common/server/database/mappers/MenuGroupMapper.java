package com.mmnttech.me.common.server.database.mappers;

import com.mmnttech.me.common.server.database.entity.MenuGroup;
import com.mmnttech.me.common.server.database.entity.MenuGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuGroupMapper {
    int countByExample(MenuGroupExample example);

    int deleteByExample(MenuGroupExample example);

    int deleteByPrimaryKey(String recId);

    int insert(MenuGroup record);

    int insertSelective(MenuGroup record);

    List<MenuGroup> selectByExample(MenuGroupExample example);

    MenuGroup selectByPrimaryKey(String recId);

    int updateByExampleSelective(@Param("record") MenuGroup record, @Param("example") MenuGroupExample example);

    int updateByExample(@Param("record") MenuGroup record, @Param("example") MenuGroupExample example);

    int updateByPrimaryKeySelective(MenuGroup record);

    int updateByPrimaryKey(MenuGroup record);
}