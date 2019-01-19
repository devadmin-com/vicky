package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.AtReferenceListener;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Test class for {@link com.devadmin.vicky.listener.AtReferenceListener}
 *
 */
public class AtReferenceListenerTest extends TaskListenerTest {

  @Override
  TaskEventFormatter getTaskEventFormatter() {
    return new SimpleTaskEventFormatter();
  }

  /**
   * tests that the event was sent
   */
  @Test
  public void itShouldPassWhenHasReferenceTest() {
    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("What is this [~serpento] ?");

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setPriority(TaskPriority.Major);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
  }


  /**
   * Tests that handler will not get the event with wrong type
   */
  @Test
  public void eventShouldNotBeHandledWithNoReferenceTest(){

    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("Hello world!");

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setPriority(TaskPriority.Major);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    AtReferenceListener listener = new AtReferenceListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(listener);
    context.refresh();
  }
}