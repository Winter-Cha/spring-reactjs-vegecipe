package com.vegecipe.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository extends JpaRepository<BookComment,Long> {
    Page<BookComment> findByBookId(Long bookId, Pageable pageable);
    List<BookComment> findByBookId(Long bookId);
    Optional<BookComment> findByIdAndBookId(Long id, Long bookId);
}
