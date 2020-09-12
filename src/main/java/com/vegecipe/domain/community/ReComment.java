package com.vegecipe.domain.community;

import com.vegecipe.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ReComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String authorEmail;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public ReComment( String content, String author, String authorEmail) {
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
    }

    public void update(String title, String content) {
        this.content = content;
    }

}
