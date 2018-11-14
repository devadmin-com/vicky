package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile TimeTracking.
 *
 * @author pldupont
 */
public class TimeTracking extends AgileResource {

    private String originalEstimate;
    private String remainingEstimate;
    private String timeSpent;
    private long originalEstimateSeconds;
    private long remainingEstimateSeconds;
    private long timeSpentSeconds;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public TimeTracking(RestClient restclient, JSONObject json) throws JiraException {
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
        this.originalEstimate = Field.getString(json.get("originalEstimate"));
        this.remainingEstimate = Field.getString(json.get("remainingEstimate"));
        this.timeSpent = Field.getString(json.get("timeSpent"));
        this.originalEstimateSeconds = Field.getLong(json.get("originalEstimateSeconds"));
        this.remainingEstimateSeconds = Field.getLong(json.get("remainingEstimateSeconds"));
        this.timeSpentSeconds = Field.getLong(json.get("timeSpentSeconds"));
    }

    @Override
    public String toString() {
        return String.format("%s{original='%s', remaining='%s', timeSpent='%s'}",
                getClass().getSimpleName(), getOriginalEstimate(), getRemainingEstimate(), getTimeSpent());
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public String getRemainingEstimate() {
        return remainingEstimate;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public long getOriginalEstimateSeconds() {
        return originalEstimateSeconds;
    }

    public long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }
}
