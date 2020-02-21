package com.devadmin.vicky.test;

import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.TaskPriority;
import com.devadmin.vicky.model.jira.task.TaskType;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.AtReferenceListener;
import com.devadmin.vicky.model.jira.*;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link com.devadmin.vicky.listener.AtReferenceListener}
 */
public class AtReferenceListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * tests that the event was sent
     */
    @Test
    public void itShouldPassWhenHasReferenceTest() {
        createContext();

        CommentModel comment = new CommentModel();
        comment.setBody("What is this [~serpento] ?");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        authorModel.setName("testUser");
        comment.setAuthor(authorModel);

        TestTask testTask = new TestTask();
        testTask.setFieldModel(new FieldModel());
        testTask.setStatus("Test status");
        testTask.setType(TaskType.OTHER);
        testTask.setPriority(TaskPriority.OTHER);

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setComment(comment);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
    }

    /**
     * tests that the event was sent
     */
    @Test
    public void testMultiplyAtReferencesInComment() {
        //Arrange
        createContext();

        CommentModel comment = new CommentModel();
        comment.setBody("What is this [~serpento] and [~vvorski]?");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("testUser");
        comment.setAuthor(authorModel);

        IssueModel issueModel = mock(IssueModel.class);
        when(issueModel.getFields()).thenReturn(new FieldModel());
        when(issueModel.getStatus()).thenReturn("Test status");
        when(issueModel.getType()).thenReturn(TaskType.OTHER);

        JiraEventModel jiraEventModel = new JiraEventModel();
        jiraEventModel.setComment(comment);
        jiraEventModel.setIssue(issueModel);

        //Act
        publish(jiraEventModel);

        //Assert
        assertFalse(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
        assertEquals(2, testMessageService.getPrivateMessageCount());
    }

    /**
     * Tests that handler will not get the event with wrong type
     */
    @Test
    public void eventShouldNotBeHandledWithNoReferenceTest() {

        createContext();

        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("serpento");

        CommentModel comment = new CommentModel();
        comment.setBody("Hello world!");

        TestTask testTask = new TestTask();
        testTask.setType(TaskType.OTHER);
        testTask.setPriority(TaskPriority.OTHER);

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setComment(comment);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    private void createContext() {
        AtReferenceListener atReferenceListener = new AtReferenceListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(atReferenceListener);
        context.refresh();
    }
}
