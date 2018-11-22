package com.heowc.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public abstract class SessionUtils {

    private static final int DEFAULT_SCOPE = RequestAttributes.SCOPE_SESSION;

    public static String getId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    public static void setAttribute(String name, Object value) {
        RequestContextHolder.currentRequestAttributes().setAttribute(name, value, DEFAULT_SCOPE);
    }

    public static Object getAttribute(String name) {
        return RequestContextHolder.currentRequestAttributes().getAttribute(name, DEFAULT_SCOPE);
    }

    public static void removeAttribute(String name) {
        RequestContextHolder.currentRequestAttributes().removeAttribute(name, DEFAULT_SCOPE);
    }
}
