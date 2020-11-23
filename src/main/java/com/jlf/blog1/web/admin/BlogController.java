package com.jlf.blog1.web.admin;


import com.jlf.blog1.po.Blog;
import com.jlf.blog1.po.Tag;
import com.jlf.blog1.service.BlogService;
import com.jlf.blog1.service.TagService;
import com.jlf.blog1.service.TypeService;
import com.jlf.blog1.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 5,sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        //model.addAttribute("page",blogService.listBlog(pageable));

        //model.addAttribute("page", blogService.listBlog2(pageable));
        model.addAttribute("types", typeService.listType());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }


    @GetMapping("/blogs/input")
    public String blogs(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/blog-input";
    }

    @PostMapping("/blogs")
    public String post(@Valid Blog blog, BindingResult result, RedirectAttributes attributes){
        blogService.saveBlog(blog);
        return "redirect:/admin/blogs";
    }
}
