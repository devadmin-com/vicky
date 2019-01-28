package com.devadmin.vicky.controller.jira.model;

import com.devadmin.jira.JiraClient;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskPriority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the object which contains the information about jira issue
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueModel implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueModel.class);

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

    @Autowired
    private JiraClient jiraClient;
    private CommentModel comment;

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
    public TaskPriority getPriority() {
        return TaskPriority.valueOf(fields.getPriority().getName());
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
    public String getStatus() {
        return this.fields.getStatus().getName();
    }

    @Override
    public String getAssignee() {
        if (this.fields.getAssignee() != null) {
            return this.fields.getAssignee().getName();
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
