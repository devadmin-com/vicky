package com.devadmin.vicky.test;

import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEventModel;

import com.devadmin.vicky.TaskEventModelType;
import java.util.Date;

/**
 * An implementation of TaskEventModel for testing...
 */
public class TestTaskEventModel implements TaskEventModel {

    private TaskEventModelType type;
    private TestChangelog changelog;

    @Override
    public boolean hasComment() {
        return false;
    }

    @Override
    public Task getTask() {
        return new TestTask();
    }

    @Override
    public Date getTimeStamp() {
        return new Date();
    }

    @Override
    public TaskEventModelType getType() {
        return type;
    }

    @Override
    public Changelog getChangeLog() {
        return changelog;
    }

    public void setChangelog(TestChangelog changelog) {
        this.changelog = changelog;
    }

    public void setType(TaskEventModelType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TestTaskEventModel{" +
            "type=" + type +
            '}';
    }
}
