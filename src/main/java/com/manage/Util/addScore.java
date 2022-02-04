package com.manage.Util;

import com.manage.Pojo.*;
import com.manage.Service.SearchService;
import com.manage.Service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 29/12/2021 21:31
 */
@Controller
public class addScore {

    SearchService searchService = new SearchServiceImpl();
    public void tst() {
        System.out.println(searchService);
    }

    public  List<mainPoint> addAllScore() {
        //获取所有人的成绩单
        List<mainPoint> allMainPoint = searchService.getAllMainPoint();
        int size = allMainPoint.size();
        //遍历成绩单
        for (int i = size-1; i >= 0 ; i--) {
            stu_message stuMessageById = searchService.getStuMessageById(allMainPoint.get(i).getStuId());

            //新建一个Hash表，存储展示的数据
            HashMap<String, Integer> hashMap = new HashMap<>();
            //新建一个记录类别分的Hash表
            HashMap<String, Integer> ClaHashMap = new HashMap<>();

            int singularPoint = 0;
            //通过学生id，查询所有的获取荣誉
            List<additional_record> allClaRecordById = searchService.getAllClaRecordById(allMainPoint.get(i).getStuId());
            //递归荣誉，放入到hash表中去除同名项目，取最高分
            for (additional_record additional_record : allClaRecordById) {
                //获取该对象所属类别的详细信息
                Additional_Classification addClaByClaName = searchService.getAddClaByClaName(additional_record.getClaName());
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
                Additional_Classification addClaByClaName = searchService.getAddClaByClaName(entry.getKey());
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
            System.out.println(addEdu1);
            allMainPoint.get(i).setAdditionEdu(addEdu1);
            allMainPoint.get(i).setStuMessage(stuMessageById);

        }

        return allMainPoint;
    }
}
