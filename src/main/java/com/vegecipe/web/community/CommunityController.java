package com.vegecipe.web.community;

import com.vegecipe.service.community.PostsService;
import com.vegecipe.dto.community.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final PostsService postsService;

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        //return "/pages/community/posts_list";
        return "/pre_page";
    }

    @GetMapping("/posts/write")
    public String postWrite() {
        return "/pages/community/posts_write";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "/pages/community/posts_update";
    }

}
