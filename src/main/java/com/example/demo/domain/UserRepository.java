package com.example.demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //扩展查询
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);

    //自定义查询
    @Query(nativeQuery = true, value = "select * from `user` where user_name = :userName1 or user_name= :userName2")
    List<User> findTwoUserByUserName(@Param("userName1") String userName1, @Param("userName2") String userName2);

    @Transactional
    @Modifying
    @Query("update User set password = ?2 where userName = ?1")//hql
    int modifyPasswordByUserName(String userName, String password);

    //测试分页
    @Query(nativeQuery = true, value = "select * from `user`")//理论上不可以，但是居然能用（诡异）
    Page<User> findAllSQLPage(Pageable pageable);

    @Query("select u from User u")//使用select查询实体对象，必须采用别名(坑)
    Page<User> findAllHQLPage(Pageable pageable);
}
