package com.manage.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manage.Pojo.stu_login;
import com.manage.Pojo.stu_message;

/**
 * @Author qinsicheng
 * @Description 内容：负责登录
 * @Date 13/12/2021 11:26
 */
public interface loginService {
    //验证登录信息
    public stu_login testLogin(Integer stu_id);

}
