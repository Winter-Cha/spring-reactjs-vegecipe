package com.vegecipe.web.community;

import com.vegecipe.domain.community.CommentRepository;
import com.vegecipe.domain.community.Post;
import com.vegecipe.domain.community.PostRepository;
import com.vegecipe.dto.community.PostListResponseDto;
import com.vegecipe.service.community.PostService;
import com.vegecipe.dto.community.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/community")
    public String community(Model model) {
        int total = postRepository.findAllDesc().size(); // 총 게시글 수
        model.addAttribute("postTotCnt",total);
        return "pages/community/post_list";
    }

    @GetMapping("/posts")
    public String community(Model model, Pageable pageable) {
        Page<Post> pagedListHolder = postRepository.findAll(pageable);
        if(!pagedListHolder.isLast()){
            model.addAttribute("isLast", true);
        }

        List<PostListResponseDto> posts = pagedListHolder.getContent().stream()
                .map(post -> {
                    PostListResponseDto prd = new PostListResponseDto(post);
                    int cnt = commentRepository.findByPostId(prd.getId()).size();
                    if(cnt > 0) prd.setCommentCnt(cnt+"");
                    return prd;
                }).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        model.addAttribute("totCnt", pagedListHolder.getTotalElements());
        model.addAttribute("totPageCnt", pagedListHolder.getTotalPages());
        model.addAttribute("pageable", pagedListHolder.getPageable());
        return "pages/community/post_list_page";
        //return "pre_page";
    }

    @GetMapping("/post/write")
    public String postWrite() {
        return "pages/community/post_write";
    }

    @PostMapping("/post/update")
    public ModelAndView postUpdate(@Valid @RequestBody Post postRequest) {
        ModelAndView modelAndView = new ModelAndView("pages/community/post_update");
        if( postService.checkPassword(postRequest.getId(), postRequest.getPassword()) ) {
            PostResponseDto dto = postService.findById(postRequest.getId());
            modelAndView.addObject("post", dto);
            modelAndView.setViewName("pages/community/post_update");
        }else{
            postService.updateViewCnt(postRequest.getId());
            PostResponseDto dto = postService.findById(postRequest.getId());
            //BooksResponseDto pre = postsService.findByIdPreBook(id);
            //BooksResponseDto post = postsService.findByIdPostBook(id);

            modelAndView.addObject("post", dto);

            //model.addAttribute("pre_book", pre);
            //model.addAttribute("post_book", post);
            modelAndView.setViewName("pages/community/post_view");
        }

        return modelAndView;
    }

    @GetMapping("/post/view/{id}/page")
    public String bookView(@PathVariable Long id, Model model , Pageable pageable,@RequestParam String sort, @RequestParam Long pageBlockCnt, @RequestParam Long pageBlockIndex ) {
        postService.updateViewCnt(id);
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);
        // 총 게시글 수
        int total = postRepository.findAllDesc().size();
        model.addAttribute("postTotCnt",total);

        model.addAttribute("sort", sort);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("page-block-cnt", pageBlockCnt);
        model.addAttribute("page-block-index", pageBlockIndex);

        return "pages/community/post_view";
    }
}
