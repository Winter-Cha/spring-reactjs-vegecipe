package com.vegecipe.service.community;

import com.vegecipe.domain.book.Books;
import com.vegecipe.domain.community.Comment;
import com.vegecipe.domain.community.CommentRepository;
import com.vegecipe.domain.community.Post;
import com.vegecipe.domain.community.PostRepository;
import com.vegecipe.dto.community.*;
import com.vegecipe.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, Post requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(post -> {
                   PostListResponseDto prd = new PostListResponseDto(post);
                   int cnt = commentRepository.findByPostId(prd.getId()).size();
                   if(cnt > 0) prd.setCommentCnt(cnt+"");
                   return prd;
                }).collect(Collectors.toList());
    }


    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostResponseDto(entity);
    }

    @Transactional
    public void updateViewCnt(Long id) {
        //booksRepository.updateViewCntById(id.toString());
        Post entity = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        entity.updateViewCnt(id);
        postRepository.save(entity);
    }

    public boolean checkPassword(Long id, String password) {
        return postRepository.findById(id).map(post -> {
            if(password.equals(post.getPassword())) {
                return true;
            }else{
                return false;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
    }
}
