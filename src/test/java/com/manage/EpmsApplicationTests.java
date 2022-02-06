package com.manage;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.manage.Dao.Additional_Classification_Dao;
import com.manage.Dao.mainPoint_Dao;
import com.manage.Dao.stu_message_Dao;
import com.manage.Dao.studentDao;

import com.manage.Pojo.*;

import com.manage.Service.SearchService;
import com.manage.Service.UpdataService;
import com.manage.Service.loginService;
import com.manage.Util.addScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@SpringBootTest
class EpmsApplicationTests {
    @Autowired
    stu_message_Dao stuMessageDao;
    @Autowired
    studentDao studentDao;
    @Autowired
    stu_message_Dao stu_message_Dao;
    @Autowired
    loginService loginService;
    @Autowired
    com.manage.Service.SearchService SearchService;
    @Autowired
    mainPoint_Dao mainPointDao;
    @Autowired
    UpdataService updataService;

    @Test
    public void testConnect() {
        List<stu_login> stu_logins = studentDao.selectList(null);
        for (stu_login stu_login : stu_logins) {
            System.out.println(stu_login);
        }
    }
    @Test
    public void testConnect1() {
        List<stu_message> allStu = SearchService.getAllStu();
        for (stu_message stu_message : allStu) {
            System.out.println("message------------");
            System.out.println(stu_message);
            System.out.println("login----------");
            System.out.println(stu_message.stu_login.getPassword());
        }
        System.out.println("==============");

    }
    @Test
    public void test1() {

        List<stu_message> stuMessage = stu_message_Dao.getStuMessage();
        for (stu_message stu_message : stuMessage) {
            System.out.println(stu_message);
        }
        System.out.println("===============");

    }
    @Test
    public void test2() {
//        stu_message stu_message = new stu_message("jack", "大三", "大数据", 1001);
        stu_message_Dao.UpdataStuLogin(new stu_login(1002,"456"));
        stu_message_Dao.UpdataStuMessage
                (new stu_message("jack321","大四","大数据",1001));
        System.out.println("=============");
        System.out.println();
    }
    @Test
    public void test3() {
        stu_message stu_message = new stu_message("小黑", "大三", "大数据", 1001,"123");
        stu_message_Dao.UpdataStuMessage(stu_message);
        stu_message_Dao.UpdataStuLogin(stu_message.stu_login);
                System.out.println("============成功");
    }
    @Test
    public void test4() {
        UpdateWrapper<stu_login> stu_id1 = new UpdateWrapper<stu_login>().eq("stu_id", 1006);
        studentDao.delete(stu_id1);
        int stu_id = stu_message_Dao.delete(new UpdateWrapper<stu_message>().eq("stu_id", 1006));
        System.out.println("删除成功");
    }
    @Test
    public void test5() {
        stu_message stu_message = new stu_message("秦思成", "大三", "经济区", 1002);
        stu_message.setStu_login(new stu_login(stu_message.getStuId(), "123456"));
        stu_message_Dao.insert(stu_message);
        studentDao.insert(stu_message.stu_login);
        System.out.println("成功");
    }
    @Test
    public void test6() {
        stu_message stu_message = new stu_message("爸爸", "干活", "大一", 1002);
        stu_message.setStu_login(new stu_login(stu_message.getStuId(),"456"));
        updataService.updateStuMessage(stu_message);
        System.out.println("======成功");
    }
    @Autowired
    Additional_Classification_Dao additionalClassificationDao;
    @Test
    public void test7() {
        System.out.println("ss");
        List<Additional_Classification> allCla = SearchService.getAllCla();
        for (Additional_Classification additional_classification : allCla) {
            System.out.println(additional_classification);
        }
    }
    @Test
    public void test8() {
        System.out.println("zz");
        updataService.insertAllRule(new Additional_Classification("上班","5"));
    }
    @Test
    public void test9() {
        System.out.println("zz");
        updataService.deleteCluRule("测试");
    }
    @Test
    public void test10() {
        List<additional_record> allClaRecord = SearchService.getAllClaRecord();
        for (additional_record additional_record : allClaRecord) {
            System.out.println(additional_record);
        }
    }
    @Test
    public void test11() {
//        updataService.insertAllRecord(new additional_record(1002,"创新创业",2,"荣誉","2021.07.01"));
//        updataService.updateAllRecord(new additional_record(1002,"创新创业",3,"荣誉1","2021.07.02"));
        updataService.deleteAllRecord("1002","创新创业");

        System.out.println("成功");
    }
    @Test
    public void test12() {
        mainPointDao.update(
        (new mainPoint
        (1002,88,86,32,20,"2021.06.02")),
                new UpdateWrapper<mainPoint>().eq("stu_id",1002));
        System.out.println("成功");
    }
    @Test
    public void test13() {
        List<exception_request> allException = SearchService.getAllException();
        for (exception_request exception_request : allException) {
            System.out.println(exception_request.getReason());
        }
    }
    /*getStuMessageById*/
    @Test
    public void test14() {
        stu_message stuMessageById = SearchService.getStuMessageById(1001);
        System.out.println(stuMessageById);
    }
    @Test
    public void test15() {
        List<additional_record> allClaRecordById = SearchService.getAllClaRecordById(1001);
        for (additional_record additional_record : allClaRecordById) {
            System.out.println(additional_record);
        }
    }
    @Test
    public void test16() {
        List<mainPoint> allMainPoint = SearchService.getAllMainPoint();
        System.out.println("初始循环遍历----------------");
        for (mainPoint mainPoint : allMainPoint) {
            System.out.println(mainPoint);
        }
        System.out.println("进行删除操作----------------");
        int size = allMainPoint.size();
        for (int i = size-1; i >= 0; i--) {
            allMainPoint.remove(i);
        }
        System.out.println("再次进行循环----------------");
        for (mainPoint mainPoint : allMainPoint) {
            System.out.println(mainPoint);
        }
        }
    @Test
    public void test17() {
        stu_message stuMessageById = SearchService.getStuMessageById(1001);
        System.out.println(stuMessageById);
    }

    @Test
    public void test18() {
        HashMap<String, Integer> map = new HashMap<>();
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            entry.getKey();
            entry.getValue();
        }
    }

    @Test
    public void test19() {
     String s = "1";
     String s1 = "nan";
        try {
            int i = Integer.parseInt(s);
            System.out.println(i);
        } catch (NumberFormatException e) {
            System.out.println("此对象部分翻译成数字");
        }

    }

    @Test
    public void test20() {
        List<mainPoint> mainPoints = SearchService.addAllScore();
        System.out.println("开始");
        for (mainPoint mainPoint : mainPoints) {
            Integer stuId = mainPoint.getStuId();
            Integer additionEdu = mainPoint.getAdditionEdu();
            System.out.println(stuId + "的附加加分为" +additionEdu);
        }
    }
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test21() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String andDelete = operations.getAndDelete("1");
        System.out.println(andDelete);
        String s = operations.get("1");
        System.out.println(s);
    }


}



