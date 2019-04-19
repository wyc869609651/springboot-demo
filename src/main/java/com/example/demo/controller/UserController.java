package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value="User", key="methodName")
    public List<User> getAllUserList(){
        System.out.println("无缓存调用！");
        return userService.getAllUserList();
    }

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    @RequestMapping("/getUserByUserName")
    @Cacheable(value="User", key = "#userName")
    public User getUserByUserName(String userName){
        System.out.println("无缓存调用！");
        return userService.getUserByUserName(userName);
    }
}
