package com.devadmin.vicky.test;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.ResolvedTaskListener;
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

import static com.devadmin.vicky.test.TestTasks.taskModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * Test fpr {@link com.devadmin.vicky.listener.ResolvedTaskListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                ResolvedTaskListener.class,
                SimpleTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class
        }
)
public class ResolvedTestListenerTest {

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
     * Test that sends notification.
     */
    @Test
    public void testSendNotificationOnCreatedEvent() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, atLeastOnce())
                .sendChannelMessage(
                        TestTasks.PROJECT,
                        this.eventFormatter.format(testEventModel)
                );
    }

    /**
     * Test that listener skip non updated status.
     */
    @Test
    public void testSkipByStatus() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        testEventModel.setType(TaskEventType.COMMENT);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendChannelMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test that listener skip non updated status.
     */
    @Test
    public void testSkipUnsupportedId() {
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
