package com.vegecipe.web.book;

import com.vegecipe.domain.book.BookComment;
import com.vegecipe.domain.book.BookCommentRepository;
import com.vegecipe.domain.book.BooksRepository;
import com.vegecipe.domain.community.Comment;
import com.vegecipe.domain.community.CommentRepository;
import com.vegecipe.domain.community.PostRepository;
import com.vegecipe.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BookCommentApiController {

    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Autowired
    private BooksRepository booksRepository;

    @GetMapping("/api/v1/book/{bookId}/comments")
    public ModelAndView getAllCommentByBookId(@PathVariable (value = "bookId") Long bookId, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("pages/book/comment/comment_list");
        Page<BookComment> pagedListHolder = bookCommentRepository.findByBookId(bookId, pageable);
        //pagedListHolder.setPageSize(1);
        modelAndView.addObject("comments", pagedListHolder);
        modelAndView.addObject("commentTotCnt", pagedListHolder.getTotalElements());
        modelAndView.addObject("bookId", bookId);
        return modelAndView;
    }

    @PostMapping("/api/v1/book/{bookId}/comments")
    public BookComment createComment(@PathVariable (value = "bookId") Long bookId, @Valid @RequestBody BookComment comment) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        comment.setAuthorIp(ip);
        return booksRepository.findById(bookId).map(book -> {
            comment.setBook(book);
            return bookCommentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
    }

    @PutMapping("/api/v1/book/{bookId}/comments/{commentId}")
    public BookComment updateComment(@PathVariable (value = "bookId") Long bookId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody BookComment commentRequest) {
        if(!booksRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("BookId " + bookId + " not found");
        }

        return bookCommentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return bookCommentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/api/v1/book/{bookId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "bookId") Long bookId,
                                           @PathVariable (value = "commentId") Long commentId,
                                           @Valid @RequestBody BookComment commentRequest) {
        return bookCommentRepository.findByIdAndBookId(commentId, bookId).map(comment -> {
            if(commentRequest.getPassword().equals(comment.getPassword())) {
                bookCommentRepository.delete(comment);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(999).build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and bookId " + bookId));
    }

}
