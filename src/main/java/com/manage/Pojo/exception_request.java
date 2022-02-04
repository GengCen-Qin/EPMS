package com.manage.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qinsicheng
 * @Description 内容：异常请求
 * @Date 16/12/2021 19:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class exception_request {
    //学生号
    Integer stuId ;
    //异常请求的具体原因
    String reason;
    //状态码  0代表未处理  1代表已经处理
    Integer state = 0;

    public exception_request(Integer stuId) {
        this.stuId = stuId;
    }

    public exception_request(Integer stuId, String reason) {
        this.stuId = stuId;
        this.reason = reason;
    }
}
