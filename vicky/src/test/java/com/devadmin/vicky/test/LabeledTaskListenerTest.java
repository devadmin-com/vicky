package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.listener.LabeledTaskListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

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
    testEventModel.setType(TaskEventModelType.CREATED);
    testEventModel.setTask(testTask);

    publish(testEventModel);

    assertFalse(messageService.channelMessaged);
    assertFalse(messageService.privateMessaged);
  }

  // private methods
  private void createContext() {
    LabeledTaskListener listener = new LabeledTaskListener(messageService);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
