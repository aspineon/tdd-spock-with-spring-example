package com.heowc.config;

import com.heowc.util.SessionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class SimpleAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SessionUtils.getAttribute("ID")).map(String.class::cast);
    }
}
