package com.example.demo.MyTest;

import com.example.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("learn", "Springboot");
        System.out.println(redisTemplate.opsForValue().get("learn").toString());
        User user = new User("wx01", "123456", "wx01@qq.com", "呀哈哈", "2019-04-12");
        redisTemplate.opsForValue().set("wx01", user);
    }
}
