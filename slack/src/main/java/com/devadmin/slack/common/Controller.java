package com.devadmin.slack.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for different Event types in Slack RTM API, Fb Messenger Bot API.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    EventType[] events() default EventType.MESSAGE;

    /**
     * The regex expression to be compiled
     * @return default pattern
     */
    String pattern() default "";

    /**
     * Regex pattern match flags, a bit mask that may include
     * {@link java.util.regex.Pattern#CASE_INSENSITIVE},
     * {@link java.util.regex.Pattern#MULTILINE},
     * {@link java.util.regex.Pattern#DOTALL},
     * {@link java.util.regex.Pattern#UNICODE_CASE},
     * {@link java.util.regex.Pattern#CANON_EQ},
     * {@link java.util.regex.Pattern#UNIX_LINES},
     * {@link java.util.regex.Pattern#LITERAL},
     * {@link java.util.regex.Pattern#UNICODE_CHARACTER_CLASS} and
     * {@link java.util.regex.Pattern#COMMENTS}
     *
     * @return pattern flags
     */
    int patternFlags() default 0;

    String next() default "";
}
