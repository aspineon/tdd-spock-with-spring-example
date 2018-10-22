package com.heowc.post


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReadPostServiceSpec extends Specification {

    def service

    @Autowired
    PostRepository repository

    def setup() {
        service = new SimpleReadPostService(repository)
    }

    def "findById() 성공"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc",null, null))

        when:
        def byId = service.findById(post.id)

        then:
        post.title == byId.title
        post.content == byId.content
        post.createdBy == byId.createdBy
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
