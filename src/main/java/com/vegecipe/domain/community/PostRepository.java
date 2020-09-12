package com.vegecipe.domain.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    Page<Post> findAll(Pageable pageable);

    @Query(value = "UPDATE Post p SET p.VIEW_CNT = (SELECT s.VIEW_CNT FROM Post s WHERE s.id = ?1) +1 WHERE p.id = ?1", nativeQuery = true )
    void updateViewCntById(String id);
}
