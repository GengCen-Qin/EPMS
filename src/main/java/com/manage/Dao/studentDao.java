package com.manage.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manage.Pojo.stu_login;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author qinsicheng
 * @Description 内容：学生登录信息
 * @Date 13/12/2021 10:43
 */

@Mapper
public interface studentDao extends BaseMapper<stu_login> {
}

