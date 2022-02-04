package com.manage.Controller.Table;

import com.manage.Pojo.Additional_Classification;
import com.manage.Pojo.additional_record;
import com.manage.Pojo.stu_login;
import com.manage.Pojo.stu_message;
import com.manage.Service.SearchService;
import com.manage.Service.UpdataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 13/12/2021 16:19
 */
@Slf4j
@Controller
public class controller {
    @Autowired
    SearchService searchService;
    @Autowired
    UpdataService updataService;
    //跳转到学生信息管理页面 并附加数据
    @RequestMapping("/editable_student")
    public String toEditable_student(Model model) {
        List<stu_message> allStu = searchService.getAllStu();
        model.addAttribute("allStu",allStu);
        return "tables/editable_table";
    }
    //跳转到综测加分分类信息管理页面 并附带数据
    @RequestMapping("/editable_addRule")
    public String toEditable_teacher(Model model) {
        List<Additional_Classification> allCla = searchService.getAllCla();
        model.addAttribute("AllCla",allCla);
        return "tables/editable_table_addRulehtml";
    }
    @GetMapping("/editable_student_Updata")
    public String toUpdate() {
        return "tables/editable_table_Update";
    }
    //跳转到数据更新页面 并 带有参数
    @GetMapping("/editable_student_Updata/{stuName}/{classNum}/{stuGrade}/{stuMajor}/{stuId}/{password}")
    public String toUpdate(@PathVariable("stuName") String stuName,
                           @PathVariable("classNum") Integer classNum,
                           @PathVariable("stuGrade") String stuGrade,
                           @PathVariable("stuMajor") String stuMajor,
                           @PathVariable("stuId") Integer stuId,
                           @PathVariable("password") String password,
                           Model model)
            {
                stu_message stu_message = new stu_message(stuName,classNum,stuGrade,stuMajor,stuId);
                stu_message.setStu_login(new stu_login(password));
                model.addAttribute("updataStu",stu_message);
                return "tables/editable_table_Update";
    }
    //学生数据更新页面 并 提交更新请求
    @PostMapping("/updataStuMessage")
    public String toUpdate(stu_message stu_message,String password) {
        stu_message.setStu_login(new stu_login(stu_message.getStuId(), password));
        updataService.updateStuMessage(stu_message);
        return "redirect:/editable_student";
    }
    //学生数据管理页面 请求删除
    @GetMapping("/deleteStuMessage/{stuId}")
    public String toDelete(@PathVariable("stuId") Integer stu_id) {
        updataService.deleteStu(stu_id);
        return "redirect:/editable_student";
    }
    //学生数据添加请求并提交
    @PostMapping("/insertStuMessage")
    public String toInsert(
            stu_message stu_message,String password
    ) {

        stu_message.setStu_login(new stu_login(stu_message.getStuId(), password));
        updataService.insertStuMessage(stu_message);
        return "redirect:/editable_student";
    }
    //跳转到添加综测加分分类页面
    @GetMapping("/editable_Cla_addRule")
    public String toDditable_addRule() {
        return "tables/editable_table_Update_addRule";
    }
    //跳转到更改综测加分分类页面
    @GetMapping("/editable_Cla_addRule/{claName}/{claCeiling}")
    public String toDditable_UpdataRule(
            @PathVariable("claName") String claName,
            @PathVariable("claCeiling") String claCeiling,
            Model model
    ) {
        Additional_Classification additionalClassification = new Additional_Classification(claName, claCeiling);
        model.addAttribute("updataValue",additionalClassification);
        return "tables/editable_table_Update_addRule";
    }
    //改变综测加分规则
    @PostMapping("/toChangeAllRule")
    public String toChangeAllRule(Additional_Classification additionalClassification,
                                  String cla_method) {

        if (cla_method.equals("updata")) {
            updataService.updataAllRule(additionalClassification);
        } else if (cla_method.equals("insert")){
            updataService.insertAllRule(additionalClassification);
        }
        return "redirect:/editable_addRule";
    }
    //删除综测加分规则
    @GetMapping("/deleteClaRule/{name}")
    public String deleteCluRule(@PathVariable("name") String name) {
        updataService.deleteCluRule(name);
        return "redirect:/editable_addRule";
    }
    //获取学生综测加分详细信息
    @GetMapping("/AllClaRecord")
    public String toAllClaRecord(Model model) {
        List<additional_record> allClaRecord = searchService.getAllClaRecord();
        model.addAttribute("allClaRecord",allClaRecord);
        return "extraPointTable/ClaRecord";
    }
    //跳转到修改页面 附带信息
    @GetMapping("/UpdataAllClaRecord/{id}/{proName}/{score}/{class}/{data}")
    public String toUpdata(@PathVariable("id") Integer id,
                           @PathVariable("proName") String proName,
                           @PathVariable("score") Integer score,
                           @PathVariable("class") String Allclass,
                           @PathVariable("data") String data,
                           Model model) {
        //获取荣誉项目分类
        List<Additional_Classification> allCla = searchService.getAllCla();
        additional_record record = new additional_record(id, proName, score, Allclass, data);
        model.addAttribute("allCla",allCla);
        model.addAttribute("record",record);
        return "extraPointTable/ClaRecordUpdate";
    }
    //挑战到修改综测加分页面
    @GetMapping("/UpdataAllClaRecord")
    public String toInsert(Model model) {
        //获取荣誉项目分类
        List<Additional_Classification> allCla = searchService.getAllCla();
        model.addAttribute("allCla",allCla);
        return "extraPointTable/ClaRecordUpdate";
    }
    //对综测加分信息进行修改
    @PostMapping("/toUpdataAllClaRecor")
    public String toUpdata(additional_record additionalRecord,String score_method) {
        if (score_method.equals("updata")) {
            updataService.updateAllRecord(additionalRecord);
        } else if (score_method.equals("insert")) {
            updataService.insertAllRecord(additionalRecord);
        }
        return "redirect:/AllClaRecord";
    }

    @GetMapping("/toDeleteScore/{id}/{projectName}")
    public String toDelete(@PathVariable("id") String id,
                           @PathVariable("projectName") String projectName) {
        updataService.deleteAllRecord(id,projectName);
        return "redirect:/AllClaRecord";
    }
}
