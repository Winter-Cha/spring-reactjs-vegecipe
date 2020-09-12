package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Comment;
import com.vegecipe.domain.community.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentListResponseDto {
    private Long id;
    private String text;
    private String author;
    private String createdDate;
    private String modifiedDate;

    public CommentListResponseDto(Comment entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.author = entity.getAuthor();

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
}
