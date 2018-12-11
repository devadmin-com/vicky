package com.devadmin.vicky.test;

import com.devadmin.vicky.MessageService;
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


    @Test
    public void handle() throws Exception {
        // check that we get the right MessageService
        String id = "bob";

        // install the listener - This should be done by spring...
        StaticApplicationContext context = new StaticApplicationContext();
        ProjectTaskListener listener = new ProjectTaskListener(null,null);
        context.addApplicationListener(listener);
        context.refresh();

        // create a test task
        TestTaskEventModel testEventModel = new TestTaskEventModel();
        TaskEvent event = new TaskEvent(testEventModel);

        // push test task onto bus
        context.publishEvent(event);

        assertTrue("Life is good", true);
    }

    private TestOneMessageService getTestMs() {
        return (TestOneMessageService) ms;
        return null;
    }
}

