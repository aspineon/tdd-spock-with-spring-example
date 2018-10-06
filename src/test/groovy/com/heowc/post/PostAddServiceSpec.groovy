package com.heowc.post

import spock.lang.Specification

class PostAddServiceSpec extends Specification {

    def service = new SimplePostAddService(null)

    def "add(PostRequest) 성공"() {
        given:
        def postRequest = new PostRequest("제목 1", "본문 1")

        when:
        def postOp = service.add(postRequest)

        then:
        with(postOp) {
            get().title == postRequest.title
            get().content == postRequest.content
        }
    }
}
