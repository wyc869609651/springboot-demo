package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUserList();

    User getUserByUserName(String userName);
}
