package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.listener.LabeledTaskListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for {@link com.devadmin.vicky.listener.LabeledTaskListener}
 *
 */
public class LabeledTaskListenerTest extends TaskListenerTest {

  /**
   * Tests that the event was not handled if task isn't contain label
   */
  @Test
  public void eventShouldNotBeHandledWithoutLabelsTest(){

    createContext();

    TestTask testTask = new TestTask();
    testTask.setLabels(new ArrayList<>());
    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventType.CREATED);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  /**
   * Tests that the message was sent to channel which has the same name as Label
   */
  @Test
  public void shouldSendChannelMessageWithTheNameOfLabelTest(){

    createContext();

    List<String> labels = Arrays.asList("label1", "label2");
    TestTask task = new TestTask();
    task.setLabels(labels);


    TestTaskEventModel testEventModel = new TestTaskEventModel();

    testEventModel.setType(TaskEventType.CREATED);
    testEventModel.setTask(task);

    publish(testEventModel);

    assertTrue(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasChannelMsged("label1"));
    assertTrue(testMessageService.wasChannelMsged("label2"));
    assertEquals(2, testMessageService.getChannelMessageCount()); // should send two messages
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    LabeledTaskListener listener = new LabeledTaskListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
