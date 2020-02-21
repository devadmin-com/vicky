package com.devadmin.vicky.test;

import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.devadmin.vicky.model.jira.task.TaskPriority;
import com.devadmin.vicky.model.jira.task.TaskType;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.LabeledTaskListener;
import com.devadmin.vicky.model.jira.AuthorModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.JiraEventModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link com.devadmin.vicky.listener.LabeledTaskListener}
 */
public class LabeledTaskListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * Tests that the event was not handled if task isn't contain label
     */
    @Test
    public void eventShouldNotBeHandledWithoutLabelsTest() {

        createContext();

        TestTask testTask = new TestTask();
        testTask.setLabels(new ArrayList<>());
        testTask.setPriority(TaskPriority.OTHER);
        testTask.setType(TaskType.OTHER);
        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setType(TaskEventType.CREATED);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * Tests that the message was sent to channel which has the same name as Label
     */
    @Test
    public void shouldSendChannelMessageWithTheNameOfLabelTest() {

        createContext();

        List<String> labels = Arrays.asList("label1", "label2");

        CommentModel commentModel = new CommentModel();
        commentModel.setBody("Some Test Comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        commentModel.setAuthor(authorModel);

        IssueModel issueModel = mock(IssueModel.class, RETURNS_DEEP_STUBS);
        when(issueModel.getFields().getIssueType().getId()).thenReturn("13");
        when(issueModel.getLabels()).thenReturn(labels);
        when(issueModel.getPriority()).thenReturn(TaskPriority.OTHER);
        when(issueModel.getType()).thenReturn(TaskType.OTHER);
        when(issueModel.getLastComment()).thenReturn(commentModel);
        when(issueModel.getStatus()).thenReturn("test status");

        JiraEventModel jiraEventModel = new JiraEventModel();

        jiraEventModel.setType(TaskEventType.CREATED);
        jiraEventModel.setIssue(issueModel);

        publish(jiraEventModel);

        assertTrue(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasChannelMsged("label1"));
        assertTrue(testMessageService.wasChannelMsged("label2"));
        assertEquals(2, testMessageService.getChannelMessageCount()); // should send two messages
        assertFalse(testMessageService.wasPMed());
    }

    // private methods
    private void createContext() {
        LabeledTaskListener listener = new LabeledTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(listener);
        context.refresh();
    }
}
