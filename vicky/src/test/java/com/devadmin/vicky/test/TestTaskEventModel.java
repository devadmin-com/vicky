package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEventModel;

import java.util.Date;

/**
 * An implementation of TaskEventModel for testing...
 *
 */
public class TestTaskEventModel implements TaskEventModel {
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
    public String getType() {
        return null;
    }
}
