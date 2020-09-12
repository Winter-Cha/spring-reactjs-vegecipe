package com.vegecipe.web.community;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import com.vegecipe.domain.community.Comment;
import com.vegecipe.domain.community.CommentRepository;
import com.vegecipe.domain.community.PostRepository;
import com.vegecipe.dto.community.CommentListResponseDto;
import com.vegecipe.dto.community.PostResponseDto;
import com.vegecipe.dto.community.PostUpdateRequestDto;
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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/v1/post/{postId}/comments")
    public ModelAndView getAllCommentByPostId(@PathVariable (value = "postId") Long postId, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("pages/community/comment/comment_list");
        Page<Comment> pagedListHolder = commentRepository.findByPostId(postId, pageable);
        //pagedListHolder.setPageSize(1);
        modelAndView.addObject("comments", pagedListHolder);
        modelAndView.addObject("commentTotCnt", pagedListHolder.getTotalElements());
        modelAndView.addObject("postId", postId);
        return modelAndView;
    }

    @PostMapping("/api/v1/post/{postId}/comments")
    public Comment createComment(@PathVariable (value = "postId") Long postId, @Valid @RequestBody Comment comment) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        comment.setAuthorIp(ip);
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @PutMapping("/api/v1/post/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "postId") Long postId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/api/v1/post/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                                           @PathVariable (value = "commentId") Long commentId,
                                           @Valid @RequestBody Comment commentRequest) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            if(commentRequest.getPassword().equals(comment.getPassword())) {
                commentRepository.delete(comment);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(999).build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }

}
