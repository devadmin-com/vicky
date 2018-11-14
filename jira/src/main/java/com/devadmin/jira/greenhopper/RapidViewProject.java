package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import com.devadmin.jira.Project;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper JIRA project.
 */
public class RapidViewProject extends GreenHopperResource {

    private String key = null;
    private String name = null;

    /**
     * Creates a project from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected RapidViewProject(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        key = Field.getString(map.get("key"));
        name = Field.getString(map.get("name"));
    }

    /**
     * Retrieves the full JIRA project.
     *
     * @return a Project
     *
     * @throws JiraException when the retrieval fails
     */
    public Project getJiraProject() throws JiraException {
        return Project.get(restclient, key);
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

