package com.devadmin.jira;

import org.apache.http.HttpRequest;

public interface ICredentials {

    void initialize(RestClient client) throws JiraException;
    /**
     * Sets the Authorization header for the given request.
     *
     * @param req HTTP request to authenticate
     */
    void authenticate(HttpRequest req);

    /**
     * Gets the logon name representing these credentials.
     *
     * @return logon name as a string
     */
    String getLogonName();

    void logout(RestClient client) throws JiraException;
}

