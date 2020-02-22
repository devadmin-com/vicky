package com.devadmin.vicky.test;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.LabeledTaskListener;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.devadmin.vicky.service.slack.MessageService;
import com.devadmin.vicky.service.slack.SlackMessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.devadmin.vicky.test.TestTasks.taskModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * Test class for {@link LabeledTaskListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                LabeledTaskListener.class,
                SimpleTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class
        }
)
public class LabeledTaskListenerTest {

    /**
     * Event publisher.
     */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Event formatter to test.
     * Add qualifier to avoid warning from IDEA
     */
    @Autowired
    @Qualifier("SimpleFormatter")
    private TaskEventFormatter eventFormatter;

    /**
     * Mocked slack message sender.
     */
    @MockBean
    private MessageService messageService;

    /**
     * Sip event if it doesn't have labels.
     */
    @Test
    public void eventShouldNotBeHandledWithoutLabelsTest() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.getTask().getFields().setLabels(new String[0]);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendChannelMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test that channel skip updated type.
     */
    @Test
    public void testSendNotificationOnCreatedEvent() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.setType(TaskEventType.UPDATED);
        testEventModel.getTask().getFields().setLabels(new String[]{"first", "second"});
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendChannelMessage(
                        any(),
                        any()
                );
    }

    /**
     * Tests that the message was sent two times.
     */
    @Test
    public void testMuptypleLabels() {
        final List<String> labels = Arrays.asList("first", "second");
        final JiraEventModel testTaskEventModel = taskModel("serpento", "testUser", "Test task");
        testTaskEventModel.getTask().getFields().setLabels(labels.toArray(new String[0]));
        testTaskEventModel.setType(TaskEventType.CREATED);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testTaskEventModel));
        for (final String label : labels) {
            Mockito.verify(
                    this.messageService, atLeastOnce())
                    .sendChannelMessage(
                            label,
                            this.eventFormatter.format(testTaskEventModel)
                    );
        }
    }
}
