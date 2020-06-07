package com.vegecipe.dto.book;

import com.vegecipe.domain.book.Books;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BooksSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String authorEmail;

    @Builder
    public BooksSaveRequestDto( String title, String content, String author, String authorEmail) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public Books toEntity() {
        return Books.builder()
                .title(title)
                .content(content)
                .author(author)
                .authorEmail(authorEmail)
                .build();
    }
}
