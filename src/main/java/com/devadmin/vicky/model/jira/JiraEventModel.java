package com.devadmin.vicky.model.jira;

import com.devadmin.vicky.model.jira.changelog.ChangeLogModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Implements a JIRA Event based on JSON from JIRA webhook
 * <p>
 * <p>This is object contains the information received from jira
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JiraEventModel implements TaskEvent {

    @JsonProperty("timestamp")
    private Long timeStamp;

    @JsonProperty("webhookEvent")
    private String webhookEvent;

    @JsonProperty("user")
    private UserModel user;

    @JsonProperty("issue")
    private IssueModel issue;

    @JsonProperty("changelog")
    private ChangeLogModel changeLog;

    @JsonProperty("comment")
    private CommentModel comment;

    private TaskEventType type;

    @Override
    public String getActor() {
        return this.user.getName();
    }

    public void setComment(CommentModel comment) {
        this.comment = comment;
    }

    @Override
    public boolean hasComment() {
        return this.comment != null;
    }

    /**
     * @return the Task that this event was for
     */
    @Override
    public IssueModel getTask() {
        return issue;
    }

    @Override
    public Date getTimeStamp() {
        return new Date(timeStamp);
    }

    public String getEmailAuthor() {
        return this.comment.getAuthor().getEmailAddress();
    }

    /*
     * Will return readable user identifier
     */
    public String getEventAuthorName() {
        return this.comment.getAuthor().getDisplayName();
    }
}
