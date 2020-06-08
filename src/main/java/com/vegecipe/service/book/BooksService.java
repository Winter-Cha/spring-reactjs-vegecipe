package com.vegecipe.service.book;

import com.vegecipe.domain.book.Books;
import com.vegecipe.domain.book.BooksRepository;
import com.vegecipe.dto.book.BooksListResponseDto;
import com.vegecipe.dto.book.BooksResponseDto;
import com.vegecipe.dto.book.BooksSaveRequestDto;
import com.vegecipe.dto.book.BooksUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Transactional
    public Long save(BooksSaveRequestDto requestDto) {
        return booksRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BooksUpdateRequestDto requestDto) {
        Books books = booksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        books.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Books books = booksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        booksRepository.delete(books);
    }

    @Transactional(readOnly = true)
    public List<BooksListResponseDto> findAllDesc() {
        return booksRepository.findAllDesc().stream()
                .map(BooksListResponseDto::new)
                .collect(Collectors.toList());
    }


    public BooksResponseDto findById(Long id) {
        Books entity = booksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new BooksResponseDto(entity);
    }

    public BooksResponseDto findByIdPreBook(Long id) {
        Books entity = booksRepository.findByIdPreBook(id.toString());
        if (entity == null)
            return null;
        else{
            return new BooksResponseDto(entity);
        }
    }

    public BooksResponseDto findByIdPostBook(Long id) {
        Books entity = booksRepository.findByIdPostBook(id.toString());
        if (entity == null)
            return null;
        else{
            return new BooksResponseDto(entity);
        }
    }

    @Transactional
    public void updateViewCnt(Long id) {
        //booksRepository.updateViewCntById(id.toString());
        Books books = booksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        books.updateViewCnt(id);
        booksRepository.save(books);
    }
}
