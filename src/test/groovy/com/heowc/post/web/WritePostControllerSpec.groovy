package com.heowc.post.web


import com.heowc.post.domain.Post
import com.heowc.post.domain.PostRepository
import com.heowc.post.domain.PostRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.IntStream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WritePostControllerSpec extends Specification {

    @Autowired
    PostRepository repository

    @Autowired
    TestRestTemplate restTemplate

    def "body가 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(headers)

        when:
        def entity = restTemplate.exchange("/posts", HttpMethod.POST, httpEntity, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "제목이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostRequest(null, "본문")

        when:
        def entity = restTemplate.postForEntity("/posts", request, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "제목이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def title = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostRequest(title, "본문")

        when:
        def entity = restTemplate.postForEntity("/posts", request, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "내용이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostRequest("제목", null)

        when:
        def entity = restTemplate.postForEntity("/posts", request, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "내용이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def content = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostRequest("제목", content)

        when:
        def entity = restTemplate.postForEntity("/posts", request, Post.class)

        then:
        entity.statusCode == HttpStatus.BAD_REQUEST
    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def request = new PostRequest("제목", "본문")

        when:
        def entity = restTemplate.postForEntity("/posts", request, Post.class)

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
