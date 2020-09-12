package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class PostResponseDto {
    private final long id;
    private String title;
    private String content;
    private String author;
    private String authorEmail;
    private String createdDate;
    private String modifiedDate;
    private String category;
    private int viewCnt;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.authorEmail = entity.getAuthorEmail();

        this.viewCnt = entity.getViewCnt();

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.now();              // 오늘 날짜면 시간만 나오게 하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if( startDate.format(date).equals(entity.getCreatedDate().format(date)) ){
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        String createdDateTime = entity.getCreatedDate().format(formatter);
        this.createdDate = createdDateTime;

        String modifiedDateTime = entity.getCreatedDate().format(formatter);
        this.modifiedDate = modifiedDateTime;
    }
}
