package com.manage.Pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @Author qinsicheng
 * @Description 内容：登录类
 * @Date 13/12/2021 10:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("student")
public class stu_login {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;  //这个是数据库的id
    Integer stuId;  //这个是学生号
    String password;  //密码
    //构造器
    public stu_login(String password) {
        this.password = password;
    }
    //登录
    public stu_login(Integer stuId, String password) {
        this.stuId = stuId;
        this.password = password;
    }
}
