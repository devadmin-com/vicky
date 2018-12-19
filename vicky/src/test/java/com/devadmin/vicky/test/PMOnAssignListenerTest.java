package com.devadmin.vicky.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.controller.jira.model.JiraChangeLogItemModel;
import com.devadmin.vicky.listener.PMOnAssignListener;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Test class for {@link com.devadmin.vicky.listener.PMOnAssignListener}
 *
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
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventModelType.UPDATED);
    publish(testEventModel);

    assertFalse(messageService.channelMessaged);
    assertTrue(messageService.privateMessaged);
    assertEquals("this message was not sent to assigned user", item.getTo(), messageService.personId);
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
    testEventModel.setType(TaskEventModelType.UPDATED);
    publish(testEventModel);

    assertFalse(messageService.channelMessaged);
    assertFalse(messageService.privateMessaged);
  }

  // private methods
  private void createContext() {
    PMOnAssignListener listener = new PMOnAssignListener(messageService);
    context.addApplicationListener(listener);
    context.refresh();
  }
}