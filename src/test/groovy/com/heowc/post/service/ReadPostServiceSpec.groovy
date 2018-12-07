package com.heowc.post.service

import com.heowc.post.domain.Post
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class ReadPostServiceSpec extends Specification {

    @Shared
    def service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleReadPostService(repository)
    }

    def "findById() 성공"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null, null))

        when:
        def byId = service.findById(post.id)

        then:
        with(byId, Post.class) {
            post.title == title
            post.content == content
            post.createdBy == createdBy
        }
    }

    def "findById() 없는 데이터 검색로 인한 실패"() {
        given:
        def UNKNOWN_ID = 1L

        when:
        service.findById(UNKNOWN_ID)

        then:
        thrown(NoSuchElementException)
    }


    def cleanup() {
        repository.deleteAll()
    }
}
