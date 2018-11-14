package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Status.
 *
 * @author pldupont
 */
public class Status extends AgileResource {

    private String description;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Status(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    @Override
    void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);

        this.description = Field.getString(json.get("description"));
    }

    public String getDescription() {
        return description;
    }
}
