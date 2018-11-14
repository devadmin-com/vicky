package com.devadmin.jira.agile;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Issue extends AgileResource {

    private String key;
    private boolean flagged;
    private Sprint sprint;
    private List<Sprint> closedSprints;
    private String description;
    private Project project;
    private List<Comment> comments;
    private Epic epic;
    private List<Worklog> worklogs;
    private TimeTracking timeTracking;
    private IssueType issueType;
    private Status status;
    private Resolution resolution;
    private Date created;
    private Date updated;
    private Priority priority;
    private User assignee;
    private User creator;
    private User reporter;
    private String environment;


    /**
     * Creates a new Agile Issue resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Issue(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Retrieves the issue matching the ID.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the issue
     * @return an issue instance
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, long id) throws JiraException {
        return AgileResource.get(restclient, Issue.class, RESOURCE_URI + "issue/" + id);
    }

    /**
     * Retrieves the issue matching the ID.
     *
     * @param restclient REST client instance
     * @param key        JIRA key of the issue
     * @return an issue instance
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, String key) throws JiraException {
        return AgileResource.get(restclient, Issue.class, RESOURCE_URI + "issue/" + key);
    }

    @Override
    protected void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);
        this.key = Field.getString(json.get("key"));

        // Extract from Field sub JSONObject
        if (json.containsKey("fields")) {
            JSONObject fields = (JSONObject) json.get("fields");
            setName(Field.getString(fields.get("summary")));
            this.flagged = Field.getBoolean(fields.get("flagged"));
            this.sprint = getSubResource(Sprint.class, fields, "sprint");
            this.closedSprints = getSubResourceArray(Sprint.class, fields, "closedSprint");
            this.description = Field.getString(fields.get("description"));
            this.project = getSubResource(Project.class, fields, "project");
            this.comments = getSubResourceArray(Comment.class, fields, "comment");
            this.epic = getSubResource(Epic.class, fields, "epic");
            this.worklogs = getSubResourceArray(Worklog.class, fields, "worklog");
            this.timeTracking = getSubResource(TimeTracking.class, fields, "timetracking");
            this.environment = Field.getString(fields.get("environment"));
            this.issueType = getSubResource(IssueType.class, fields, "issuetype");
            this.status = getSubResource(Status.class, fields, "status");
            this.resolution = getSubResource(Resolution.class, fields, "resolution");
            this.created = Field.getDateTime(fields.get("created"));
            this.updated = Field.getDateTime(fields.get("updated"));
            this.priority = getSubResource(Priority.class, fields, "priority");
            this.assignee = getSubResource(User.class, fields, "assignee");
            this.creator = getSubResource(User.class, fields, "creator");
            this.reporter = getSubResource(User.class, fields, "reporter");

            addAttributes(fields);
        }
    }

    public String getKey() {
        return key;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public List<Sprint> getClosedSprints() {
        return closedSprints;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Epic getEpic() {
        return epic;
    }

    public List<Worklog> getWorklogs() {
        return worklogs;
    }

    public TimeTracking getTimeTracking() {
        return timeTracking;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public Status getStatus() {
        return status;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public Priority getPriority() {
        return priority;
    }

    public User getAssignee() {
        return assignee;
    }

    public User getCreator() {
        return creator;
    }

    public User getReporter() {
        return reporter;
    }

    public String getEnvironment() {
        return environment;
    }
}
