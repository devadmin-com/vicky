package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.CommentedTaskListener;
import org.junit.Test;

public class CommentedTaskListenerTest extends TaskListenerTest {

  @Override
  TaskEventFormatter getTaskEventFormatter() {
    return new SimpleTaskEventFormatter();
  }

  /**
   * test CommentedTaskListener if commenter and assignee is different users
   */
  @Test
  public void testWhenCommenterAndAssigneeIsDifferentUsers() {
    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("This is a simple comment");
    AuthorModel authorModel = new AuthorModel();
    authorModel.setName("serpento");
    comment.setAuthor(authorModel);

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setAssignee("testUser");

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
  }

  /**
   * test CommentedTaskListener if commenter and assignee is same users
   */
  @Test
  public void testWhenCommenterAndAssigneeIsSameUsers() {
    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("This is a simple comment");
    AuthorModel authorModel = new AuthorModel();
    authorModel.setName("testUser");
    comment.setAuthor(authorModel);

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setAssignee("testUser");

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  /**
   * test CommentedTaskListener if event does not contain comment
   */
  @Test
  public void testWhenCommentModelInEventIsNull() {
    createContext();

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setAssignee("testUser");

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }


  // private methods
  private void createContext() {
    CommentedTaskListener listener = new CommentedTaskListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
