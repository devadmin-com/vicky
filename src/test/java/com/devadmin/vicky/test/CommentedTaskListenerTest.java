package com.devadmin.vicky.test;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.CommentedTaskListener;
import com.devadmin.vicky.model.jira.JiraEventModel;
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

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                CommentedTaskListener.class,
                SimpleTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class
        }
)
public class CommentedTaskListenerTest {

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
     * test CommentedTaskListener if commenter and assignee are different users
     */
    @Test
    public void testWhenCommenterAndAssigneeAreDifferent() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "This is a simple comment");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, atLeastOnce())
                .sendPrivateMessage(
                        "serpento",
                        this.eventFormatter.format(testEventModel)
                );
    }

    /*
     * Test that message service is not called if assignee and author are equal.
     */
    @Test
    public void testWhenCommenterAndAssigneeAreSameUsers() {
        final JiraEventModel testEventModel = taskModel("testUser", "testUser", "This is a simple comment");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    /**
     * test CommentedTaskListener if event does not contain comment
     */
    @Test
    public void testCommentIsNull() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "This is a simple comment");
        testEventModel.setComment(null);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }
}
