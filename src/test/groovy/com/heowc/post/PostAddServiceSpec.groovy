package com.heowc.post

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class PostAddServiceSpec extends Specification {

    @Shared service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimplePostAddService(repository)
    }

    def "add(PostRequest) 성공"() {
        given:
        def postRequest = new PostRequest("제목 1", "본문 1")

        when:
        def postOp = service.add(postRequest)

        then:
        with(postOp) {
            title == postRequest.title
            content == postRequest.content
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
