package com.heowc.post.domain

import spock.lang.Specification

class PostSpec extends Specification {

    def "Post의 title과 content 수정 성공"() {
        given:
        def post = new Post(1L, "제목", "본문", "heowc", null, null)
        def modifiedPost = new Post(1L, "수정된 제목", "수정된 본문", "heowc", null, null)

        when:
        post.changeFields(modifiedPost)

        then:
        with(post, Post.class) {
            id == modifiedPost.id
            title == modifiedPost.title
            content == modifiedPost.content
            createdBy == modifiedPost.createdBy
        }
    }

    def "Post를 생성한 사람이 동일하여 성공"() {
        expect:
        new Post(1L, "제목", "본문", createdBy, null, null).canNotAccess(modifiedBy) == canNotAccess

        where:
        createdBy | modifiedBy || canNotAccess
         "heowc"  |  "heowc"   || false
         "heowc"  |  "test"    || true

    }
}
