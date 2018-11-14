package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

public class IssueType extends AgileResource {

    private String description;
    private boolean subTask;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public IssueType(RestClient restclient, JSONObject json) throws JiraException {
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
        this.subTask = Field.getBoolean(json.get("subtask"));
    }

    public String getDescription() {
        return description;
    }

    public boolean isSubTask() {
        return subTask;
    }
}
