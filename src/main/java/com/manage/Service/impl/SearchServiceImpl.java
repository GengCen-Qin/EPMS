package com.manage.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.manage.Dao.*;
import com.manage.Pojo.*;
import com.manage.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 14/12/2021 16:49
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    studentDao studentDao;
    @Autowired
    stu_message_Dao stuMessageDao;
    @Autowired
    com.manage.Dao.Additional_Classification_Dao additionalClassificationDao;
    @Autowired
    Additional_Record_Dao additionalRecordDao;
    @Autowired
    mainPoint_Dao mainPointDao;
    @Autowired
    exception_request_Dao exceptionRequestDao;

    @Override
    public List<stu_message> getAllStu() {
        return stuMessageDao.getStuMessage();
    }

    @Override
    public List<Additional_Classification> getAllCla() {
        return  additionalClassificationDao.selectList(null);
    }

    @Override
    public List<additional_record> getAllClaRecord() {
        return additionalRecordDao.selectList(null);
    }

    @Override
    public List<mainPoint> getAllMainPoint() {
        return mainPointDao.selectList(null);
    }

    @Override
    public List<exception_request> getAllException() {
        //ne  不等于
        return exceptionRequestDao.selectList(new QueryWrapper<exception_request>().ne("state",1));
    }

    @Override
    public stu_message getStuMessageById(Integer stuId) {
        return stuMessageDao.selectOne(new QueryWrapper<stu_message>().eq("stu_id", stuId));
    }

    @Override
    public List<additional_record> getAllClaRecordById(Integer id) {
        //向additionalRecord Dao 数据访问层请求数据
        return additionalRecordDao.
                //QueryWrapper  查询条件
                selectList(new QueryWrapper<additional_record>().eq("stu_id",id));
    }

    @Override
    public Integer AllStuNum() {
        return stuMessageDao.selectCount(null);
    }

    @Override
    public Integer AllClaNum() {
        return additionalClassificationDao.selectCount(null);
    }

    @Override
    public stu_login getStuPasswordById(Integer id) {
        return studentDao.selectOne(new QueryWrapper<stu_login>().eq("stu_id",id));
    }

    @Override
    public Additional_Classification getAddClaByClaName(String claName) {
        return additionalClassificationDao.selectOne(new QueryWrapper<Additional_Classification>().eq("cla_name",claName));
    }

    @Override
    public List<mainPoint> addAllScore() {
        //获取所有人的成绩单
        List<mainPoint> allMainPoint = getAllMainPoint();
        int size = allMainPoint.size();
        //遍历成绩单
        for (int i = size-1; i >= 0 ; i--) {
            stu_message stuMessageById = getStuMessageById(allMainPoint.get(i).getStuId());

            //新建一个Hash表，存储展示的数据
            HashMap<String, Integer> hashMap = new HashMap<>();
            //新建一个记录类别分的Hash表
            HashMap<String, Integer> ClaHashMap = new HashMap<>();

            int singularPoint = 0;
            //通过学生id，查询所有的获取荣誉
            List<additional_record> allClaRecordById =getAllClaRecordById(allMainPoint.get(i).getStuId());
            //递归荣誉，放入到hash表中去除同名项目，取最高分
            for (additional_record additional_record : allClaRecordById) {
                //获取该对象所属类别的详细信息
                Additional_Classification addClaByClaName = getAddClaByClaName(additional_record.getClaName());
                //如果是只能添加一项的  特殊处理
                if (addClaByClaName.getClaCeiling().equals("只能添加一项")) {
                    //如果新添加的比较大 则替换
                    if (singularPoint<additional_record.getProjectScore()) {
                        //赋值
                        singularPoint = additional_record.getProjectScore();
                        //放入
                        ClaHashMap.put(additional_record.getClaName(),singularPoint);
                        //挑出该层
                        continue;
                    } else {
                        continue;
                    }
                }
                //如果新加入的项目已经存在 判断分数是否大于新加入的同名项目，如果新的分数高，则进行替换
                //如果当前项目 hash表中已存在  并且  数据还小于新的同名项目 则不添加新的数据
                if (hashMap.get(additional_record.getProjectName()) != null
                        &&
                        additional_record.getProjectScore() < hashMap.get(additional_record.getProjectName())) {
                } else {
                    //当hash中不存在该项目时候，或者存在但是我新来得比你大
                    hashMap.put(additional_record.getProjectName(), additional_record.getProjectScore());
                    //添加到 类别 表中 = 原先数据 + 现在数据
                    Integer integer = ClaHashMap.get(addClaByClaName.getClaName());
                    ClaHashMap.put(addClaByClaName.getClaName(), (integer==null?0:integer) +
                            additional_record.getProjectScore());
                }
            }
            int allPoint = 0;
            //新加部分
            int addEdu1 = 0;
            Set<Map.Entry<String, Integer>> entries = ClaHashMap.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                Additional_Classification addClaByClaName = getAddClaByClaName(entry.getKey());
                String claCeiling = addClaByClaName.getClaCeiling();
                try {
                    int claScore = Integer.parseInt(claCeiling);
                    if (entry.getValue()>claScore) {
                        addEdu1 = addEdu1+=claScore;
                    } else {
                        addEdu1 = addEdu1+=entry.getValue();
                    }
                } catch (NumberFormatException e) {
                    //如果捕捉到异常  说明规则不是数字  只能添加一项
                    if (claCeiling.equals("只能添加一项")) {
                        addEdu1 += entry.getValue();
                    }
                }
            }
            //限制附加分为十分
            if (addEdu1>10) {
                addEdu1 = 10;
            }
            allMainPoint.get(i).setAdditionEdu(addEdu1);
            allMainPoint.get(i).setStuMessage(stuMessageById);

        }

        return allMainPoint;
    }
}
