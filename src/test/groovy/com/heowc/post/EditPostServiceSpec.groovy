package com.heowc.post

import com.heowc.config.TestConfig
import com.heowc.config.TestConstant
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
        def postRequest = new PostRequest("제목", "본문")
        def post = repository.save(postRequest.toPost())

        when:
        service.edit(new Post(post.id, "수정된 제목", "수정된 본문", TestConstant.ID + 1, null, null))

        then:
        thrown(AccessDeniedException.class)
    }

    def "동일한 글쓴이 ID가 수정하여 성공"() {
        given:
        def postRequest = new PostRequest("제목", "본문")
        def post = repository.save(postRequest.toPost())

        when:
        def updatedPost = service.edit(new Post(post.id, "수정된 제목", "수정된 본문", TestConstant.ID, null, null))

        then:
        def byId = repository.findById(updatedPost.id)
        updatedPost.title == byId.get().title
        updatedPost.content == byId.get().content
        updatedPost.createdBy == byId.get().createdBy
    }

    def cleanup() {
        repository.deleteAll()
    }
}
