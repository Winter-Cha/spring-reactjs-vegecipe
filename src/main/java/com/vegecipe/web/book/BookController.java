package com.vegecipe.web.book;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BooksService booksService;

    @GetMapping("/book")
    //public String book(Model model) {
    public String book(Model model) {

        model.addAttribute("books", booksService.findAllDesc());

        // SEO를 위한 데이터
        model.addAttribute("seo_title", "비건책");
        model.addAttribute("seo_desc", "안녕하세요 채식인 여러분!! 베지서피에서는 원서 <The minimalist Vegan>의 한글 번역을 제공하고 있습니다.");
        model.addAttribute("seo_image", "");
        
        return "pages/book/books_list";
    }

    @GetMapping("/book/write")
    public String bookWrite() {
        return "pages/book/books_write";
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdate(@PathVariable Long id, Model model) {
        BooksResponseDto dto = booksService.findById(id);
        model.addAttribute("book", dto);
        return "pages/book/books_update";
    }

    @GetMapping("/book/view/{id}")
    public String bookView(@PathVariable Long id, @LoginUser SessionUser user, Model model ) {
        booksService.updateViewCnt(id);
        BooksResponseDto dto = booksService.findById(id);
        BooksResponseDto pre = booksService.findByIdPreBook(id);
        BooksResponseDto post = booksService.findByIdPostBook(id);

        model.addAttribute("book", dto);

        // author check
        String authorEmail = dto.getAuthorEmail();
        if(user != null && user.getEmail().equals(authorEmail))
            model.addAttribute("isAuthor", true);

        model.addAttribute("pre_book", pre);
        model.addAttribute("post_book", post);

        String desc = "";
        int len = dto.getContent().length();
        if(len > 200){
            desc = dto.getContent().substring(0,200);
        }else{
            desc = dto.getContent();
        }

        // SEO를 위한 데이터
        model.addAttribute("seo_title", dto.getTitle());
        model.addAttribute("seo_desc", desc);
        model.addAttribute("seo_image", "");

        return "pages/book/books_view";
    }


}
