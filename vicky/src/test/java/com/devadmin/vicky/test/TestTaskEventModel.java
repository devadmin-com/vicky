package com.devadmin.vicky.test;

import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.Comment;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventType;
import java.util.Date;

/**
 * An implementation of TaskEventModelWrapper for testing...
 */
public class TestTaskEventModel implements TaskEvent {

  private TaskEventType type;
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
  public TaskEventType getType() {
    return type;
  }

  @Override
  public Changelog getChangeLog() {
    return changelog;
  }

  public void setChangelog(TestChangelog changelog) {
    this.changelog = changelog;
  }

  public void setType(TaskEventType type) {
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
