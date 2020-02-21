package com.devadmin.vicky.test;

import com.devadmin.vicky.model.jira.changelog.ChangeLog;
import com.devadmin.vicky.model.jira.comment.Comment;
import com.devadmin.vicky.model.jira.task.Task;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.model.jira.task.TaskEventType;

import java.util.Date;

/**
 * An implementation of TaskEventModelWrapper for testing...
 */
public class TestTaskEventModel implements TaskEvent {

    private TaskEventType type;
    private TestChangeLog changelog;
    private Comment comment;
    private Task task;

    @Override
    public boolean hasComment() {
        return getComment() != null;
    }

    @Override
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Date getTimeStamp() {
        return new Date();
    }

    @Override
    public TaskEventType getType() {
        return type;
    }

    public void setType(TaskEventType type) {
        this.type = type;
    }

    @Override
    public ChangeLog getChangeLog() {
        return changelog;
    }

    public void setChangelog(TestChangeLog changelog) {
        this.changelog = changelog;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getActor() {
        return "serpento";
    }

    @Override
    public String getEmailAutor() {
        return "mail@mail.com";
    }

    @Override
    public String toString() {
        return "TestTaskEventModel{" + "type=" + type + '}';
    }
}
