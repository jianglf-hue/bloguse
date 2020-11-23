package com.jlf.blog1.Dao;

import com.jlf.blog1.po.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>{
    Tag findByName(String name); // 查重名
}
