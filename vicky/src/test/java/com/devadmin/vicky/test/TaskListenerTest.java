package com.devadmin.vicky.test;

import com.devadmin.vicky.TaskEventModel;
import com.devadmin.vicky.event.TaskEvent;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Before;
import org.junit.Test;
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
    void publish(TaskEventModel model) {
        // create a test task
        TaskEvent event = new TaskEvent(model);

        // push test task onto bus
        context.publishEvent(event);
    }

    @Before
    public void setUp() {
        context = new StaticApplicationContext();
        testMessageService = new TestMessageService();

    }
}

