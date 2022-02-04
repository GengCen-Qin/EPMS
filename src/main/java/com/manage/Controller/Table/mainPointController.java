package com.manage.Controller.Table;

import com.manage.Dao.mainPoint_Dao;
import com.manage.Pojo.additional_record;
import com.manage.Pojo.mainPoint;
import com.manage.Pojo.stu_message;
import com.manage.Service.SearchService;
import com.manage.Service.UpdataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：成绩总菜单控制器
 * @Date 16/12/2021 17:21
 */
@Slf4j
@Controller
public class mainPointController {
    @Autowired
    UpdataService updataService;
    @Autowired
    SearchService searchService;
    //跳转到主成绩单页面
    @GetMapping("/mainPoint")
    public String toMain(Model model) {
        int totalPoint;
        List<mainPoint> allMainPoint = searchService.getAllMainPoint();
        for (mainPoint mainPoint : allMainPoint) {
            //新建一个Hash表，存储展示的数据
            HashMap<String, Integer> hashMap = new HashMap<>();
            //通过学生id，查询所有的获取荣誉
            List<additional_record> allClaRecordById = searchService.getAllClaRecordById(mainPoint.getStuId());
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
            mainPoint.setAdditionEdu(addEdu);
            totalPoint = mainPoint.getLearnEdu() + mainPoint.getMoralEdu() + mainPoint.getAdditionEdu() + mainPoint.getSportEdu();

            stu_message stuMessageById = searchService.getStuMessageById(mainPoint.getStuId());

            stuMessageById.setTotalPoint(totalPoint);
            mainPoint.setStuMessage(stuMessageById);
        }
        //修改部分
        List<mainPoint> mainPoints = searchService.addAllScore();
        int size = mainPoints.size();
        int size1 = allMainPoint.size();
        for (int i = 0; i < allMainPoint.size(); i++) {
            allMainPoint.get(i).setAdditionEdu(mainPoints.get(i).getAdditionEdu());
        }
        //

        model.addAttribute("allMainPoint", allMainPoint);
        return "mainPointTable/mainPoint";

    }

    @GetMapping("/toUpdatePage/{id}/{moral}/{iq}/{sport}/{extra}/{time}")
    public String toPageUpdata(@PathVariable("id") Integer id,
                               @PathVariable("moral") Integer moral,
                               @PathVariable("iq") Integer iq,
                               @PathVariable("sport") Integer sport,
                               @PathVariable("extra") Integer extra,
                               @PathVariable("time") String time,
                               Model model) {
        mainPoint mainPoint = new mainPoint(id, moral, iq, sport, extra, time);
        model.addAttribute("mainPoint", mainPoint);
        return "mainPointTable/mainPointUpdate";
    }

    @GetMapping("/toInsertPage")
    public String toPageInsert() {
        return "mainPointTable/mainPointUpdate";
    }

    @GetMapping("/deleteMainPoint/{id}")
    public String toDelete(@PathVariable("id") Integer id) {
        updataService.deleteMainPoint(id);
        return "redirect:/mainPoint";
    }

    @PostMapping("/updataMainPoint")
    public String toDelete(mainPoint mainPoint, String score_method) {
        if (score_method.equals("insert")) {
            updataService.insertMainPoint(mainPoint);
        } else if (score_method.equals("update")) {
            updataService.updateMainPoint(mainPoint);
        }
        return "redirect:/mainPoint";
    }
}
