package com.devadmin.vicky.model.jira.task;

import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.FieldModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the object which contains the information about jira issue
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueModel implements Task {

    @JsonProperty("id")
    private String id;

    @JsonProperty("self")
    private String self;

    @JsonProperty("key")
    private String key;

    @JsonProperty("fields")
    private FieldModel fields;

    @Value("jira:cloud-url")
    private String cloudUrl;

    private CommentModel comment;

    @Override
    public String getStatus() {
        return this.fields.getStatus().getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FieldModel getFields() {
        return fields;
    }

    public void setFields(FieldModel fields) {
        this.fields = fields;
    }

    @Override
    public String getDescription() {
        return fields.getStatus().getDescription();
    }

    @Override
    public String getProject() {
        return getFields().getProject().getName();
    }

    @Override
    public List<String> getLabels() {
        ArrayList<String> labels = new ArrayList<>();
        if (getFields().getLabels() == null) {
            return labels;
        } else {
            return Arrays.asList(getFields().getLabels());
        }
    }

    /**
     * @return true if task is resolved
     */
    @Override
    public Boolean isResolved() {
        return "Resolved 解決済".equals(this.fields.getStatus().getName());
    }

    @Override
    public String getTypeId() {
        return fields.getIssueType().getId();
    }

    @Override
    public String getAssignee() {
        if (this.fields.getAssignee() != null) {
            return this.fields.getAssignee().getDisplayName();
        } else {
            return "Unassigned";
        }
    }

    @Override
    public String getUrl() {
        return String.format("https://devadmin.atlassian.net/browse/%s", this.key);
    }

    @Override
    public String getSummary() {
        return this.fields.getSummary();
    }

    @Override
    public CommentModel getLastComment() {
        return comment;
    }

    public void setLastComment(CommentModel comment) {
        this.comment = comment;
    }
}
