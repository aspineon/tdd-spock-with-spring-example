package com.heowc.post.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.heowc.post.domain.Post
import com.heowc.post.service.ReadPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.is
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = ReadPostController.class)
class ReadPostControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    ReadPostService service

    @Autowired
    ObjectMapper mapper

    def "없는 id를 조회하여 HttpStatus(404)를 반환하며 실패"() {
        given:
        service.findById(_) >> { throw new NoSuchElementException() }

        and:
        def EMPTY_ID = 1L

        expect:
        mvc.perform(get("/posts/{id}", EMPTY_ID)
                .accept(APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound())
    }

    def "올바르지 않은 id를 조회하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        service.findById(_) >> { throw new NoSuchElementException() }

        and:
        def EMPTY_ID = "empty"

        expect:
        mvc.perform(get("/posts/{id}", EMPTY_ID)
                .accept(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
    }

    def "해당 id에 대한 게시글이 존재하므로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def willReturn = new Post(1L, "제목", "본문", "heowc", null, null)
        service.findById(_) >> willReturn

        expect:
        mvc.perform(get("/posts/{id}", willReturn.id)
                .accept(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath('$.id', is(willReturn.id.intValue())))
            .andExpect(jsonPath('$.title', is(willReturn.title)))
            .andExpect(jsonPath('$.content', is(willReturn.content)))
            .andExpect(jsonPath('$.createdBy', is(willReturn.createdBy)))
    }

    @TestConfiguration
    static class MockConfig {

        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        ReadPostService readPostService() {
            return detachedMockFactory.Mock(ReadPostService)
        }
    }
}
