package com.manage.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manage.Pojo.exception_request;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author qinsicheng
 * @Description 内容：异常请求
 * @Date 16/12/2021 19:45
 */
@Mapper
public interface exception_request_Dao extends BaseMapper<exception_request> {
    void UpdateExcept(Integer id);
}
