package com.heowc.config;

import com.heowc.Application;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;

@Configuration
@EnableJdbcRepositories(basePackageClasses = Application.class)
@EnableJdbcAuditing()
@Import(JdbcConfiguration.class)
public class SimpleJdbcConfiguation {
}
