package com.devadmin.vicky.test;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.SummaryTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.CreatedTaskListener;
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

import static com.devadmin.vicky.test.TestTasks.PROJECT;
import static com.devadmin.vicky.test.TestTasks.taskModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * Test class for {@link CreatedTaskListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                CreatedTaskListener.class,
                SummaryTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class
        }
)
public class CreatedTaskListenerTest {

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
    @Qualifier("SummaryFormatter")
    private TaskEventFormatter eventFormatter;

    /**
     * Mocked slack message sender.
     */
    @MockBean
    private MessageService messageService;

    /**
     * Test that channel will send notification.
     */
    @Test
    public void testSendNotificationOnCreatedEvent() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.setType(TaskEventType.CREATED);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, atLeastOnce())
                .sendChannelMessage(
                        PROJECT,
                        this.eventFormatter.format(testEventModel)
                );
    }

    /**
     * Test that UPDATED type will be skipped.
     */
    @Test
    public void testWrongEventType() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.setType(TaskEventType.UPDATED);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendChannelMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test that unsupported issue ids ae skipped
     */
    @Test
    public void testSkipIssueId() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.getIssue().getFields().getIssueType().setId("228");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendChannelMessage(
                        any(),
                        any()
                );
    }
}
