package com.heowc.post.service

import com.heowc.config.TestConfig
import com.heowc.post.domain.AccessDeniedException
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForEdit
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@SpringBootTest
@Import(TestConfig.class)
class EditPostServiceSpec extends Specification {

    def service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleEditPostService(repository)
    }

    def "동일한 글쓴이 ID가 아니므로 실패"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null,
                null))

        when:
        service.edit(new PostForEdit(post.id, "수정된 제목", "수정된 본문", "heowc" + 1))

        then:
        thrown(AccessDeniedException.class)
    }

    def "없는 게시물을 수정하려고 하므로 실패"() {
        given:
        def UNKNOWN_ID = -1L

        when:
        service.edit(new PostForEdit(UNKNOWN_ID, "수정된 제목", "수정된 본문", "heowc"))

        then:
        thrown(NoSuchElementException.class)
    }

    def "동일한 글쓴이 ID가 수정하여 성공"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null,
                null))

        when:
        def updatedPost = service.edit(new PostForEdit(post.id, "수정된 제목", "수정된 본문", "heowc"))

        then:
        def byIdOp = repository.findById(updatedPost.id)
        with(byIdOp.get(), Post.class) {
            updatedPost.title == title
            updatedPost.content == content
            updatedPost.createdBy == createdBy
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
