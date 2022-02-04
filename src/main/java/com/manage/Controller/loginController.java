package com.manage.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.manage.Pojo.exception_request;
import com.manage.Pojo.stu_login;

import com.manage.Service.SearchService;
import com.manage.Service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @Author qinsicheng
 * @Description 内容：负责后台管理人员登录控制器
 * @Date 13/12/2021 10:48
 */
@Controller
public class loginController {
    @Autowired
    com.manage.Service.loginService loginService;
    @Autowired
    SearchService searchService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping(value = "/")
    public String toLogin() {
        return "login";
    }

    @PostMapping (value = "/index")
    public String toIndex(stu_login stu_login, HttpSession session, Model model) {
        com.manage.Pojo.stu_login stuLogin = loginService.testLogin(stu_login.getStuId());
        // 异常信息数量
        List<exception_request> allException = searchService.getAllException();
        if (!allException.isEmpty()) {
            model.addAttribute("exceptNum",allException.size());
        }
        // 1.全校学生人数
        Integer allStuNum = searchService.AllStuNum();
        model.addAttribute("allStuNum",allStuNum);
        // 2.综测加分分类个数
        Integer allClaNum = searchService.AllClaNum();
        model.addAttribute("allClaNum",allClaNum);

        if (stuLogin == null||!(stuLogin.getPassword().equals(stu_login.getPassword()))) {

            session.setAttribute("returnEx", "输入错误");
            return "login";
        } else {

            session.setAttribute("loginUser", stuLogin);
            return "index";
        }

    }
    @GetMapping("/index")
    public String Index(HttpSession session,Model model) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        List<exception_request> allException = searchService.getAllException();

        if (!allException.isEmpty()) {
            model.addAttribute("exceptNum",allException.size());
        }
        // 1.全校学生人数
        Integer allStuNum = searchService.AllStuNum();
        model.addAttribute("allStuNum",allStuNum);
        // 2.综测加分分类个数
        Integer allClaNum = searchService.AllClaNum();
        model.addAttribute("allClaNum",allClaNum);

        Integer login = Integer.parseInt(Objects.requireNonNull(ops.get("logeing")));
        model.addAttribute("login",login);

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("returnEx","请先进行登录");
            return "/";
        }
        return "index";
    }

    @GetMapping("/regist")
    public String toRegist() {
        return "/registration/registration";
    }
    @ResponseBody
    @GetMapping("/testLoginUserById")
    public String toTest(Integer username) {
        stu_login stuPasswordById = searchService.getStuPasswordById(username);
        return new Gson().toJson(stuPasswordById);
    }
}
