package com.devadmin.vicky.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.Item;
import com.devadmin.vicky.TaskEventModelType;
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
   * tests that the event was sent
   */
  @Test
  public void basicTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestItem item = new TestItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<Item> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventModelType.PM_ON_ASSIGN);
    publish(testEventModel);

    assertFalse("message was sent to the channel", messageService.channelMessaged);
    assertTrue("message was not sent privately", messageService.privateMessaged);
  }

  /**
   * Tests that handler gets the event and send the right message
   */
  @Test
  public void eventShouldBeHandledByThisHandlerTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestItem item = new TestItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<Item> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventModelType.PM_ON_ASSIGN);
    publish(testEventModel);

    assertFalse("message was sent to the channel", messageService.channelMessaged);
    assertTrue("message was not sent privately", messageService.privateMessaged);
    assertEquals("this message will not send to assigned user", item.getTo(), messageService.personId);
  }
  /**
   * tests that the event was not be handled with wrong type0
   */
  @Test
  public void eventShouldNotBeHandledWithWrongTypeTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestItem item = new TestItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<Item> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventModelType.LABELED_TASK);
    publish(testEventModel);

    assertFalse("message was sent to the channel", messageService.channelMessaged);
    assertFalse("message was sent privately", messageService.privateMessaged);
  }

  /**
   * tests that the event was not handled if task is unsigned
   */
  @Test
  public void eventShouldNotBeHandledIfTaskIsUnsignedTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestItem item = new TestItem();

    List<Item> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangelog testChangelog = new TestChangelog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventModelType.PM_ON_ASSIGN);
    publish(testEventModel);

    assertFalse("message was sent to the channel", messageService.channelMessaged);
    assertFalse("message was sent privately", messageService.privateMessaged);
  }

  // private methods
  private void createContext() {
    PMOnAssignListener listener = new PMOnAssignListener(messageService);
    context.addApplicationListener(listener);
    context.refresh();
  }
}