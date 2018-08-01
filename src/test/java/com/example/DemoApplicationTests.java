package com.example;

import com.alibaba.fastjson.JSON;
import com.example.Dao.Customer2Dao;
import com.example.Dao.Customer3Dao;
import com.example.Dao.CustomerDao;
import com.example.Entity.CustomerCoupon;
import com.example.Service.CustomerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {
    Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);
    @Autowired
    CustomerDao customerDao;

    @Autowired
    Customer2Dao customer2Dao;
    @Autowired
    Customer3Dao customer3Dao;

    @Autowired
    CustomerService customerService;

    @Test
    public void contextLoads() {
        logger.info("====开始啦");
        CustomerCoupon customerCoupon = customerDao.findOne(30L);
        System.out.println("============3" + JSON.toJSONString(customerCoupon));

        List<CustomerCoupon> list = customerDao.findByCustomerId(7017L);
        System.out.println("============1" + JSON.toJSONString(list));

        CustomerCoupon c = customerDao.get(30L);
        System.out.println("=====2" + JSON.toJSONString(c));

        List<CustomerCoupon> list2 = customerService.getList();
        System.out.println(JSON.toJSONString(list2));

        Pageable pageable = new PageRequest(0, 5);
        Page<CustomerCoupon> list3 = customerDao.findAll(pageable);
        System.out.println(JSON.toJSONString(list3));
    }

    @Test
    public void testOther() {
        PageRequest page = new PageRequest(1, 5);
        Page<CustomerCoupon> list = customer2Dao.findAll(page);
        System.out.println("=====" + JSON.toJSONString(list));

    }

    @Test
    public void testPage() {
        Page<CustomerCoupon> page = customerService.getListByCondition(null);
        System.out.println("条件查询" + JSON.toJSONString(page));
    }

    @Test
    public void testNot() {
        List<CustomerCoupon> list = customer3Dao.findByCustomerIdAndIdNot(7017L,30L);
        System.out.println(JSON.toJSONString(list));

    }
    @Test
    public void DateTest(){
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.MONTH,1);
        Date date=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String d=sdf.format(date);
        System.out.println("============="+d);
    }

}