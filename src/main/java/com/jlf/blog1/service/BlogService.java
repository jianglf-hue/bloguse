package com.jlf.blog1.service;

import com.jlf.blog1.po.Blog;
import com.jlf.blog1.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog2(Pageable pageable);
    // Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    /*

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);


    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();*/

    Blog getAndConvert(Long id); // markdown 转 html

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
