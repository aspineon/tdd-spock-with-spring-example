package com.heowc.post.service

import com.heowc.post.domain.AccessDeniedException
import com.heowc.post.domain.Post
import com.heowc.post.domain.PostForEdit
import com.heowc.post.domain.PostRepository
import com.heowc.util.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletWebRequest
import spock.lang.Specification

@SpringBootTest
class EditPostServiceSpec extends Specification {

    def service

    @Autowired
    PostRepository repository

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));
        service = new SimpleEditPostService(repository)
    }

    def "동일한 글쓴이 ID가 아니므로 실패"() {
        given:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null, null))

        when:
        service.edit(new PostForEdit(post.id, "수정된 제목", "수정된 본문", "heowc" + 1))

        then:
        thrown(AccessDeniedException.class)
    }

    def "없는 게시물을 수정하려고 하므로 실패"() {
        given:
        SessionUtils.setAttribute("ID", "heowc")

        and:
        def UNKNOWN_ID = -1L

        when:
        service.edit(new PostForEdit(UNKNOWN_ID, "수정된 제목", "수정된 본문", "heowc"))

        then:
        thrown(NoSuchElementException.class)
    }

    def "동일한 글쓴이 ID가 수정하여 성공"() {
        given:
        SessionUtils.setAttribute("ID", "heowc")

        and:
        def post = repository.save(new Post(null, "제목", "본문", "heowc", null, null))

        when:
        def updatedPost = service.edit(new PostForEdit(post.id, "수정된 제목", "수정된 본문", "heowc"))

        then:
        def byIdOp = repository.findById(updatedPost.id)
        with(byIdOp.get(), Post.class) {
            updatedPost.title == title
            updatedPost.content == content
            updatedPost.createdBy == createdBy
        }
    }

    def cleanup() {
        repository.deleteAll()
        SessionUtils.removeAttribute("ID")
    }
}
