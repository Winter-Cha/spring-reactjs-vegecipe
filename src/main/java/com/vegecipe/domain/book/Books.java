package com.vegecipe.domain.book;

import com.vegecipe.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Books extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String authorEmail;

    @Column(columnDefinition = "integer default 0")
    private int viewCnt;

    @Builder
    public Books( String title, String content, String author, String authorEmail) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateViewCnt(Long id) {
        this.viewCnt += 1;
    }
}
