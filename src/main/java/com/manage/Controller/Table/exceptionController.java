package com.manage.Controller.Table;

import com.manage.Pojo.exception_request;
import com.manage.Service.SearchService;
import com.manage.Service.UpdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：学生异常请求控制器
 * @Date 16/12/2021 19:59
 */
@Controller
public class exceptionController {
    @Autowired
    SearchService searchService;
    @Autowired
    UpdataService updataService;
    @GetMapping("/toExceptPage")
    public String toExc(Model model) {
        List<exception_request> allException = searchService.getAllException();
        model.addAttribute("allException",allException);
        return "exception_request/exception_req";
    }
    @GetMapping("/deleteExcept/{stu_id}")
    public String toDeleteById(@PathVariable("stu_id") Integer id) {
        updataService.deleteException(id);
        return "redirect:/toExceptPage";
    }
}
