package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.TaskType;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.CreatedTaskListener;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link CreatedTaskListener}
 */
public class CreatedTaskListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * tests that the event was sent
     */
    @Test
    public void basicTest() {
        createContext();

        JiraEventModel jiraEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(jiraEventModel);

        assertEquals(1, testMessageService.getChannelMessageCount()); // should call the method just 1 time
        assertTrue(testMessageService.wasChannelMsged()); // check that a message was sent to the channel
        assertFalse(testMessageService.wasPMed()); // check that a message was NOT sent privately
    }

    /**
     * Tests that handler gets the event and send the right message
     */
    @Test
    public void eventShouldBeHandledByThisHandlerTest() {
        createContext();

        JiraEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(testEventModel);

        assertTrue(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * Tests that handler will not get the event with wrong type
     */
    @Test
    public void eventShouldNotBeHandledWithWrongTypeTest() {

        createContext();

        JiraEventModel testEventModel = getTestTaskEventModel(TaskEventType.COMMENT);
        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * Tests that handler will handle the event with correct type
     */
    @Test
    public void eventShouldBeHandledWithCorrectTypeTest() {

        createContext();

        JiraEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(testEventModel);

        assertTrue(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    private JiraEventModel getTestTaskEventModel(TaskEventType type) {
        JiraEventModel jiraEventModel = new JiraEventModel();
        jiraEventModel.setType(type);

        CommentModel commentModel = new CommentModel();
        commentModel.setBody("Some Test Comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        commentModel.setAuthor(authorModel);

        IssueModel issueModel = mock(IssueModel.class, RETURNS_DEEP_STUBS);
        when(issueModel.getFields().getIssueType().getId()).thenReturn("13");
        when(issueModel.getStatus()).thenReturn("Test status");
        when(issueModel.getPriority()).thenReturn(TaskPriority.OTHER);
        when(issueModel.getType()).thenReturn(TaskType.OTHER);
        when(issueModel.getLastComment()).thenReturn(commentModel);
        jiraEventModel.setIssue(issueModel);
        return jiraEventModel;
    }

    private void createContext() {
        CreatedTaskListener listener = new CreatedTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(listener);
        context.refresh();
    }
}
