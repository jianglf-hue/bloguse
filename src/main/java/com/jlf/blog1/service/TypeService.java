package com.jlf.blog1.service;

import com.jlf.blog1.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id); // 查询

    Type getTypeByName(String name); // 不能提交重名

    Page<Type> listType(Pageable pageable); // 分页

    Type updateType(Long id,Type type);  // 修改
    void deleteType(Long id);

    List<Type> listType();

}
