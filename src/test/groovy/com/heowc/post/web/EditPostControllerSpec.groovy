package com.heowc.post.web

import com.heowc.config.TestConfig
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForEdit
import com.heowc.post.domain.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.IntStream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class EditPostControllerSpec extends Specification {

    @Autowired
    PostRepository repository

    @Autowired
    TestRestTemplate restTemplate

    def "body가 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<PostForEdit> httpEntity = new HttpEntity<>(headers)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "제목이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(1L, null, "본문")
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "제목이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def title = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForEdit(1L, title, "본문")
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "내용이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(1L, "제목", null)
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "내용이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def content = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForEdit(1L, "제목", content)
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "없는 Post를 수정하려고 하여 HttpStatus(404)를 반환하며 실패"() {
        given:
        def UNKNOWN_ID = -1L
        def request = new PostForEdit(UNKNOWN_ID, "제목", "본문")
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.NOT_FOUND
    }

    def "본인의 Post가 아닌 다른 Post를 수정하려고 하여 HttpStatus(403)를 반환하며 실패"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "test", null,
                null))

        and:
        def request = new PostForEdit(post.id, "수정된 제목", "수정된 본문")
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.FORBIDDEN
    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null,
                null))

        and:
        def request = new PostForEdit(post.id, "수정된 제목", "수정된 본문")
        def httpEntity = new HttpEntity<>(request)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.PUT, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.OK
        with(entity.body, Post.class) {
            request.title == title
            request.content == content
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
