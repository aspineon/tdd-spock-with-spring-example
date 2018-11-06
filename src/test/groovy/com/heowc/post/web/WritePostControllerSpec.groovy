package com.heowc.post.web

import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WritePostControllerSpec extends Specification {

    @Autowired
    PostRepository repository

    @Autowired
    TestRestTemplate restTemplate

    def "body가 비어있어 HttpStatus(400)를 반환하며 실패"() {

    }
    
    def "제목이 비어있어 HttpStatus(400)를 반환하며 실패"() {

    }

    def "제목이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {

    }

    def "내용이 비어있어 HttpStatus(400)를 반환하며 실패"() {

    }

    def "내용이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {

    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {

    }
}
