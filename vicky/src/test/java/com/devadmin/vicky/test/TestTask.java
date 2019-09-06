package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.TaskType;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.controller.jira.model.StatusModel;

import java.util.List;

class TestTask implements Task {

    private final String TEST_PROJECT_NAME = "proj";
    private List<String> labels;

    private TaskType type = TaskType.OTHER;
    private TaskPriority priority;
    private CommentModel lastComment;
    private String assignee;
    private FieldModel fieldModel;

    private boolean isResolved = false;

    @Override
    public String getStatus() {
        return this.fieldModel.getStatus().getName();
    }

    public void setStatus(final String status) {
        final StatusModel statusModel = new StatusModel();
        statusModel.setName(status);
        this.fieldModel.setStatus(statusModel);
    }

    @Override
    public String getId() {
        return "13";
    }

    @Override
    public String getDescription() {
        return "description";
    }

    @Override
    public TaskPriority getPriority() {
        return this.priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public String getProject() {
        return TEST_PROJECT_NAME;
    }

    @Override
    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    /**
     * @return true if task is resolved
     */
    @Override
    public Boolean isResolved() {
        return isResolved;
    }

    public void setIsResolved() {
        isResolved = true;
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String getUrl() {
        return "https://devadmin.atlassian.net/browse/TL-000";
    }

    @Override
    public String getSummary() {
        return "Test Task";
    }

    @Override
    public String getKey() {
        return "TL-000";
    }

    @Override
    public CommentModel getLastComment() {
        return lastComment;
    }

    public void setLastComment(CommentModel lastComment) {
        this.lastComment = lastComment;
    }

    @Override
    public FieldModel getFields() {
        return fieldModel;
    }

    public void setFieldModel(FieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }
}