package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.listener.AtReferenceListener;
import com.devadmin.vicky.listener.LabeledTaskListener;
import com.devadmin.vicky.listener.PMOnAssignListener;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** Test class for {@link ProjectTaskListener} */
public class MultipleTaskListenerTest extends TaskListenerTest {

  /** tests that the event was sent */
  @Test
  public void basicTest() {

    createContext();

    TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
    publish(testEventModel);

    assertTrue(testMessageService.wasChannelMsged());
    assertTrue(testMessageService.wasPMed());
    assertNotNull(testMessageService.getChannelMsg());
    assertNotNull(testMessageService.getPrivateMsg());
    assertTrue(testMessageService.getChannelMsg().size() > 0);
    assertTrue(testMessageService.getPrivateMsg().size() > 0);
    assertTrue(testMessageService.wasChannelMsged("proj"));
    assertTrue(testMessageService.wasChannelMsged("label1"));
    assertTrue(testMessageService.wasChannelMsged("label2"));
  }

  // private methods

  private TestTaskEventModel getTestTaskEventModel(TaskEventType type) {

    TestTaskEventModel testEventModel = new TestTaskEventModel();
    testEventModel.setType(type);

    //  from PMOnAssign test
    TestJiraChangeLogItem item = new TestJiraChangeLogItem();
    item.setField("assignee");
    item.setTo("testUser");

    List<ChangeLogItem> itemList = new ArrayList<>();
    itemList.add(item);

    TestChangeLog testChangelog = new TestChangeLog();
    testChangelog.setItems(itemList);
    testEventModel.setChangelog(testChangelog);

    CommentModel commentModel = new CommentModel();
    commentModel.setBody("Some Test Comment");
    AuthorModel authorModel = new AuthorModel();
    authorModel.setDisplayName("serpento");
    commentModel.setAuthor(authorModel);

    // from labeled
    List<String> labels = Arrays.asList("label1", "label2");
    TestTask testTask = new TestTask();
    testTask.setLabels(labels);
    testTask.setPriority(TaskPriority.MINOR);
    testTask.setStatus("Backlog");
    testTask.setLastComment(commentModel);
    testEventModel.setTask(testTask);

    return testEventModel;
  }

  private void createContext() {
    ProjectTaskListener projectTaskListener =
        new ProjectTaskListener(testMessageService, taskEventFormatter);
    PMOnAssignListener pmOnAssignListener =
        new PMOnAssignListener(testMessageService, taskEventFormatter);
    AtReferenceListener atReferenceListener =
        new AtReferenceListener(testMessageService, taskEventFormatter);
    LabeledTaskListener labeledTaskListener =
        new LabeledTaskListener(testMessageService, taskEventFormatter);
    context.addApplicationListener(projectTaskListener);
    context.addApplicationListener(pmOnAssignListener);
    context.addApplicationListener(atReferenceListener);
    context.addApplicationListener(labeledTaskListener);
    context.refresh();
  }
}
