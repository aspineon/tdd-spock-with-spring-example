package com.heowc.post.service;

import com.heowc.post.domain.AccessDeniedException;
import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemovePostServiceTest {

    private RemovePostService service;

    @Autowired
    private PostRepository repository;

    @Before
    public void before_setup() {
        service = new SimpleRemovePostService(repository);
    }

    @Test
    public void test_동일한_글쓴이ID가_아니므로_실패() {
        // given
        Post post = repository.save(new Post(null, "제목", "본문", "heowc", null, null));

        // when-then
        assertThatThrownBy(
                () -> service.remove(new Post(post.getId(), null, null, "wonchul", null, null))
        ).isInstanceOf(AccessDeniedException.class);

        Optional<Post> byId = repository.findById(post.getId());
        assertThat(byId).hasValueSatisfying(p -> {
            assertThat(p).hasFieldOrPropertyWithValue("id", post.getId());
            assertThat(p).hasFieldOrPropertyWithValue("title", post.getTitle());
            assertThat(p).hasFieldOrPropertyWithValue("content", post.getContent());
            assertThat(p).hasFieldOrPropertyWithValue("createdBy", post.getCreatedBy());
        });
    }

    @Test
    public void test_없는_게시물을_지우려고_하므로_실패() {
        // given
        final long UNKNOWN_ID = -1L;

        // when-then
        assertThatThrownBy(
                () -> service.remove(new Post(UNKNOWN_ID, null, null, "heowc", null, null))
        ).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void test_동일한_글쓴이ID가_삭제하여_성공() {
        // given
        Post post = repository.save(new Post(null, "제목", "본문", "heowc", null, null));

        // when
        service.remove(new Post(post.getId(), null, null, "heowc", null, null));

        // then
        Optional<Post> byId = repository.findById(post.getId());
        assertThat(byId).isEmpty();
    }

    @After
    public void after_cleanup() {
        repository.deleteAll();
    }
}
