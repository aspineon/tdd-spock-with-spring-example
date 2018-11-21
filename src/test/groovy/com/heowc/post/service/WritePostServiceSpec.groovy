package com.heowc.post.service

import com.heowc.config.TestAuditorAware
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForWrite
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@Import(TestAuditorAware.class)
class WritePostServiceSpec extends Specification {

    @Shared
            service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleWritePostService(repository)
    }

    def "write(PostRequest) 성공"() {
        given:
        def postRequest = new PostForWrite("제목 1", "본문 1")

        when:
        def post = service.write(postRequest)

        then:
        with(post, Post.class) {
            title == postRequest.title
            content == postRequest.content
            createdBy == "heowc"
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
