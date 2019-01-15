package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.listener.AtReferenceListener;
import org.junit.Test;


/**
 * Test class for {@link com.devadmin.vicky.listener.AtReferenceListener}
 *
 */
public class AtReferenceListenerTest extends TaskListenerTest {

  /**
   * tests that the event was sent
   */
  @Test
  public void itShouldPassWhenHasReferenceTest() {
    createContext();

    CommentModel comment = new CommentModel();
    comment.setBody("What is it [~serpento] ?");

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);

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

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setComment(comment);

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