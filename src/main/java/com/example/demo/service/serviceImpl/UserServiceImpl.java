package com.example.demo.service.serviceImpl;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUserList(){
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
