package com.devadmin.jira.agile;

import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Priority.
 *
 * @author pldupont
 */
public class Priority extends AgileResource {

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Priority(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }
}
