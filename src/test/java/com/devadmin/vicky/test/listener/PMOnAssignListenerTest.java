package com.devadmin.vicky.test.listener;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.AssignTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.listener.PMOnAssignListener;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.model.jira.changelog.JiraChangeLogItemModel;
import com.devadmin.vicky.service.slack.MessageService;
import com.devadmin.vicky.service.slack.SlackMessageServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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
import static org.mockito.Mockito.times;

/**
 * Test fpr {@link com.devadmin.vicky.listener.PMOnAssignListener}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                PMOnAssignListener.class,
                AssignTaskEventFormatter.class,
                SlackMessageServiceImpl.class,
                ApplicationEventPublisher.class
        }
)
public class PMOnAssignListenerTest {

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
    @Qualifier("AssignFormatter")
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
                .sendPrivateMessage(
                        "testUser",
                        this.eventFormatter.format(testEventModel)
                );
    }

    /**
     * Test that not assignee field is skipped.
     */
    @Test
    public void testNotAssigneeEvent() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        ((JiraChangeLogItemModel) testEventModel.getChangeLog().getItems().get(0)).setField("not assignee");
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, never())
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    /**
     * Test multiple change logs.
     */
    @Test
    public void testMultipleChangeLogs() {
        final JiraEventModel testEventModel = taskModel("serpento", "testUser", "Test task");
        final List<JiraChangeLogItemModel> itemModels = this.multipleItems();

        Assert.assertThat(itemModels.size(), CoreMatchers.is(2));

        testEventModel.getChangeLog().setItems(itemModels);
        this.applicationEventPublisher.publishEvent(new TaskEventModelWrapper(testEventModel));
        Mockito.verify(
                this.messageService, times(2))
                .sendPrivateMessage(
                        any(),
                        any()
                );
    }

    private List<JiraChangeLogItemModel> multipleItems() {
        final JiraChangeLogItemModel logItemModel = new JiraChangeLogItemModel();
        logItemModel.setField("assignee");
        logItemModel.setTo("testUser2");

        final JiraChangeLogItemModel logItemModel2 = new JiraChangeLogItemModel();
        logItemModel2.setField("assignee");
        logItemModel2.setTo("testUser3");

        return Arrays.asList(logItemModel, logItemModel2);
    }

}
