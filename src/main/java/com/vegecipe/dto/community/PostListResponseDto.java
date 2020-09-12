package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String createdDate;
    private String modifiedDate;
    private String category;
    private int viewCnt;
    private String commentCnt;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.category = entity.getCategory().getTitle();
        this.viewCnt = entity.getViewCnt();

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.now();              // 오늘 날짜면 시간만 나오게 하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if( startDate.format(date).equals(entity.getCreatedDate().format(date)) ){
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        String createdDateTime = entity.getCreatedDate().format(formatter);
        this.createdDate = createdDateTime;

        String modifiedDateTime = entity.getCreatedDate().format(formatter);
        this.modifiedDate = modifiedDateTime;
    }

    public void setCommentCnt(String cnt){
        this.commentCnt = cnt;
    }
}
