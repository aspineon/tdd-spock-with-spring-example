package com.heowc.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WritePostServiceTest {

    private WritePostService service;

    @Autowired
    private PostRepository repository;

    @Before
    public void setup() {
        service = new SimpleWritePostService(repository);
    }

    @Test
    public void test_write_성공() {
        // given
        PostRequest postRequest = new PostRequest("제목 1", "본문 1");

        // when
        Post post = service.write(postRequest);

        // then
        assertThat(post).hasFieldOrPropertyWithValue("title", postRequest.getTitle());
        assertThat(post).hasFieldOrPropertyWithValue("content", postRequest.getContent());
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }
}
