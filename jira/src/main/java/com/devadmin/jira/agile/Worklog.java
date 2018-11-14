package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Represents an Agile Worklog.
 *
 * @author pldupont
 */
public class Worklog extends AgileResource {

    private User author;
    private String comment;
    private Date created;
    private Date updated;
    private User updateAuthor;
    private Date started;
    private String timeSpent;
    private long timeSpentSeconds;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Worklog(RestClient restclient, JSONObject json) throws JiraException {
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
        this.author = getSubResource(User.class, json, "author");
        this.comment = Field.getString(json.get("comment"));
        this.created = Field.getDateTime(json.get("created"));
        this.updated = Field.getDateTime(json.get("updated"));
        this.updateAuthor = getSubResource(User.class, json, "updateAuthor");
        this.started = Field.getDateTime(json.get("started"));
        this.timeSpent = Field.getString(json.get("timeSpent"));
        this.timeSpentSeconds = Field.getLong(json.get("timeSpentSeconds"));
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, comment='%s'}", getClass().getSimpleName(), getId(), getComment());
    }

    public User getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getStarted() {
        return started;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }
}
