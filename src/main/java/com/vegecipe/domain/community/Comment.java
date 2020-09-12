package com.vegecipe.domain.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vegecipe.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable =  false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

    @Column(length = 400, nullable = false)
    private String text;

    @Column(length = 10, nullable = false)
    private String password;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String authorIp;

    @Builder
    public Comment( String title, String text, String password, String author, String authorIp) {
        this.text = text;
        this.password = password;
        this.author = author;
        this.authorIp = authorIp;
    }

/*    public void update( String text) {
        this.text = text;
    }*/
}
