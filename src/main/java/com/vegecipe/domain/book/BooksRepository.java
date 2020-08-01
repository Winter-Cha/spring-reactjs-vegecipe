package com.vegecipe.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Long> {

    @Query("SELECT b FROM Books b ORDER BY b.id DESC")
    List<Books> findAllDesc();

    @Query(value = "SELECT * FROM books b WHERE b.id > ?1 ORDER BY b.id DESC LIMIT 1", nativeQuery = true )
    Books findByIdPreBook(Long id);

    @Query(value = "SELECT * FROM books b WHERE b.id < ?1 ORDER BY b.id LIMIT 1", nativeQuery = true )
    Books findByIdPostBook(Long id);

    @Query(value = "UPDATE Books b SET b.VIEW_CNT = (SELECT s.VIEW_CNT FROM Books s WHERE s.id = ?1) +1 WHERE b.id = ?1", nativeQuery = true )
    void updateViewCntById(String id);
}
