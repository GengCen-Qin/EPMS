package com.manage.Pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qinsicheng
 * @Description 内容：学生总信息
 * @Date 14/12/2021 12:29
 */
@NoArgsConstructor
@AllArgsConstructor
@Data  //默认包含get set 方法
public class stu_message {
    @TableField(exist = false)
    Integer totalPoint;
    String stuName;
    String stuGrade;
    String stuMajor;
    Integer classNum;
    Integer stuId;
    @TableField(exist = false)
    public stu_login stu_login;

    public stu_message(String stuName, String stuGrade, String stuMajor, Integer stuId) {
        this.stuName = stuName;
        this.stuGrade = stuGrade;
        this.stuMajor = stuMajor;
        this.stuId = stuId;
    }

    public stu_message(String stuName,Integer classNum,  String stuMajor, String stuGrade, Integer stuId) {
        this.stuName = stuName;
        this.classNum = classNum;
        this.stuGrade = stuGrade;
        this.stuMajor = stuMajor;
        this.stuId = stuId;
    }

    public stu_message(String stuName, String stuGrade, String stuMajor, Integer stuId, String password) {
        this.stuName = stuName;
        this.stuGrade = stuGrade;
        this.stuMajor = stuMajor;
        this.stuId = stuId;
        com.manage.Pojo.stu_login stu_login = new stu_login(stuId, password);
        this.stu_login = stu_login;
    }


}
