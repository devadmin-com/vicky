package com.devadmin.vicky.test;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.AtReferenceListener;
import com.devadmin.vicky.model.jira.AuthorModel;
import com.devadmin.vicky.model.jira.FieldModel;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.status.StatusModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.IssueTypeModel;
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

import static org.mockito.Mockito.*;

/**
 * Test class for {@link AtReferenceListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                AtReferenceListener.class,
                SimpleTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class,
        }
)
public class AtReferenceListenerTest {

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
     * Nobody will get message because reference format is incorrect.
     */
    @Test
    public void testMessageWithInvalidReference() {
        final JiraEventModel testEventModel = taskModel("Wrong reference format Lollipop");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    /**
     * Nobody will get message because reference format is incorrect.
     */
    @Test
    public void testSkipEmptyComment() {
        final JiraEventModel testEventModel = taskModel("[~lollipop]");
        testEventModel.setComment(null);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test that if reference and author are the same, then don't send notification.
     */
    @Test
    public void testTheSameReferenceAndAuthor() {
        final JiraEventModel testEventModel = taskModel("serpento");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test that multiple users will receive private messages.
     */
    @Test
    public void testMultipleReferences() {
        final JiraEventModel testEventModel = taskModel("[~lollipop] and [~vvorski]");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, atLeastOnce())
                .sendPrivateMessage(
                        "lollipop",
                        this.eventFormatter.format(testEventModel)
                );
        Mockito.verify(
                this.messageService, atLeastOnce())
                .sendPrivateMessage(
                        "vvorski",
                        this.eventFormatter.format(testEventModel)
                );
    }


    /**
     * Test that one user will receive private message.
     */
    @Test
    public void shouldSendPrivateMessage() {
        final JiraEventModel testEventModel = taskModel("[~Lollipop]");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, times(1))
                .sendPrivateMessage(
                        "Lollipop",
                        this.eventFormatter.format(testEventModel)
                );
    }

    /**
     * Create task model with given body.
     *
     * @param body Body to use
     * @return Test task
     */
    private static JiraEventModel taskModel(final String body) {
        final AuthorModel authorModel = new AuthorModel();
        authorModel.setName("serpento");

        final CommentModel comment = new CommentModel();
        comment.setBody(body);
        comment.setAuthor(authorModel);

        final IssueTypeModel issueTypeModel = new IssueTypeModel();
        issueTypeModel.setId("13");

        final StatusModel statusModel = new StatusModel();
        statusModel.setName("test");
        final FieldModel fieldModel = new FieldModel();
        fieldModel.setIssueType(issueTypeModel);
        fieldModel.setStatus(statusModel);
        final IssueModel issueModel = new IssueModel();
        issueModel.setId("13");
        issueModel.setFields(fieldModel);
        JiraEventModel testEventModel = new JiraEventModel();
        testEventModel.setComment(comment);
        testEventModel.setIssue(issueModel);

        return testEventModel;
    }
}
