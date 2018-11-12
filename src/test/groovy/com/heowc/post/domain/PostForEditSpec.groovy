package com.heowc.post.domain

import spock.lang.Specification

class PostForEditSpec extends Specification {

    def "PostForEdit를 Post로 변환 성공"() {
        given:
        def request = new PostForEdit(1L, "제목", "본문", "heowc")

        when:
        def post = request.toPost()

        then:
        with(post, Post.class) {
            id == request.id
            title == request.title
            content == request.content
            createdBy == request.createdBy
        }
    }
}
