package com.heowc.util

import com.heowc.config.TestConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class SessionUtilsTest extends Specification {

    def '더미 세션 추가'() {
        when:
        SessionUtils.setAttribute("ID", "spock-with-spring")

        then:
        def jSessionId = RequestContextHolder.currentRequestAttributes().getAttribute("ID", RequestAttributes.SCOPE_SESSION)
        jSessionId == "spock-with-spring"
    }

    def '더미 세션 수정'() {
        given:
        SessionUtils.setAttribute("ID", "spock-with-spring")

        when:
        SessionUtils.setAttribute("ID", "spock")

        then:
        def jSessionId = RequestContextHolder.currentRequestAttributes().getAttribute("ID", RequestAttributes.SCOPE_SESSION)
        jSessionId == "spock"
    }

    def '더미 세션 삭제'() {
        given:
        SessionUtils.setAttribute("ID", "spock-with-spring")

        when:
        SessionUtils.removeAttribute("ID")

        then:
        def jSessionId = RequestContextHolder.currentRequestAttributes().getAttribute("ID", RequestAttributes.SCOPE_SESSION)
        jSessionId == null
    }
}
