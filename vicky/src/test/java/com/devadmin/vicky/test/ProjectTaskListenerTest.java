package com.devadmin.vicky.test;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.TaskEventModel;
import com.devadmin.vicky.event.TaskEvent;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


import static org.junit.Assert.*;

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
        publish(testEventModel);

        assertTrue(messageService.channelMessaged == true); // check that a message was sent to the channel
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
        testEventModel.setType("issue_created");
        publish(testEventModel);

        assertEquals(expectedMessage, messageService.lastMessage);
        assertTrue(messageService.channelMessaged);

    }

    /**
     * Tests that handler will not get the event with wrong type
     */
    @Test
    public void eventShouldNotBeHandledWithWrongTypeTest(){

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setType("issue_updated");
        publish(testEventModel);

        assertFalse(messageService.channelMessaged);
    }

    /**
     * Tests that handler will hande the event with correct type
     */
    @Test
    public void eventShouldNotBeHandledWithCorrectTypeTest(){

        createContext();

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setType("issue_created");
        publish(testEventModel);

        assertTrue(messageService.channelMessaged);
    }


    // private methods
    private void createContext() {
        ProjectTaskListener listener = new ProjectTaskListener(messageService);
        context.addApplicationListener(listener);
        context.refresh();
    }

}

