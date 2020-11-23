package com.jlf.blog1.service;

import com.jlf.blog1.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
