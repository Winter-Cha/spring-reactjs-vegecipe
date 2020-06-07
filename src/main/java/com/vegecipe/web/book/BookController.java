package com.vegecipe.web.book;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import com.vegecipe.dto.book.BooksResponseDto;
import com.vegecipe.service.book.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BooksService booksService;

    @GetMapping("/book")
    public ModelAndView book() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/book/books_list");
        modelAndView.addObject("books", booksService.findAllDesc());
        return modelAndView;
    }

    @GetMapping("/book/write")
    public String bookWrite() {
        return "/pages/book/books_write";
    }

    @GetMapping("/book/update/{id}")
    public ModelAndView bookUpdate(@PathVariable Long id) {
        BooksResponseDto dto = booksService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/book/books_update");
        modelAndView.addObject("book", dto);
        return modelAndView;
    }

    @GetMapping("/book/view/{id}")
    public ModelAndView bookView(@PathVariable Long id, @LoginUser SessionUser user ) {
        BooksResponseDto dto = booksService.findById(id);
        booksService.updateViewCnt(id);
        BooksResponseDto pre = booksService.findByIdPreBook(id);
        BooksResponseDto post = booksService.findByIdPostBook(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/book/books_view");
        modelAndView.addObject("book", dto);
        // author check
        String authorEmail = dto.getAuthorEmail();
        if(user != null && user.getEmail().equals(authorEmail))
            modelAndView.addObject("isAuthor", true);
        modelAndView.addObject("pre_book", pre);
        modelAndView.addObject("post_book", post);

        return modelAndView;
    }

}
