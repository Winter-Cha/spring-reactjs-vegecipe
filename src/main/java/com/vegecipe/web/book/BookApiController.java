package com.vegecipe.web.book;

import com.vegecipe.service.book.BooksService;
import com.vegecipe.dto.book.BooksResponseDto;
import com.vegecipe.dto.book.BooksSaveRequestDto;
import com.vegecipe.dto.book.BooksUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BooksService booksService;


    @PostMapping("/api/v1/books")
    public Long save(@RequestBody BooksSaveRequestDto requestDto) {
        return booksService.save(requestDto);
    }

    @PutMapping("/api/v1/books/{id}")
    public Long update(@PathVariable Long id, @RequestBody BooksUpdateRequestDto requestDto) {
        return booksService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public Long delete(@PathVariable Long id) {
        booksService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/books/{id}")
    public BooksResponseDto findById(@PathVariable Long id) {
        return booksService.findById(id);
    }
}
