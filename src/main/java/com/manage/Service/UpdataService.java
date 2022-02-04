package com.manage.Service;

import com.manage.Pojo.*;

/**
 * @Author qinsicheng
 * @Description 内容：负责数据更新
 * @Date 15/12/2021 12:15
 */
public interface UpdataService {
    void updateStuMessage(stu_message stuMessage);

    void deleteStu(Integer stu_id);

    void insertStuMessage(stu_message stu_message);

    void insertAllRule(Additional_Classification additionalClassification);

    void updataAllRule(Additional_Classification additionalClassification);

    void deleteCluRule(String name);

    void updateAllRecord(additional_record additionalRecord);

    void insertAllRecord(additional_record additionalRecord);

    void deleteAllRecord(String StuId, String projectName);

    void insertMainPoint(mainPoint mainPoint);

    void updateMainPoint(mainPoint mainPoint);

    void deleteMainPoint(Integer stu_id);
    //这里只是将该条信息的处理值修改为1，为已处理，数据库内并未删除
    void deleteException(Integer stu_id);

    //学生端 发送异常请求并接收
    void insertStuException(exception_request exceptionRequest);

}
