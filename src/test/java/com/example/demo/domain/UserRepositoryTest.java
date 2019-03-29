package com.example.demo.domain; 

import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** 
* UserRepository Tester. 
* 
* @author <Authors name> 
* @since <pre>03/28/2019</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    
    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 

    @Test
    public void testInsert(){
        Date date = new Date();
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        String dateStr = dateFormat.format(date);
        User wx01 = userRepository.save(new User("wx01", "123456", "wx01@qq.com", "呀哈哈", dateStr));
        User wx02 = userRepository.save(new User("wx02", "123456", "wx02@qq.com", "哈呀哈", dateStr));
        User wx03 = userRepository.save(new User("wx03", "123456", "wx03@qq.com", "哈哈呀", dateStr));
        System.out.println(wx01);
        System.out.println(wx02);
        System.out.println(wx03);
    }

    @Test
    public void testQuery(){
        //排序查询
        Sort sort = new Sort(Sort.Direction.DESC, "userName");
        List<User> userList = userRepository.findAll(sort);
        for (User user : userList) {
            System.out.println(user);
        }
        //分页查询
        Pageable page = new PageRequest(0, 2);
        Page<User> userPage = userRepository.findAll(page);
        int totalPages = userPage.getTotalPages();
        long totalElements = userPage.getTotalElements();
        int number = userPage.getNumber();
        int size = userPage.getSize();
        System.out.println("totalPages : "+totalPages+"\ntotalElements : "+totalElements+"\nnumber : "+number+"\nsize : "+size);
        List<User> userList2 = userPage.getContent();
        for (User user : userList2) {
            System.out.println(user);
        }
    }

    @Test
    public void testExtendQuery(){
        User user1 = userRepository.findByUserName("wx01");
        User user2 = userRepository.findByUserNameAndPassword("wx02", "123456");
        System.out.println(user1);
        System.out.println(user2);
    }

    @Test
    public void testDefineQuery(){
        List<User> userList = userRepository.findTwoUserByUserName("wx01", "wx03");
        for (User user :
                userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testPageQuery(){
        Pageable page = new PageRequest(0, 2);
        Page<User> userPage = userRepository.findAllSQLPage(page);
        System.out.println(userPage.getTotalPages());
        System.out.println(userPage.getTotalElements());
        for (User user :
                userPage.getContent()) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate(){
        userRepository.modifyPasswordByUserName("wx03", "654321");
        User user = userRepository.findByUserName("wx03");
        System.out.println(user);
    }
}
