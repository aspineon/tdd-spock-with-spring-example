package com.heowc.post.service

import com.heowc.config.TestConfig
import com.heowc.post.PostRepository
import com.heowc.post.PostRequest
import com.heowc.post.service.SimpleWritePostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@Import(TestConfig.class)
class WritePostServiceSpec extends Specification {

    @Shared service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleWritePostService(repository)
    }

    def "write(PostRequest) 성공"() {
        given:
        def postRequest = new PostRequest("제목 1", "본문 1")

        when:
        def postOp = service.write(postRequest)

        then:
        with(postOp) {
            title == postRequest.title
            content == postRequest.content
            createdBy == "heowc"
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}