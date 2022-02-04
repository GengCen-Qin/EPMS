package com.manage.Service.extraPointImpl;

import com.manage.Pojo.additional_record;
import com.manage.Pojo.mainPoint;
import com.manage.Service.SearchService;
import com.manage.Service.extraPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：学术科技 三分上限
 * @Date 22/12/2021 11:42
 */
public class academic implements extraPoint {
    @Autowired
    SearchService searchService;
    @Override
    public Integer rule(Integer stuId) {
        //新建一个Hash表，存储展示的数据
        HashMap<String, Integer> hashMap = new HashMap<>();
        //通过学生id，查询所有的获取荣誉
        List<additional_record> allClaRecordById = searchService.getAllClaRecordById(stuId);
        //递归荣誉，放入到hash表中去除重复项，取最高分
        for (additional_record additional_record : allClaRecordById) {
            //如果新加入的项目已经存在 判断分数是否大于新加入的同名项目，如果新的分数高，则进行替换
            if (hashMap.get(additional_record.getProjectName()) != null
                    &&
                    additional_record.getProjectScore() < hashMap.get(additional_record.getProjectName())) {
            } else {
                hashMap.put(additional_record.getProjectName(), additional_record.getProjectScore());
            }
        }
        Integer addEdu= 0;
        Collection<Integer> values = hashMap.values();
        for (Integer value : values) {
            addEdu+=value;
        }
        return null;
    }
}
