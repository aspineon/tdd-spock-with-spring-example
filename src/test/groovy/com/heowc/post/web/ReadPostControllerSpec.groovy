package com.heowc.post.web

import com.heowc.config.TestConfig
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostRepository
import com.heowc.util.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class ReadPostControllerSpec extends Specification {

    @Autowired
    PostRepository repository

    @Autowired
    TestRestTemplate restTemplate

    def "없는 id를 조회하여 HttpStatus(404)를 반환하며 실패"() {
        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, 1L)

        then:
        entity.statusCode == HttpStatus.NOT_FOUND
    }

    def "올바르지 않은 id를 조회하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        repository.save(new Post(null, "제목", "본문", "heowc", null,
                null))
        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, "s")

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "해당 id에 대한 게시글이 존재하므로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null, null))

        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, post.getId())

        then:
        entity.statusCode == HttpStatus.OK
        with(entity.body, Post.class) {
            post.id == id
            post.title == title
            post.content == content
            post.createdBy == createdBy
        }
    }

    def cleanup() {
        repository.deleteAll()
        SessionUtils.removeAttribute("ID")
    }
}
