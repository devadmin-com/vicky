package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Represents an Agile Epic.
 *
 * @author pldupont
 */
public class Epic extends AgileResource {

    private Issue issue;
    private String key;
    private String summary;
    private boolean done;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Epic(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Retrieves the epic matching the ID.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the epic
     * @return an epic instance
     * @throws JiraException when the retrieval fails
     */
    public static Epic get(RestClient restclient, long id) throws JiraException {
        return AgileResource.get(restclient, Epic.class, RESOURCE_URI + "epic/" + id);
    }

    /**
     * @param refresh If true, will fetch the information from JIRA, otherwise use the cached info.
     * @return The Issue representation of this Epic.
     * @throws JiraException when the retrieval fails
     */
    public Issue asIssue(boolean refresh) throws JiraException {
        if (this.issue == null || refresh) {
            this.issue = Issue.get(getRestclient(), getId());
        }
        return this.issue;
    }

    /**
     * @return All issues in the Epic.
     * @throws JiraException when the retrieval fails
     */
    public List<Issue> getIssues() throws JiraException {
        return AgileResource.list(getRestclient(), Issue.class, RESOURCE_URI + "epic/" + getId() + "/issue", "issues");
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

        this.key = Field.getString(json.get("key"));
        this.summary = Field.getString(json.get("summary"));
        this.done = Field.getBoolean(json.get("done"));
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isDone() {
        return done;
    }
}
