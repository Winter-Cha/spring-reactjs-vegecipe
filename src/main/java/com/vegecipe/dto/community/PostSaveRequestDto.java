package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Category;
import com.vegecipe.domain.community.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private Category category;
    private String title;
    private String password;
    private String content;
    private String author;
    private String authorEmail;

    @Builder
    public PostSaveRequestDto(Category category, String title, String password, String content, String author, String authorEmail) {
        this.category = category;
        this.title = title;
        this.password = password;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public Post toEntity() {
        return Post.builder()
                .category(category)
                .title(title)
                .password(password)
                .content(content)
                .author(author)
                .authorEmail(authorEmail)
                .build();
    }
}
