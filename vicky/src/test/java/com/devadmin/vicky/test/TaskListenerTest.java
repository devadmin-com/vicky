package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Base class for listener tests
 *
 */
public class TaskListenerTest {
    StaticApplicationContext context;
    TestMessageService testMessageService;

    @Qualifier("SimpleFormatter")
    TaskEventFormatter taskEventFormatter;


    /**
     * Publishes the event as a TaskEventModelWrapper to the testing event bus.
     */
    void publish(TaskEvent model) {
        // create a test task
        TaskEventModelWrapper event = new TaskEventModelWrapper(model);

        // push test task onto bus
        context.publishEvent(event);
    }

    @Before
    public void setUp() {
        context = new StaticApplicationContext();
        testMessageService = new TestMessageService();

    }
}

