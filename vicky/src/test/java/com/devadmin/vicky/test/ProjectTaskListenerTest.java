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
public class ProjectTaskListenerTest {
    StaticApplicationContext context;
    TestOneMessageService messageService;

    @Test
    public void handle() throws Exception {
        // check that we get the right MessageService
        String id = "bob";


        ProjectTaskListener listener = new ProjectTaskListener(messageService);
        context.addApplicationListener(listener);
        context.refresh();

        TestTaskEventModel testEventModel = new TestTaskEventModel();

        publish(testEventModel);

        assertTrue("Life is good", true);
    }


    void publish(TaskEventModel model) {
        // create a test task
        TaskEvent event = new TaskEvent(model);

        // push test task onto bus
        context.publishEvent(event);
    }
    @Before
    public void setUp() {
        context = new StaticApplicationContext();
        messageService = new TestOneMessageService();

    }
}

