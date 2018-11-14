package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import com.devadmin.jira.agile.Issue;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * A base class for GreenHopper issues.
 */
public abstract class GreenHopperIssue extends GreenHopperResource {

    private String key = null;
    private boolean hidden = false;
    private String summary = null;
    private String typeName = null;
    private String typeId = null;
    private String typeUrl = null;
    private String priorityUrl = null;
    private String priorityName = null;
    private boolean done = false;
    private String assignee = null;
    private String assigneeName = null;
    private String avatarUrl = null;
    private String colour = null;
    private String statusId = null;
    private String statusName = null;
    private String statusUrl = null;
    private List<Integer> fixVersions = null;
    private int projectId = 0;

    /**
     * Creates an issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected GreenHopperIssue(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        key = Field.getString(map.get("key"));
        hidden = Field.getBoolean(map.get("hidden"));
        summary = Field.getString(map.get("summary"));
        typeName = Field.getString(map.get("key"));
        typeId = Field.getString(map.get("typeId"));
        typeUrl = Field.getString(map.get("typeUrl"));
        priorityUrl = Field.getString(map.get("priorityUrl"));
        priorityName = Field.getString(map.get("priorityName"));
        done = Field.getBoolean(map.get("done"));
        assignee = Field.getString(map.get("assignee"));
        assigneeName = Field.getString(map.get("assigneeName"));
        avatarUrl = Field.getString(map.get("avatarUrl"));
        colour = Field.getString(map.get("color"));
        statusId = Field.getString(map.get("statusId"));
        statusName = Field.getString(map.get("statusName"));
        statusUrl = Field.getString(map.get("statusUrl"));
        fixVersions = GreenHopperField.getIntegerArray(map.get("fixVersions"));
        projectId = Field.getInteger(map.get("projectId"));
    }

    /**
     * Retrieves the full JIRA issue.
     *
     * @return an Issue
     *
     * @throws JiraException when the retrieval fails
     */
    public Issue getJiraIssue() throws JiraException {
        return Issue.get(restclient, key);
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public String getSummary() {
        return summary;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public String getPriorityUrl() {
        return priorityUrl;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public Boolean isDone() {
        return done;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getColour() {
        return colour;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public List<Integer> getFixVersions() {
        return fixVersions;
    }

    public int getProjectId() {
        return projectId;
    }
}

