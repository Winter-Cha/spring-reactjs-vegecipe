package com.vegecipe.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BooksUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public BooksUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
