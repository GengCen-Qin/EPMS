package com.manage.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manage.Pojo.stu_login;
import com.manage.Pojo.stu_message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：学生总信息
 * @Date 14/12/2021 15:05
 */
@Mapper
public interface stu_message_Dao extends BaseMapper<stu_message> {
    //查询学生总信息（基本信息+账户密码）
    List<stu_message> getStuMessage();
    stu_message getStuById(Integer id);
    void UpdataStuMessage(stu_message stu_message);
    void UpdataStuLogin(stu_login stuLogin);
}
