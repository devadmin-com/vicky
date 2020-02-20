package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.TaskType;
import com.devadmin.vicky.controller.jira.model.*;
import com.devadmin.vicky.listener.AtReferenceListener;
import com.devadmin.vicky.listener.CreatedTaskListener;
import com.devadmin.vicky.listener.LabeledTaskListener;
import com.devadmin.vicky.listener.PMOnAssignListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link CreatedTaskListener}
 */
public class MultipleTaskListenerTest extends TaskListenerTest {

    /**
     * tests that the event was sent
     */
    @Test
    public void basicTest() {
        createContext();

        JiraEventModel jiraEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(jiraEventModel);

        assertTrue(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
        assertNotNull(testMessageService.getChannelMsg());
        assertNotNull(testMessageService.getPrivateMsg());
        assertTrue(testMessageService.getChannelMsg().size() > 0);
        assertTrue(testMessageService.getPrivateMsg().size() > 0);
        assertTrue(testMessageService.wasChannelMsged("label1"));
        assertTrue(testMessageService.wasChannelMsged("label2"));
    }

    private JiraEventModel getTestTaskEventModel(TaskEventType type) {

        JiraEventModel jiraEventModel = spy(JiraEventModel.class);
        UserModel userModel = new UserModel();
        userModel.setName("some user");
        jiraEventModel.setUser(userModel);
        jiraEventModel.setType(type);

        //  from PMOnAssign test
        JiraChangeLogItemModel item = new JiraChangeLogItemModel();
        item.setField("assignee");
        item.setTo("testUser");

        List<JiraChangeLogItemModel> itemList = new ArrayList<>();
        itemList.add(item);

        ChangeLogModel changeLogModel = new ChangeLogModel();
        changeLogModel.setItems(itemList);
        jiraEventModel.setChangeLog(changeLogModel);

        CommentModel commentModel = new CommentModel();
        commentModel.setBody("Some Test Comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        commentModel.setAuthor(authorModel);

        UserModel assignee = new UserModel();
        assignee.setEmailAddress("devadmin@devadmin.com");
        List<String> labels = Arrays.asList("label1", "label2");
        IssueModel issueModel = mock(IssueModel.class, RETURNS_DEEP_STUBS);
        when(issueModel.getFields().getIssueType().getId()).thenReturn("13");
        when(issueModel.getFields().getAssignee()).thenReturn(assignee);
        when(issueModel.getStatus()).thenReturn("Test test");
        when(issueModel.getLabels()).thenReturn(labels);
        when(issueModel.getPriority()).thenReturn(TaskPriority.OTHER);
        when(issueModel.getType()).thenReturn(TaskType.OTHER);
        when(issueModel.getLastComment()).thenReturn(commentModel);
        jiraEventModel.setIssue(issueModel);

        return jiraEventModel;
    }

    private void createContext() {
        CreatedTaskListener projectTaskListener = new CreatedTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        PMOnAssignListener pmOnAssignListener = new PMOnAssignListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        AtReferenceListener atReferenceListener = new AtReferenceListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        LabeledTaskListener labeledTaskListener = new LabeledTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(projectTaskListener);
        context.addApplicationListener(pmOnAssignListener);
        context.addApplicationListener(atReferenceListener);
        context.addApplicationListener(labeledTaskListener);
        context.refresh();
    }
}
