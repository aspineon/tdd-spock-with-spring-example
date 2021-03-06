package com.heowc.post.service;

import com.heowc.post.domain.AccessDeniedException;
import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;
import com.heowc.post.domain.PostRepository;
import com.heowc.util.SessionUtils;
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
public class EditPostServiceTest {

    private EditPostService service;

    @Autowired
    private PostRepository repository;

    @Before
    public void before_setup() {
        service = new SimpleEditPostService(repository);
    }

    @Test
    public void test_동일한_글쓴이ID가_아니므로_실패() {
        // given
        SessionUtils.setAttribute("ID", "test");
        Post post = repository.save(new Post(null, "제목", "본문", null, null, null));
        SessionUtils.setAttribute("ID", "heowc");

        // when-then
        assertThatThrownBy(
                () -> service.edit(new PostForEdit(post.getId(), "수정된 제목", "수정된 본문", null))
        ).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void test_없는_게시물을_수정하려고_하므로_실패() {
        // given
        SessionUtils.setAttribute("ID", "heowc");
        final long UNKNOWN_ID = -1L;

        // when-then
        assertThatThrownBy(
                () -> service.edit(new PostForEdit(UNKNOWN_ID, "수정된 제목", "수정된 본문", "heowc"))
        ).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void test_동일한_글쓴이ID가_수정하여_성공() {
        // given
        SessionUtils.setAttribute("ID", "heowc");
        Post post = repository.save(new Post(null, "제목", "본문", "heowc", null, null));

        // when
        Post updatedPost = service.edit(new PostForEdit(post.getId(), "수정된 제목", "수정된 본문", "heowc"));

        // then
        Optional<Post> byId = repository.findById(updatedPost.getId());
        assertThat(byId).hasValueSatisfying(p -> {
            assertThat(p).hasFieldOrPropertyWithValue("title", updatedPost.getTitle());
            assertThat(p).hasFieldOrPropertyWithValue("content", updatedPost.getContent());
            assertThat(p).hasFieldOrPropertyWithValue("createdBy", updatedPost.getCreatedBy());
        });
    }

    @After
    public void after_cleanup() {
        repository.deleteAll();
        SessionUtils.removeAttribute("ID");
    }
}
