package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Category;
import com.vegecipe.domain.community.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private Category category;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(Category category, String title, String content, String author) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .category(category)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
