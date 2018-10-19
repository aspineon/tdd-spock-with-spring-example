package com.heowc.post

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RemovePostServiceSpec extends Specification {

    def service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleEditPostService(repository)
    }
    
    def "동일한 글쓴이 ID가 아니므로 실패"() {

    }

    def "없는 게시물을 지우려고 하므로 실패"() {

    }
    
    def "동일한 글쓴이 ID가 삭제하여 성공"() {
        
    }

    def cleanup() {
        repository.deleteAll()
    }
}
