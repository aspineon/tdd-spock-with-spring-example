package com.heowc.post.web


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReadPostControllerSpec extends Specification {

    def "없는 id를 조회하여 HttpStatus(400)를 반환하며 실패"() {
//        given:
//        when:
//        then:
    }

    def "id를 입력하지 않아 HttpStatus(404)를 반환하며 실패"() {
//        given:
//        when:
//        then:
    }

    def "해당 id에 대한 게시글이 존재하므로 Post 반환하며 성공"() {
//        given:
//        when:
//        then:
    }
}
