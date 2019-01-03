package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEvent;
import org.junit.Before;
import org.springframework.context.support.StaticApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * Base class for listener tests
 *
 */
public class TaskListenerTest {
    StaticApplicationContext context;
    TestMessageService testMessageService;


    /**
     * Publishes the event as a TaskEvent to the testing event bus.
     */
    void publish(TaskEvent model) {
        // create a test task
        com.devadmin.vicky.event.TaskEvent event = new com.devadmin.vicky.event.TaskEvent(model);

        // push test task onto bus
        context.publishEvent(event);
    }

    @Before
    public void setUp() {
        context = new StaticApplicationContext();
        testMessageService = new TestMessageService();

    }
}

