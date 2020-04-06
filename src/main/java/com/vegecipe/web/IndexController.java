package com.vegecipe.web;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserPicture", user.getPicture());
        }
        return "index";
    }

}
