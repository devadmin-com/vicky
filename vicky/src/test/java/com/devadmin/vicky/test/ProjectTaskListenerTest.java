package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link com.devadmin.vicky.listener.ProjectTaskListener}
 */
public class ProjectTaskListenerTest extends TaskListenerTest {

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
        String expectedMessage = "This message was sent by supercool Vicky 2.0 from ProjectTaskListener";

        createContext();

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventType.CREATED);
        publish(testEventModel);

        assertEquals(expectedMessage, testMessageService.getLastMessage());
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
        testEventModel.setTask(new TestTask());
        return testEventModel;
    }

    private void createContext() {
        ProjectTaskListener listener = new ProjectTaskListener(testMessageService, taskEventFormatter);
        context.addApplicationListener(listener);
        context.refresh();
    }

}

