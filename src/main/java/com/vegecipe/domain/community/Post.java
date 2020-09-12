package com.vegecipe.domain.community;

import com.vegecipe.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.CompositeName;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 10, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String authorEmail;

    @Column(columnDefinition = "integer default 0")
    private int viewCnt;

    @Builder
    public Post(Category category, String title, String password, String content, String author, String authorEmail) {
        this.category = category;
        this.title = title;
        this.password = password;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getSubjectKey() {
        return this.category.getKey();
    }

    public void updateViewCnt(Long id) {
        this.viewCnt += 1;
    }
}
