package com.example.controller;

import com.example.Dao.user.UserDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/8/10.
 */
@RestController
public class Controller {

    @Resource
    UserDao userdao;

    @RequestMapping("/testModule")
    public String test() {
        return "success";
    }

}
