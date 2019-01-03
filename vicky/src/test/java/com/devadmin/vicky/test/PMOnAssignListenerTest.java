package com.devadmin.vicky.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.listener.PMOnAssignListener;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Test class for {@link com.devadmin.vicky.listener.PMOnAssignListener}
 */
public class PMOnAssignListenerTest extends TaskListenerTest {

  /**
   * Tests that handler gets the event and send the right message
   */
  @Test
  public void eventShouldBeHandledByThisHandlerTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestJiraChangeLogItem item = new TestJiraChangeLogItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<ChangeLogItem> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    TestTask testTask = new TestTask();
    testEventModel.setTask(testTask);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventType.UPDATED);
    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
    assertNotNull(testMessageService.getPrivateMsg());
    assertTrue(testMessageService.getPrivateMsg().size() > 0);
    assertTrue(testMessageService.wasPMed(item.getTo()));
  }

  /**
   * tests that the event was not handled if task is unsigned
   */
  @Test
  public void eventShouldNotBeHandledIfTaskIsUnassignedTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestJiraChangeLogItem item = new TestJiraChangeLogItem();

    List<ChangeLogItem> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventType.UPDATED);
    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    PMOnAssignListener listener = new PMOnAssignListener(testMessageService);
    context.addApplicationListener(listener);
    context.refresh();
  }
}