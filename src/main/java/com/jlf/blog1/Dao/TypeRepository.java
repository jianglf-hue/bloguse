package com.jlf.blog1.Dao;

import com.jlf.blog1.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name); // 查重名
}
