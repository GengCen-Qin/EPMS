package com.manage.Pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qinsicheng
 * @Description 内容：总成绩类
 * @Date 16/12/2021 17:24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("report_card")
public class mainPoint {
    Integer stuId;
    Integer moralEdu;
    Integer learnEdu;
    Integer sportEdu;
    Integer additionEdu;
    String schoolYear;
    @TableField(exist = false)
    stu_message stuMessage;

    public mainPoint(Integer stuId, Integer moralEdu, Integer learnEdu, Integer sportEdu, Integer additionEdu, String schoolYear) {
        this.stuId = stuId;
        this.moralEdu = moralEdu;
        this.learnEdu = learnEdu;
        this.sportEdu = sportEdu;
        this.additionEdu = additionEdu;
        this.schoolYear = schoolYear;
    }
}
