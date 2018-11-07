package com.heowc.post.web


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EditPostControllerSpec extends Specification {

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

    def "없는 Post를 수정하려고 하여 HttpStatus(404)를 반환하며 실패"() {

    }

    def "본인의 Post가 아닌 다른 Post를 수정하려고 하여 HttpStatus(403)를 반환하며 실패"() {

    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {

    }

    def cleanup() {

    }
}
