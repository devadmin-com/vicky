package com.devadmin.jira;

import java.lang.Throwable;

/**
 * A generic JIRA client exception.
 */
public class JiraException extends Exception {

    public JiraException(String msg) {
        super(msg);
    }

    public JiraException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

