package com.devadmin.vicky.test;

import com.devadmin.vicky.format.AssignTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.PMOnAssignListener;
import com.devadmin.vicky.model.jira.*;
import com.devadmin.vicky.model.jira.changelog.ChangeLogItem;
import com.devadmin.vicky.model.jira.changelog.ChangeLogModel;
import com.devadmin.vicky.model.jira.changelog.JiraChangeLogItemModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.devadmin.vicky.model.jira.task.TaskPriority;
import com.devadmin.vicky.model.jira.task.TaskType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link com.devadmin.vicky.listener.PMOnAssignListener}
 */
public class PMOnAssignListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new AssignTaskEventFormatter();
    }

    /**
     * Tests that handler gets the event and send the right message
     */
    @Test
    public void eventShouldBeHandledByPMOnAssignListenerTest() {

        createContext();

        JiraEventModel jiraEventModel = new JiraEventModel();
        UserModel userModel = new UserModel();
        userModel.setName("some user");
        jiraEventModel.setUser(userModel);

        JiraChangeLogItemModel item = new JiraChangeLogItemModel();
        item.setField("assignee");
        item.setTo("testUser");

        List<JiraChangeLogItemModel> itemList = new ArrayList<>();
        itemList.add(item);

        ChangeLogModel changeLogModel = new ChangeLogModel();
        changeLogModel.setItems(itemList);

        CommentModel commentModel = new CommentModel();
        commentModel.setBody("Some Test Comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        commentModel.setAuthor(authorModel);

        UserModel assignee = new UserModel();
        assignee.setEmailAddress("devadmin@devadmin.com");
        IssueModel issueModel = mock(IssueModel.class, RETURNS_DEEP_STUBS);
        when(issueModel.getFields().getIssueType().getId()).thenReturn("13");
        when(issueModel.getFields().getAssignee()).thenReturn(assignee);
        when(issueModel.getPriority()).thenReturn(TaskPriority.OTHER);
        when(issueModel.getType()).thenReturn(TaskType.OTHER);
        when(issueModel.getLastComment()).thenReturn(commentModel);

        jiraEventModel.setIssue(issueModel);
        jiraEventModel.setChangeLog(changeLogModel);
        jiraEventModel.setType(TaskEventType.UPDATED);
        publish(jiraEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
        assertNotNull(testMessageService.getPrivateMsg());
        assertTrue(testMessageService.getPrivateMsg().size() > 0);
        assertTrue(testMessageService.wasPMed(assignee.getEmailAddress()));
    }

    /**
     * tests that the event was not handled if task is unsigned
     */
    @Test
    public void listenerShouldNotHandleWrongTypeEventTest() {

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();

        TestTask testTask = new TestTask();
        testTask.setPriority(TaskPriority.OTHER);
        testTask.setType(TaskType.OTHER);
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
        PMOnAssignListener listener = new PMOnAssignListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(listener);
        context.refresh();
    }
}
