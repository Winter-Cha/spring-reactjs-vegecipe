package com.vegecipe.web.community;

import com.vegecipe.domain.community.Comment;
import com.vegecipe.domain.community.Post;
import com.vegecipe.exception.ResourceNotFoundException;
import com.vegecipe.service.community.PostService;
import com.vegecipe.dto.community.PostResponseDto;
import com.vegecipe.dto.community.PostSaveRequestDto;
import com.vegecipe.dto.community.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;


    @PostMapping("/api/v1/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @Valid @RequestBody Post postRequest) throws Exception {
        if( postService.checkPassword(postRequest.getId(), postRequest.getPassword()) ) {
            postService.update(id, postRequest);
            return id;
        }else{
            throw new Exception();
        }
    }

    @DeleteMapping("/api/v1/post/{id}")
    public Long delete(@PathVariable Long id, @Valid @RequestBody Post postRequest) throws Exception {
        if( postService.checkPassword(postRequest.getId(), postRequest.getPassword()) ) {
            postService.delete(id);
            return id;
        }else{
            throw new Exception();
        }
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @RequestMapping("/api/v1/post/check/password")
    public ResponseEntity<?> checkPassword( @Valid @RequestBody Post postRequest) {
        if( postService.checkPassword(postRequest.getId(), postRequest.getPassword()) ) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(999).build();
        }
    }

    @PostMapping("/api/v1/post/{postId}/update")
    public ModelAndView getAllCommentByPostId(@PathVariable (value = "postId") Long postId, @Valid @RequestBody Post postRequest) {
        ModelAndView modelAndView = new ModelAndView("pages/community/post_update");
        if( postService.checkPassword(postRequest.getId(), postRequest.getPassword()) ) {
            PostResponseDto dto = postService.findById(postId);
            modelAndView.addObject("post", dto);
            modelAndView.addObject("pwd", postRequest.getPassword());
            modelAndView.setStatus(HttpStatus.OK);
        }else{
            modelAndView.setViewName("pages/community/post_view");
            PostResponseDto dto = postService.findById(postId);
            modelAndView.addObject("post", dto);
            modelAndView.setStatus(HttpStatus.BAD_GATEWAY);
        }
        return modelAndView;
    }
}
