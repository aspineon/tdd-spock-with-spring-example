package com.heowc.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadPostServiceTest {

    private ReadPostService service;

    @Autowired
    private PostRepository repository;

    @Before
    public void setup() {
        service = new SimpleReadPostService(repository);
    }

    @Test
    public void test_findById_성공() {
        // given
        Post post = repository.save(new Post(null, "제목", "본문", "heowc",null, null));

        // when
        Post byId = service.findById(post.getId());

        // then
        assertThat(post).hasFieldOrPropertyWithValue("title", byId.getTitle());
        assertThat(post).hasFieldOrPropertyWithValue("content", byId.getContent());
        assertThat(post).hasFieldOrPropertyWithValue("createdBy", byId.getCreatedBy());
    }

    public void test_findById_없는_데이터_검색로_인한_실패() {
        // given
        final long UNKNOWN_ID = 1L;

        // when-then
        assertThatThrownBy(() -> service.findById(UNKNOWN_ID)).isInstanceOf(NoSuchElementException.class);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }
}
