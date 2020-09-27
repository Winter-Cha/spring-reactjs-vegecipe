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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HttpSession httpSession;

    @GetMapping("/community")
    public String community(Model model, @RequestParam(value = "srhText", required = false) String srhText , @RequestParam(value = "srhType", required = false) String srhType) {
        // 총 게시글 수
        Long total = Long.valueOf(0);
        HashMap srhTypeMap = new HashMap();
        if(srhText == null) srhText = "";
        if("TC".equals(srhType)){
            total = postRepository.findByTitleContainingOrContentContaining(srhText,srhText);
            srhTypeMap.put("TC", "checked");
        } else {
            total = postRepository.findByTitleContaining(srhText);
            srhTypeMap.put("T", "checked");
        }
        model.addAttribute("postTotCnt",total);
        model.addAttribute("srhText", srhText);
        model.addAttribute("srhType", srhTypeMap);

        // SEO를 위한 데이터
        model.addAttribute("seo_title", "커뮤니티");
        model.addAttribute("seo_desc", "채식인들이 모여 편안하게 담소를 나누는 지극히 채식인에 편향된 커뮤니티입니다. ");
        model.addAttribute("seo_image", "");

        return "pages/community/post_list";
    }

    @GetMapping("/posts")
    public String community(Model model, Pageable pageable, @RequestParam String srhText, @RequestParam String srhType, @RequestParam String sort, @RequestParam Long pageBlockCnt, @RequestParam Long pageBlockIndex  ) {
        Page<Post> pagedListHolder = null;

        // 공지글 제외 리스트
        List<Long> idList = Arrays.asList(new Long[]{Long.valueOf(1), Long.valueOf(2), Long.valueOf(3), Long.valueOf(4), Long.valueOf(5)});

        if("T".equals(srhType)){
            pagedListHolder = postRepository.findByTitleContainingAndIdNotIn(srhText,idList, pageable);
        } else {
            pagedListHolder = postRepository.findByTitleContainingOrContentContainingAndIdNotIn(srhText, srhText, idList, pageable);
        }

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

        // page가 0이면 공지 뽑기
        List<PostListResponseDto> notice = null;
        if(pageable.getPageNumber() == 0) {
            notice = postRepository.findByIdIn(idList).stream()
                    .map(post -> {
                        PostListResponseDto prd = new PostListResponseDto(post);
                        int cnt = commentRepository.findByPostId(prd.getId()).size();
                        if (cnt > 0) prd.setCommentCnt(cnt + "");
                        return prd;
                    }).collect(Collectors.toList());
            model.addAttribute("posts", Stream.concat(notice.stream(),posts.stream()).collect(Collectors.toList()));
        } else {
            model.addAttribute("posts", posts);
        }
        model.addAttribute("totCnt", pagedListHolder.getTotalElements());
        model.addAttribute("totPageCnt", pagedListHolder.getTotalPages());
        model.addAttribute("pageable", pagedListHolder.getPageable());
        model.addAttribute("sort", sort);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("page-block-cnt", pageBlockCnt);
        model.addAttribute("page-block-index", pageBlockIndex);
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
    public String bookView(@PathVariable Long id, Model model , Pageable pageable, @RequestParam String srhText, @RequestParam String srhType, @RequestParam String sort, @RequestParam Long pageBlockCnt, @RequestParam Long pageBlockIndex ) {
        postService.updateViewCnt(id);
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);
        Long total = Long.valueOf(0);
        HashMap srhTypeMap = new HashMap();
        if(srhText == null) srhText = "";
        if("TC".equals(srhType)){
            total = postRepository.findByTitleContainingOrContentContaining(srhText,srhText);
            srhTypeMap.put("TC", "checked");
        } else {
            total = postRepository.findByTitleContaining(srhText);
            srhTypeMap.put("T", "checked");
        }

        model.addAttribute("postTotCnt", total);
        model.addAttribute("srhText", srhText);
        model.addAttribute("srhType", srhTypeMap);

        model.addAttribute("sort", sort);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("page-block-cnt", pageBlockCnt);
        model.addAttribute("page-block-index", pageBlockIndex);

        String desc = "";
        int len = dto.getContent().length();
        if(len > 200){
            desc = dto.getContent().substring(0,200);
        }else{
            desc = dto.getContent();
        }
        
        // SEO를 위한 데이터
        model.addAttribute("seo_title", dto.getTitle());
        model.addAttribute("seo_desc", desc);
        model.addAttribute("seo_image", "");

        return "pages/community/post_view";
    }
}
