package com.vegecipe.web;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import com.vegecipe.service.posts.PostsService;
import com.vegecipe.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class communityController {

    private final PostsService postsService;

    @GetMapping("/community")
    public String community(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserPicture", user.getPicture());
        }
        model.addAttribute("posts", postsService.findAllDesc());
        return "pages/community";
    }

    @GetMapping("/posts/write")
    public String postWrite(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserPicture", user.getPicture());
        }
        return "pages/posts_write";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserPicture", user.getPicture());
        }
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "pages/posts_update";
    }

}
