package com.vegecipe.domain.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);
    Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Query("SELECT count(p) FROM Post p WHERE p.title LIKE %:title%")
    Long findByTitleContaining(@Param("title") String title);
    @Query("SELECT count(p) FROM Post p WHERE p.title LIKE %:title% or p.content LIKE %:content%")
    Long findByTitleContainingOrContentContaining(@Param("title") String title, @Param("content") String content);

    @Query(value = "UPDATE Post p SET p.VIEW_CNT = (SELECT s.VIEW_CNT FROM Post s WHERE s.id = ?1) +1 WHERE p.id = ?1", nativeQuery = true )
    void updateViewCntById(String id);

}
