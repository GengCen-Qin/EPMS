package com.manage.Pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 16/12/2021 09:44
 */
@AllArgsConstructor
@Data
@TableName("additional_classification")
//附加分分類
public class Additional_Classification {
    String claName;
    //附加分的加分详情 规则
    String claCeiling;
}
