package com.manage.Service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.manage.Dao.*;
import com.manage.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 15/12/2021 12:16
 */
@Service
public class UpdataService implements com.manage.Service.UpdataService {
    @Autowired
    stu_message_Dao stuMessageDao;
    @Autowired
    studentDao studentDao;
    @Autowired
    Additional_Classification_Dao additionalClassificationDao;
    @Autowired
    Additional_Record_Dao additionalRecordDao;
    @Autowired
    mainPoint_Dao mainPointDao;
    @Autowired
    exception_request_Dao exceptionRequestDao;


    @Override
    public void updateStuMessage(stu_message stuMessage) {
        stuMessageDao.UpdataStuMessage(stuMessage);
        stuMessageDao.UpdataStuLogin(stuMessage.stu_login);
        System.out.println("我需要找的：" + stuMessage.stu_login);
    }

    @Override
    public void deleteStu(Integer stu_id) {
        UpdateWrapper<stu_login> stu_id1 = new UpdateWrapper<stu_login>().eq("stu_id", stu_id);
        studentDao.delete(stu_id1);
        UpdateWrapper<stu_message> stu_id2 = new UpdateWrapper<stu_message>().eq("stu_id", stu_id);
        stuMessageDao.delete(stu_id2);
    }

    @Override
    public void insertStuMessage(stu_message stu_message) {
        System.out.println("达到");
        stuMessageDao.insert(stu_message);
        studentDao.insert(stu_message.getStu_login());
    }

    @Override
    public void insertAllRule(Additional_Classification additionalClassification) {
        additionalClassificationDao.insert(additionalClassification);
    }

    @Override
    public void updataAllRule(Additional_Classification additionalClassification) {
        additionalClassificationDao.
                update(additionalClassification, new UpdateWrapper<Additional_Classification>().eq("cla_name", additionalClassification.getClaName()));
    }

    @Override
    public void deleteCluRule(String name) {
        additionalClassificationDao.delete(new UpdateWrapper<Additional_Classification>().eq("cla_name", name));
    }

    @Override
    public void updateAllRecord(additional_record additionalRecord) {
        additionalRecordDao.update(additionalRecord,
                new UpdateWrapper<additional_record>().
                        eq("stu_id", additionalRecord.getStuId()).eq("project_name", additionalRecord.getProjectName()));
    }

    @Override
    public void insertAllRecord(additional_record additionalRecord) {
        additionalRecordDao.insert(additionalRecord);
    }

    @Override
    public void deleteAllRecord(String StuId, String projectName) {
        additionalRecordDao.delete(new UpdateWrapper<additional_record>().eq("stu_id", StuId).eq("project_name", projectName));
    }

    @Override
    public void insertMainPoint(mainPoint mainPoint) {
        mainPointDao.insert(mainPoint);
    }

    @Override
    public void updateMainPoint(mainPoint mainPoint) {
        mainPointDao.update(mainPoint,
                new UpdateWrapper<mainPoint>().eq("stu_id",mainPoint.getStuId()));
    }

    @Override
    public void deleteMainPoint(Integer stu_id) {
        mainPointDao.delete(new UpdateWrapper<mainPoint>().eq("stu_id",stu_id));
    }

    @Override
    public void deleteException(Integer stu_id) {
        exceptionRequestDao.UpdateExcept(stu_id);
    }

    @Override
    public void insertStuException(exception_request exceptionRequest) {
        exceptionRequestDao.insert(exceptionRequest);
    }
}
