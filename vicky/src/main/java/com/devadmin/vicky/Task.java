/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import com.devadmin.vicky.controller.jira.model.FieldModel;

import java.util.List;

/**
 * A Generic task in some task tracking system...
 * <p>
 * <p>Abstracts away the task tracking system so we can view jira, asana, etc. the same way in our
 * code
 */
public interface Task {

    /**
     * Status
     * @return status
     */
    String getStatus();

    /**
     * Unique identifier of task
     *
     * @return task ID
     */
    String getId();

    /**
     * @return this task's description
     */
    String getDescription();

    /**
     * @return the priority of this task
     */
    TaskPriority getPriority();

    /**
     * @return a string identifier of the project this task belongs to.
     */
    String getProject();

    /**
     * @return the URL of this task if they have appropriate permissions a user can view the task at
     * this URL in it's system
     */
    String getUrl();

    /**
     * @return the list of labels
     */
    List<String> getLabels();

    /**
     * @return true if task is resolved
     */
    Boolean isResolved();

    /**
     * @return status of task
     */
    TaskType getType();

    /**
     * @return the username who assignee this task
     */
    String getAssignee();

    /**
     * @return IssueKey which is consist of project key and issue number
     */
    String getKey();

    /**
     * @return Issue summary (one-line description)
     */
    String getSummary();

    /**
     * @return The last comment written on the task
     */
    Comment getLastComment();

    /**
     * @return fields of task
     */
    FieldModel getFields();
}
