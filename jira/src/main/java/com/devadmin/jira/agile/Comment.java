package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Represents an Agile Comment.
 *
 * @author pldupont
 */
public class Comment extends AgileResource {

    private User author;
    private String body;
    private User updateAuthor;
    private Date created;
    private Date updated;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Comment(RestClient restclient, JSONObject json) throws JiraException {
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
        this.body = Field.getString(json.get("body"));
        this.updateAuthor = getSubResource(User.class, json, "updateAuthor");
        this.created = Field.getDateTime(json.get("created"));
        this.updated = Field.getDateTime(json.get("updated"));
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, body='%s'}", getClass().getSimpleName(), getId(), getBody());
    }

    public User getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
}
