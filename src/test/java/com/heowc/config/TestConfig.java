package com.heowc.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(TestAuditorAware.class)
public class TestConfig {

}
