package com.heowc.post.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.heowc.post.domain.AccessDeniedException
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForEdit
import com.heowc.post.service.EditPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.util.stream.Collectors
import java.util.stream.IntStream

import static org.hamcrest.Matchers.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = EditPostController.class)
class EditPostControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    EditPostService service

    @Autowired
    ObjectMapper mapper

    def "body가 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def EMPTY_BODY = ""

        expect:
        mvc.perform(put("/posts")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(EMPTY_BODY))
            .andExpect(status().isBadRequest())
    }

    def "id가 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(null, "제목", "본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }

    def "올바른 id가 아니므로 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(-1L, "제목", "본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }

    def "제목이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(1L, null, "본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }

    def "제목이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def title = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForEdit(1L, title, "본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }

    def "내용이 비어있어 HttpStatus(400)를 반환하며 실패"() {
        given:
        def request = new PostForEdit(1L, "제목", null, "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())    }

    def "내용이 255자를 초과하여 HttpStatus(400)를 반환하며 실패"() {
        given:
        def content = IntStream.range(0, 256).mapToObj({ value -> "1" }).collect(Collectors.joining())
        def request = new PostForEdit(1L, "제목", content, "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }

    def "없는 Post를 수정하려고 하여 HttpStatus(404)를 반환하며 실패"() {
        given:
        service.edit(_) >> { throw new NoSuchElementException() }

        and:
        def request = new PostForEdit(1L, "제목", "본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
    }

    def "본인의 Post가 아닌 다른 Post를 수정하려고 하여 HttpStatus(403)를 반환하며 실패"() {
        given:
        service.edit(_) >> { throw new AccessDeniedException() }

        and:
        def request = new PostForEdit(1L, "수정된 제목", "수정된 본문", "test")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
    }

    def "올바른 데이터로 HttpStatus(200)과 Post 반환하며 성공"() {
        given:
        def willReturn = new Post(1L, "수정된 제목", "수정된 본문", "heowc", null, null)
        service.edit(_) >> willReturn

        and:
        def request = new PostForEdit(1L, "수정된 제목", "수정된 본문", "heowc")

        expect:
        mvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(request)))
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
        EditPostService editPostService() {
            return detachedMockFactory.Mock(EditPostService)
        }
    }
}
