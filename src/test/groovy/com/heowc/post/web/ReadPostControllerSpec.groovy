package com.heowc.post.web

import com.heowc.post.domain.Post
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReadPostControllerSpec extends Specification {

    @Autowired
    PostRepository repository

    @LocalServerPort
    def port

    def restTemplate

    def setup() {
        def uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost")
                .port(port)
                .build()

        restTemplate = new RestTemplateBuilder().rootUri(uriComponents.toUriString()).build()
    }

    def "없는 id를 조회하여 HttpStatus(404)를 반환하며 실패"() {
        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, 1L)

        then:
        entity.statusCode == HttpStatus.NOT_FOUND
    }

    def "id를 입력하지 않아 HttpStatus(400)를 반환하며 실패"() {
        given:
        repository.save(new Post(1L, "제목", "본문", "heowc", null,
                null))
        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, 1L)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "해당 id에 대한 게시글이 존재하므로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def post = new Post(1L, "제목", "본문", "heowc", null, null)
        repository.save(post)

        when:
        def entity = restTemplate.getForEntity("/posts/{id}", Post.class, 1L)

        then:
        entity.statusCode == HttpStatus.OK
        with(entity.getBody(), Post.class) {
            post.id == id
            post.title == title
            post.content == content
            post.createdBy == createdBy
        }
    }
}
