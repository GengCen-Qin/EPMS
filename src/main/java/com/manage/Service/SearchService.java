package com.manage.Service;

import com.manage.Dao.exception_request_Dao;
import com.manage.Dao.stu_message_Dao;
import com.manage.Pojo.*;

import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：负责所有查询
 * @Date 14/12/2021 16:08
 */
public interface SearchService {
    //查找全校学生数量
    Integer AllStuNum();
    //通过学生号 查看学生密码
    stu_login getStuPasswordById(Integer id);
    //查询所有学生信息
    List<stu_message> getAllStu();
    //通过学生号查找学生信息
    stu_message getStuMessageById(Integer stuId);
    //查看综测加分的类别数量
    Integer AllClaNum();
    //查询综测加分分类信息
    List<Additional_Classification> getAllCla();
    //查询所有综测加分记录信息
    List<additional_record> getAllClaRecord();
    //查询所有主成绩记录信息
    List<mainPoint> getAllMainPoint();
    //查询所有的异常信息
    List<exception_request> getAllException();
    //通过学生id查找所有综测加分项目
    List<additional_record> getAllClaRecordById(Integer id);
    //通过附加分类名查找该类信息
    Additional_Classification getAddClaByClaName(String claName);


    //查询所有附加分信息
    List<mainPoint> addAllScore();
}
