package com.manage.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manage.Dao.studentDao;
import com.manage.Pojo.stu_login;

import com.manage.Service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 13/12/2021 11:31
 */
@Service
public class LoginServiceImpl implements loginService {
    @Autowired
    studentDao studentDao;
    @Override
    public stu_login testLogin(Integer stu_id) {
        stu_login stu = studentDao.selectOne(new QueryWrapper<stu_login>().eq("stu_id", stu_id));
        if (stu == null) {
            return null;
        } else {
            return stu;
        }
    }
}
