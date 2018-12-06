package com.heowc.post.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForWrite
import com.heowc.post.service.EditPostService
import com.heowc.post.service.RemovePostService
import com.heowc.post.service.WritePostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.util.stream.Collectors
import java.util.stream.IntStream

import static org.hamcrest.Matchers.*
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = WritePostController.class)
class WritePostControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    WritePostService service

    @Autowired
    ObjectMapper mapper

    def "body가 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def EMPTY_BODY = ""

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(EMPTY_BODY))
            .andExpect(status().isBadRequest())
    }

    def "제목이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForWrite(null, "본문")

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
    }

    def "제목이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def title = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForWrite(title, "본문")

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
    }

    def "내용이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForWrite("제목", null)

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
    }

    def "내용이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def content = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForWrite("제목", content)

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def willReturn = new Post(null, "제목", "본문", null, null, null)
        service.write(_) >> willReturn

        and:
        def request = new PostForWrite("제목", "본문")

        expect:
        mvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.title', is(willReturn.title)))
            .andExpect(jsonPath('$.content', is(willReturn.content)))
    }

    @TestConfiguration
    static class MockConfig {

        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        WritePostService writePostService() {
            return detachedMockFactory.Mock(WritePostService)
        }
    }

}
