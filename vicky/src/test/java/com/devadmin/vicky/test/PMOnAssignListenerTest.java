package com.devadmin.vicky.test;

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
   * @throws Exception
   */
  @Test
  public void basicTest() throws Exception {
    // check that we get the right MessageService
    String id = "bob";

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_created");
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
  public void eventShouldBeHandledByThisHandlerTest() throws Exception {

    createContext();

    String expectedMessage = "This message was sent by supercool Vicky 2.0 from ProjectTaskListener";

    TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_created");
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
   * tests that the event was sent
   * @throws Exception
   */
  @Test
  public void eventShouldNotBeHandledWithWrongTypeTest() throws Exception {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(TaskEventModelType.LABELED_TASK);
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