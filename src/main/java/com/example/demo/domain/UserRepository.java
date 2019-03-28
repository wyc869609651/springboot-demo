package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //扩展查询
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);
    //自定义查询
    @Query(nativeQuery = true, value = "select * from `user` where user_name = :userName1 or user_name= :userName2")
    List<User> findTwoUserByUserName(@Param("userName1") String userName1, @Param("userName2") String userName2);
}
