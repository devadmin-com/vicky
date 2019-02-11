package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.format.AssignTaskEventFormatter;
import com.devadmin.vicky.listener.PMOnAssignListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Test class for {@link com.devadmin.vicky.listener.PMOnAssignListener} */
public class PMOnAssignListenerTest extends TaskListenerTest {

  @Override
  TaskEventFormatter getTaskEventFormatter() {
    return new AssignTaskEventFormatter();
  }

  /** Tests that handler gets the event and send the right message */
  @Test
  public void eventShouldBeHandledByPMOnAssignListenerTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    TestJiraChangeLogItem item = new TestJiraChangeLogItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<ChangeLogItem> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangeLog testChangelog = new TestChangeLog();
    testChangelog.setItems(itemList);

    CommentModel commentModel = new CommentModel();
    commentModel.setBody("Some Test Comment");
    AuthorModel authorModel = new AuthorModel();
    authorModel.setDisplayName("serpento");
    commentModel.setAuthor(authorModel);

    TestTask testTask = new TestTask();
    testTask.setPriority(TaskPriority.MINOR);
    testTask.setStatus("Backlog");
    testTask.setLastComment(commentModel);

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

  /** tests that the event was not handled if task is unsigned */
  @Test
  public void listenerShouldNotHandleWrongTypeEventTest() {

    createContext();

    TestTaskEventModel testEventModel = new TestTaskEventModel();

    TestTask testTask = new TestTask();
    testTask.setPriority(TaskPriority.MINOR);
    testTask.setStatus("Backlog");
    testEventModel.setTask(testTask);

    TestJiraChangeLogItem item = new TestJiraChangeLogItem();

    List<ChangeLogItem> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangeLog testChangelog = new TestChangeLog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);
    testEventModel.setType(TaskEventType.UPDATED);
    publish(testEventModel);

    assertFalse(testMessageService.wasChannelMsged());
    assertFalse(testMessageService.wasPMed());
  }

  // private methods
  private void createContext() {
    PMOnAssignListener listener = new PMOnAssignListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(listener);
    context.refresh();
  }
}
