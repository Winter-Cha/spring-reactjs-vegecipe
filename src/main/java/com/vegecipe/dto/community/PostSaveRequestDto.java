package com.vegecipe.dto.community;

import com.vegecipe.domain.community.Category;
import com.vegecipe.domain.community.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {
    private Category category;
    private String title;
    private String password;
    private String content;
    private String author;
    private String authorIp;

    @Builder
    public PostSaveRequestDto(Category category, String title, String password, String content, String author, String authorIp) {
        this.category = category;
        this.title = title;
        this.password = password;
        this.content = content;
        this.author = author;
        this.authorIp = authorIp;
    }

    public Post toEntity() {
        return Post.builder()
                .category(category)
                .title(title)
                .password(password)
                .content(content)
                .author(author)
                .authorIp(authorIp)
                .build();
    }
}
