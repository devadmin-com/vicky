package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.listener.LabeledTaskListener;
import org.junit.Test;

public class LabeledTaskListenerTest extends TaskListenerTest {

  /**
   * tests that the event was sent
   * @throws Exception
   */
  @Test
  public void basicTest() throws Exception {
    String id = "bob";

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.LABELED_TASK);
    publish(testEventModel);

    assertTrue("message was not sent to the channel", messageService.channelMessaged);
    assertFalse("message was sent privately", messageService.privateMessaged);
  }

  // private methods
  private void createContext() {
    LabeledTaskListener listener = new LabeledTaskListener(messageService);
    context.addApplicationListener(listener);
    context.refresh();
  }

}
