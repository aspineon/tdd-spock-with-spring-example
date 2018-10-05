package com.heowc.post

import spock.lang.Specification

class AddPostServiceTest extends Specification {

    def service

    def setup() {
        service = new AddPostService() {
            @Override
            Optional<Post> add(PostRequest postRequest) {
                return Optional.of(new Post(null, "제목 1", "본문 1", null, null))
            }
        }
    }

    def "add(PostRequest) 성공"() {
        given:
        def postRequest = new PostRequest("제목 1", "본문 1")

        when:
        def postOp = service.add(postRequest)

        then:
        postOp.get().title == postRequest.title
        postOp.get().content == postRequest.content
    }
}
