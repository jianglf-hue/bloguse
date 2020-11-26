package com.jlf.blog1.service;

import com.jlf.blog1.Dao.BlogRepository;
import com.jlf.blog1.NotFoundException;
import com.jlf.blog1.po.Blog;
import com.jlf.blog1.po.Type;
import com.jlf.blog1.util.MarkdownUtils;
import com.jlf.blog1.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }
/*

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return null;
    }
*/

    @Override
    @Transactional
    public Page<Blog> listBlog2(Pageable pageable)  {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        /*Blog blog = blogRepository.findOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;*/

        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        /*blogRepository.updateViews(id);*/
        return b;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        /*blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());*/
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            //blog.setCreateTime(blog.getCreateTime());
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null)
        {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                /*if (blog.getType().getId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getType().getId()));
                }*/
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
}
