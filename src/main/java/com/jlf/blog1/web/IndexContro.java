package com.jlf.blog1.web;

import com.jlf.blog1.NotFoundException;
import com.jlf.blog1.service.BlogService;
import com.jlf.blog1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class IndexContro {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        //model.addAttribute("page",typeService.listType(pageable));
        model.addAttribute("page", blogService.listBlog2(pageable));
        //model.addAttribute("types", typeService.listType());
        // System.out.println("---index---");
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

}
