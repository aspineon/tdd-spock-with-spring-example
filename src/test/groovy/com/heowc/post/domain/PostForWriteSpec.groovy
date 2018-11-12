package com.heowc.post.domain

import spock.lang.Specification

class PostForWriteSpec extends Specification {

    def "PostForWrite를 Post로 변환 성공"() {
        given:
        def request = new PostForWrite("제목", "본문")

        when:
        def post = request.toPost()

        then:
        with(post, Post.class) {
            title == request.title
            content == request.content
        }
    }
}
