package com.heowc.post.service

import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForWrite
import com.heowc.post.domain.PostRepository
import com.heowc.util.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletWebRequest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class WritePostServiceSpec extends Specification {

    @Shared
    def service

    @Autowired
    PostRepository repository

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest()
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request))
        service = new SimpleWritePostService(repository)
    }

    def "write(PostRequest) 성공"() {
        given:
        SessionUtils.setAttribute("ID", "heowc")

        and:
        def postRequest = new PostForWrite("제목 1", "본문 1")

        when:
        def post = service.write(postRequest)

        then:
        with(post, Post.class) {
            title == postRequest.title
            content == postRequest.content
            createdBy == "heowc"
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
