package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link com.devadmin.vicky.listener.ProjectTaskListener}
 */
public class ProjectTaskListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * tests that the event was sent
     */
    @Test
    public void basicTest() {
        // check that we get the right MessageService
        String id = "bob";

        createContext();

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(testEventModel);

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

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
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

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.COMMENT);
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

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(testEventModel);

        assertTrue(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    // private methods

    private TestTaskEventModel getTestTaskEventModel(TaskEventType type) {
        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setType(type);

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
        return testEventModel;
    }

    private void createContext() {
        ProjectTaskListener listener = new ProjectTaskListener(testMessageService, taskEventFormatter);
        context.addApplicationListener(listener);
        context.refresh();
    }

}

