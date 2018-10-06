package com.heowc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest(classes = Application)
class GroovyApplicationSpec extends Specification {

    @Autowired
    ApplicationContext context

    def "contextLoads"() {
        expect:
        context != null
    }
}
