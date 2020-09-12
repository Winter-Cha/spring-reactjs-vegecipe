/*
package com.vegecipe.domain.community;

import com.vegecipe.domain.book.Books;
import com.vegecipe.domain.book.BooksRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksRepositoryTest {

    @Autowired
    BooksRepository booksRepository;

    @After
    public void cleanup() {
        booksRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트본문";

        booksRepository.save(Books.builder()
                .title(title)
                .content(content)
                .author("author")
                .authorEmail("diskdoo@gmail.com")
                .build());

        //when
        List<Books> BooksList = booksRepository.findAll();

        //then
        Books books = BooksList.get(0);
        assertThat(books.getTitle()).isEqualTo(title);
        assertThat(books.getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2020,3,7,0,0,0);
        booksRepository.save(Books.builder()
                .title("title")
                .content("content")
                .author("author")
                .authorEmail("diskdoo@gmail.com")
                .build());

        //when
        List<Books> BooksList = booksRepository.findAll();

        //then
        Books books = BooksList.get(0);
        assertThat(books.getCreatedDate()).isAfter(now);
        assertThat(books.getModifiedDate()).isAfter(now);
    }
}
*/
