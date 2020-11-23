package com.jlf.blog1.service;

import com.jlf.blog1.po.Tag;

import com.jlf.blog1.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);


    Tag getTagByName(String name); // 不能提交重名

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    List<Tag> listTag();
}
