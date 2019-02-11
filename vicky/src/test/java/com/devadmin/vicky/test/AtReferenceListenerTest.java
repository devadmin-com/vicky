package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.AtReferenceListener;
import org.junit.Test;

import static org.junit.Assert.*;


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
    AuthorModel authorModel = new AuthorModel();
    authorModel.setDisplayName("serpento");
    authorModel.setName("testUser");
    comment.setAuthor(authorModel);

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setPriority(TaskPriority.MAJOR);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
  }

  /**
   * tests that the event was sent
   */
  @Test
  public void testMultiplyAtReferencesInComment() {
    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("What is this [~serpento] and [~vvorski]?");
    AuthorModel authorModel = new AuthorModel();
    authorModel.setName("testUser");
    comment.setAuthor(authorModel);

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
    assertEquals(2, testMessageService.getPrivateMessageCount());
  }


  /**
   * Tests that handler will not get the event with wrong type
   */
  @Test
  public void eventShouldNotBeHandledWithNoReferenceTest(){

    createContext();

    AuthorModel authorModel = new AuthorModel();
    authorModel.setName("serpento");

    CommentModel comment = new CommentModel();
    comment.setBody("Hello world!");

    TestTask testTask = new TestTask();
    testTask.setStatus("Backlog");
    testTask.setPriority(TaskPriority.MAJOR);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    AtReferenceListener atReferenceListener = new AtReferenceListener(testMessageService, taskEventFormatter);
    //CommentedTaskListener commentedTaskListener = new CommentedTaskListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(atReferenceListener);
    //context.addApplicationListener(commentedTaskListener);
    context.refresh();
  }
}