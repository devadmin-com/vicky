package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.listener.ResolvedTaskListener;
import org.junit.Test;

/**
 * Test class for {@link ResolvedTaskListenerTest}
 *
 */
public class ResolvedTaskListenerTest extends TaskListenerTest {

  /**
   * Tests that the event was not handled if task isn't resolved
   */
  @Test
  public void eventShouldNotBeHandledIfTaskIsNotResolvedTest(){

    createContext();

    TestTask testTask = new TestTask();
    testTask.setStatus("IN PROGRESS 進行中");
    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  /**
   * Tests that the event handled if task is resolved
   */
  @Test
  public void eventShouldBeHandledIfTaskIsResolvedTest(){

    createContext();

    TestTask testTask = new TestTask();
    testTask.setStatus("Resolved 解決済");
    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertTrue(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    ResolvedTaskListener listener = new ResolvedTaskListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
