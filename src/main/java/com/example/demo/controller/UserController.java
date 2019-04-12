package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 获取所有的用户列表
     * @return
     */
    @RequestMapping("/getAllUserList")
    public List<User> getAllUserList(){
        return userService.getAllUserList();
    }
}
