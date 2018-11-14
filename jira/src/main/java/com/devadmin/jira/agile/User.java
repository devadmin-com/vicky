package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile User.
 *
 * @author pldupont
 */
public class User extends AgileResource {

    private String emailAddress;
    private String displayName;
    private boolean active;
    private String timeZone;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public User(RestClient restclient, JSONObject json) throws JiraException {
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
        this.emailAddress = Field.getString(json.get("emailAddress"));
        this.displayName = Field.getString(json.get("displayName"));
        this.active = Field.getBoolean(json.get("active"));
        this.timeZone = Field.getString(json.get("timeZone"));
    }

    @Override
    public String toString() {
        return String.format("%s{name='%s', Display Name='%s'}", getClass().getSimpleName(), getName(), getDisplayName());
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isActive() {
        return active;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
