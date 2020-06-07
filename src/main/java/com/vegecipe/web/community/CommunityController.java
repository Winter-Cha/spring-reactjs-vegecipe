package com.vegecipe.web.community;

import com.vegecipe.service.community.PostsService;
import com.vegecipe.dto.community.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final PostsService postsService;

    @GetMapping("/community")
    public ModelAndView community() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/community/posts_list");
        modelAndView.addObject("posts", postsService.findAllDesc());
        return modelAndView;
    }

    @GetMapping("/posts/write")
    public String postWrite() {
        return "/pages/community/posts_write";
    }

    @GetMapping("/posts/update/{id}")
    public ModelAndView postUpdate(@PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/community/posts_update");
        modelAndView.addObject("post", dto);
        return modelAndView;
    }

}
