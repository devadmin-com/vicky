package com.devadmin.vicky.test;

import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.Comment;
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
  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "TestTaskEventModel{" +
        "type=" + type +
        '}';
  }
}
