package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.listener.LabeledTaskListener;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class LabeledTaskListenerTest extends TaskListenerTest {

  /**
   * tests that the event was sent
   */
  @Test
  public void basicTest() {
    createContext();

    List<String> labels = Arrays.asList("label1", "label2");
    TestTask task = new TestTask();
    task.setLabels(labels);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.LABELED_TASK);
    testEventModel.setTask(task);

    publish(testEventModel);

    assertTrue("message was not sent to the channel", messageService.channelMessaged);
    assertFalse("message was sent privately", messageService.privateMessaged);
  }

  /**
   * Tests that handler will not get the event with wrong type
   */
  @Test
  public void eventShouldNotBeHandledWithWrongTypeTest(){

    createContext();

    List<String> labels = Arrays.asList("label1", "label2");
    TestTask task = new TestTask();
    task.setLabels(labels);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.PROJECT_TASK);
    testEventModel.setTask(task);

    publish(testEventModel);

    assertFalse(messageService.channelMessaged);
    assertFalse(messageService.privateMessaged);
  }

  /**
   * Tests that the event was not handled if task isn't contain label
   */
  @Test
  public void eventShouldNotBeHandledWithoutLabelsTest(){

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.PROJECT_TASK);

    publish(testEventModel);

    assertFalse(messageService.channelMessaged);
    assertFalse(messageService.privateMessaged);
  }

  /**
   * Tests that handler will hande the event with correct type
   */
  @Test
  public void eventShouldBeHandledWithCorrectTypeTest(){

    createContext();

    List<String> labels = Arrays.asList("label1", "label2");
    TestTask task = new TestTask();
    task.setLabels(labels);

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.LABELED_TASK);
    testEventModel.setTask(task);

    publish(testEventModel);

    assertTrue(messageService.channelMessaged);
    assertFalse(messageService.privateMessaged);
  }

  // private methods
  private void createContext() {
    LabeledTaskListener listener = new LabeledTaskListener(messageService);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
