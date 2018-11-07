package com.heowc.post.domain

import spock.lang.Specification

class PostRequestSpec extends Specification {

    def "PostRequest를 Post로 변환 성공"() {
        given:
        def request = new PostRequest("제목", "본문")

        when:
        def post = request.toPost()

        then:
        with(post, Post.class) {
            title == request.title
            content == request.content
        }
    }
}
