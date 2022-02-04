package com.manage.Controller.Table;

import com.manage.Pojo.*;
import com.manage.Service.SearchService;
import com.manage.Service.UpdataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author qinsicheng
 * @Description 内容：学生页面控制器
 * @Date 17/12/2021 09:42
 */
@Slf4j
@Controller
public class studentPageController {
    @Autowired
    com.manage.Service.loginService loginService;
    @Autowired
    SearchService searchService;
    @Autowired
    UpdataService updataService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    //进入学生主页面
    @GetMapping("/studentPage")
    public String toStudentPage(Model model, HttpSession session) {
        stu_login loginUser = (stu_login)session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("returnEx", "请先进行登录");
            return "redirect:/StuLogin";
        }


        //获取登录者详细信息
        stu_message loginStu = searchService.getStuMessageById(loginUser.getStuId());
        //获取所有人的成绩单
        List<mainPoint> allMainPoint = searchService.getAllMainPoint();
        int size = allMainPoint.size();
        //遍历成绩单
        for (int i = size-1; i >= 0 ; i--) {
            stu_message stuMessageById = searchService.getStuMessageById(allMainPoint.get(i).getStuId());
            //判断是否是与登录者相同专业的学生，如果不是进行剔除
            if (!loginStu.getStuMajor().equals(stuMessageById.getStuMajor())) {
                allMainPoint.remove(i);
                continue;
            }
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
            int addEdu= 0;


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
            //限制附加分不大于10
            if (addEdu1>10) {
                addEdu1 = 10;
            }
            allMainPoint.get(i).setAdditionEdu(addEdu1);
            allMainPoint.get(i).setStuMessage(stuMessageById);
            allPoint = allMainPoint.get(i).getLearnEdu()+ allMainPoint.get(i).getSportEdu()+ allMainPoint.get(i).getMoralEdu()+ allMainPoint.get(i).getAdditionEdu();
            stuMessageById.setTotalPoint(allPoint);
        }
        model.addAttribute("allMainPoint", allMainPoint);
        return "studentPage/studentPage";
    }

    //进入学生登录界面
    @GetMapping({"/StuLogin"})
    public String toLogin(HttpSession session) {
        session.removeAttribute("loginUser");
        return "studentPage/Stulogin";

    }

    //进入学生主页面
    @PostMapping("/studentPage")
    public String toIndex(stu_login stuLogin, HttpSession session) {
        stu_login stu_login = loginService.testLogin(stuLogin.getStuId());
        if (stu_login == null || !(stuLogin.getPassword().equals(stu_login.getPassword()))) {
            session.setAttribute("returnEx", "输入错误");
            return "redirect:/StuLogin";
        } else {
            //记录学生登录信息
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.increment("logeing");

            session.setAttribute("loginUser", stuLogin);
            return "redirect:/studentPage";
        }
    }

    //跳转目标客户的综测加分成绩页面
    //GetMapping  标注自己能操作的地址
    @GetMapping("/stuClaRecord/{stu_id}")
    public String toStuClaRecord(Model model,
                                 //将地址里的 stu_id 封装为一个stuid使用
                                 @PathVariable("stu_id") Integer stuId) {

        //1.调用searchService获取自己的荣誉信息
        List<additional_record> allCla = searchService.getAllClaRecordById(stuId);
        //1.1请求各个项目类别的总分
        HashMap<String, Integer> hashMap = new HashMap<>();
        int i;
        int singularPoint = 0;
        for (additional_record record : allCla) {
            //如果获取值为null  则表示为0  获取分数
            Integer integer = hashMap.get(record.getClaName())==null?0:hashMap.get(record.getClaName());
            //获取该项目的信息对象
            Additional_Classification addClaByClaName = searchService.getAddClaByClaName(record.getClaName());
            //获取限制
            String claCeiling = addClaByClaName.getClaCeiling();
            //如果限制为只能添加一项
            if (claCeiling.equals("只能添加一项")) {
                if (singularPoint<record.getProjectScore()) {
                    singularPoint = record.getProjectScore();
                    //添加值最大的一项
                    hashMap.put(record.getClaName(),singularPoint);
                    continue;
                }
                continue;
            }

            //如果规则不是分数  则跳过
            try {
                i = Integer.parseInt(addClaByClaName.getClaCeiling());
            } catch (NumberFormatException e) {
                continue;
            }
            //如果当前项目分数已经超过要求 则进行修改 并跳过
            if (integer > i){
                hashMap.put(record.getClaName(),i);
                continue;
            }

            hashMap.put(record.getClaName(),(hashMap.get(record.getClaName())==null?0:hashMap.get(record.getClaName()))+record.getProjectScore());
            //如果加后   超过分数 则进行修改
            if (hashMap.get(record.getClaName()) > i) {
                hashMap.put(record.getClaName(),i);
            }
        }
        //1.2放入请求域中
        model.addAttribute("map",hashMap);
        //2.数据展示给页面  model是请求域对象
        model.addAttribute("allCla", allCla);
        //3.正式返回页面
        return "studentPage/ClaRecord";
    }

    //跳转到异常请求界面
    @GetMapping("/EditRequest")
    public String toEditRequest() {
        return "studentPage/EditRequest";
    }

    //获取异常请求信息
    @PostMapping("/sendRequst")
    public String toSend(String requestTxt,
                         HttpSession session,
                         @RequestPart("multiImage") MultipartFile[] multiImage) throws IOException {
        stu_login loginUser = (stu_login)session.getAttribute("loginUser");
        //异常请求的上传图片
        log.info("---------------上传的信息-------------");
        log.info("学生学号={}，上传图片数量={}",loginUser.getStuId(),multiImage.length);
        //区分图片
        Integer UUID = 1 ;
        //下载到本地
        for (MultipartFile multipartFile : multiImage) {
            if (!multipartFile.isEmpty()) {
                String originalFilename = loginUser.getStuId()+("_"+(UUID++))+".png";
                multipartFile.transferTo(new File("F:\\my_Work\\EPMS\\src\\main\\resources\\static\\images\\stu_exceptRequest\\" + originalFilename));
            }
        }


        updataService.insertStuException(new exception_request(loginUser.getStuId(),requestTxt));
        return "redirect:/studentPage";
    }
}
