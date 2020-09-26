package com.vegecipe.web;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import com.vegecipe.dto.book.BooksResponseDto;
import com.vegecipe.service.book.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

/*    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUserName", user.getName());
            model.addAttribute("loginUserPicture", user.getPicture());
        }
        return "content";
    }*/

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "pages/login";
    }


    @GetMapping("/vegecipe")
    public String vegecipe(Model model) {
        // SEO를 위한 데이터
        model.addAttribute("seo_title", "베지서피는");
        model.addAttribute("seo_desc", "채식인들이 앞뒤 없는 공격과 싸우지 않아도 되고 마음 편하게 소통할 수 있는 공간이 필요하다고 느껴 커뮤니티를 만들었습니다. 이곳은 채식의 찬반을 다투거나 논쟁하는 장소가 아니라, 채식인들이 모여 편안하게 담소를 나누는 지극히 채식인에 편향된 커뮤니티입니다. ");
        model.addAttribute("seo_image", "");
        return "pages/vegecipe/vegecipe_about";
    }



}
