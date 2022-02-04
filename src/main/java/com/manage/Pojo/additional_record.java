package com.manage.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qinsicheng
 * @Description 内容：附加分记录类
 * @Date 16/12/2021 12:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class additional_record {
    Integer stuId;
    String projectName;
    Integer projectScore;
    String claName;
    String projectTime;
}
