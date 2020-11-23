package com.jlf.blog1.Dao;

import com.jlf.blog1.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword (String username,String password);
}
