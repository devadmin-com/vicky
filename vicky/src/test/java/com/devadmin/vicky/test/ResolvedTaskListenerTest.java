package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskType;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.ResolvedTaskListener;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link ResolvedTaskListenerTest}
 */
public class ResolvedTaskListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * Tests that the event was not handled if task isn't resolved
     */
    @Test
    public void eventShouldNotBeHandledIfTaskIsNotResolvedTest() {

        createContext();

        TestTask testTask = new TestTask();
        testTask.setType(TaskType.OTHER);
        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * Tests that the event handled if task is resolved
     */
    @Test
    public void eventShouldBeHandledIfTaskIsResolvedTest() {

        createContext();

        CommentModel commentModel = new CommentModel();
        commentModel.setBody("Some Test Comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        commentModel.setAuthor(authorModel);

        TestTask testTask = new TestTask();
        testTask.setFieldModel(new FieldModel());
        testTask.setStatus("Test status");
        testTask.setIsResolved();
        testTask.setLastComment(commentModel);

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertTrue(!testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    // private methods
    private void createContext() {
        ResolvedTaskListener listener = new ResolvedTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"));
        context.addApplicationListener(listener);
        context.refresh();
    }
}
