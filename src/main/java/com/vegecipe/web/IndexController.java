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
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "pages/login";
    }

    private final BooksService booksService;

    @GetMapping("/book")
    //public String book(Model model) {
    public String book() {
        //model.addAttribute("books", booksService.findAllDesc());
        return "/pages/book/books_list";
    }

    @GetMapping("/book/write")
    public String bookWrite() {
        return "/pages/book/books_write";
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdate(@PathVariable Long id, Model model) {
        BooksResponseDto dto = booksService.findById(id);
        model.addAttribute("book", dto);
        return "/pages/book/books_update";
    }

    @GetMapping("/book/view/{id}")
    public String bookView(@PathVariable Long id, @LoginUser SessionUser user, Model model ) {
        BooksResponseDto dto = booksService.findById(id);
        booksService.updateViewCnt(id);
        BooksResponseDto pre = booksService.findByIdPreBook(id);
        BooksResponseDto post = booksService.findByIdPostBook(id);

        model.addAttribute("book", dto);

        // author check
        String authorEmail = dto.getAuthorEmail();
        if(user != null && user.getEmail().equals(authorEmail))
            model.addAttribute("isAuthor", true);

        model.addAttribute("pre_book", pre);
        model.addAttribute("post_book", post);

        return "/pages/book/books_view";
    }

}
