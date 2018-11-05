package com.heowc.post.service

import com.heowc.post.domain.AccessDeniedException
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RemovePostServiceSpec extends Specification {

    def service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleRemovePostService(repository)
    }
    
    def "동일한 글쓴이 ID가 아니므로 실패"() {
        given:
        def post = new Post(null, "제목", "본문", "heowc", null, null)
        repository.save(post)

        when:
        service.remove(new Post(post.id, "제목", "본문", "wonchul", null, null))

        then:
        thrown(AccessDeniedException.class)
        def byId = repository.findById(post.id)
        post.id == byId.get().id
        post.title == byId.get().title
        post.content == byId.get().content
        post.createdBy == byId.get().createdBy
    }

    def "없는 게시물을 지우려고 하므로 실패"() {
        given:
        def UNKNOWN_ID = -1L

        when:
        service.remove(new Post(UNKNOWN_ID, "제목", "본문", "heowc", null, null))

        then:
        thrown(NoSuchElementException.class)
    }
    
    def "동일한 글쓴이 ID가 삭제하여 성공"() {
        given:
        def post = new Post(null, "제목", "본문", "heowc", null, null)
        repository.save(post)

        when:
        service.remove(new Post(post.id, null, null, "heowc", null, null))

        then:
        notThrown(AccessDeniedException.class)
    }

    def cleanup() {
        repository.deleteAll()
    }
}
