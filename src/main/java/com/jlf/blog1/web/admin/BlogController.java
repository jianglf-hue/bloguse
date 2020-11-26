package com.jlf.blog1.web.admin;


import com.jlf.blog1.po.Blog;
import com.jlf.blog1.po.Tag;
import com.jlf.blog1.po.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    public String blog(@PageableDefault(size = 8,sort = {"updateTime"}, direction = Sort.Direction.DESC)
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
        model.addAttribute("blog", new Blog());  // 返还一个新的对象，用于区分是否存在id值，来加载thymeleaf
        return "admin/blog-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        // blog.setUser((User) session.getAttribute("user"));
        // System.out.println(blog.getId());
        // blogService.saveBlog(blog);
        //Blog b;

        if (blog.getId() == null) {
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog.getId(), blog);
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = blogService.getBlog(id);
        //blog.init();
        model.addAttribute("blog",blog);
        return "admin/blog-input";
    }

    @PostMapping("/blogs/{id}")
    public String edit(@Valid Blog blog, @PathVariable Long id, RedirectAttributes attributes){
        //model.addAttribute("blog", blogService.getBlog(id));
        blogService.updateBlog(id,blog);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, Model model){
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }
}
