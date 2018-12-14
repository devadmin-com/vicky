package com.devadmin.vicky.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Test;

/**
 * Test class for {@link com.devadmin.vicky.listener.ProjectTaskListener}
 *
 */
public class ProjectTaskListenerTest extends TaskListenerTest {

    /**
     * tests that the event was sent
     * @throws Exception
     */
    @Test
    public void basicTest() throws Exception {
        // check that we get the right MessageService
        String id = "bob";

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_created");
        testEventModel.setType(TaskEventModelType.PROJECT_TASK);
        publish(testEventModel);

        assertTrue(messageService.channelMessaged); // check that a message was sent to the channel
        assertFalse(messageService.privateMessaged); // check that a message was NOT sent privately
    }


    /**
     * Tests that handler gets the event and send the right message
     */
    @Test
    public void eventShouldBeHandledByThisHandlerTest(){

        String expectedMessage = "This message was sent by supercool Vicky 2.0 from ProjectTaskListener";

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_created");
        testEventModel.setType(TaskEventModelType.PROJECT_TASK);
        publish(testEventModel);

        assertEquals(expectedMessage, messageService.lastMessage);
        assertTrue(messageService.channelMessaged);
        assertFalse(messageService.privateMessaged);

    }

    /**
     * Tests that handler will not get the event with wrong type
     */
    @Test
    public void eventShouldNotBeHandledWithWrongTypeTest(){

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_updated");
        testEventModel.setType(TaskEventModelType.LABELED_TASK);

        publish(testEventModel);

        assertFalse(messageService.channelMessaged);
        assertFalse(messageService.privateMessaged);
    }

    /**
     * Tests that handler will hande the event with correct type
     */
    @Test
    public void eventShouldBeHandledWithCorrectTypeTest(){

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
//        testEventModel.setType("issue_created");
        testEventModel.setType(TaskEventModelType.PROJECT_TASK);
        publish(testEventModel);

        assertTrue(messageService.channelMessaged);
        assertFalse(messageService.privateMessaged);
    }


    // private methods
    private void createContext() {
        ProjectTaskListener listener = new ProjectTaskListener(messageService);
        context.addApplicationListener(listener);
        context.refresh();
    }

}

