package com.devadmin.vicky.test.service;

import com.devadmin.vicky.model.jira.FieldModel;
import com.devadmin.vicky.model.jira.task.Task;
import com.devadmin.vicky.service.jira.JiraTaskServiceImpl;
import com.devadmin.vicky.service.jira.TaskService;
import net.rcarz.jiraclient.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test {@link JiraTaskServiceImpl}.
 */
public final class JiraServiceTest {

    /**
     * Id of empty comment.
     */
    private static final String EMPTY_COMMENT = "empty";

    /**
     * Id of existing comment.
     */
    private static final String EXISTING_COMMENT = "exist";

    /**
     * Id of comment that throws exception.
     */
    private static final String EXCEPTIONAL_COMMENT = "exception";

    /**
     * Jira service.
     */
    private TaskService taskService;

    /**
     * Init jira service.
     *
     * @throws JiraException if failed
     */
    @Before
    public void init() throws JiraException {
        this.taskService = new JiraTaskServiceImpl(this.prepareClient());
    }

    /**
     * Test that service returns blocker tasks.
     */
    @Test
    public void testBlockerTasks() {
        final List<Task> blockerTasks = this.taskService.getBlockerTasks();
        Assert.assertThat(
                blockerTasks.size(),
                CoreMatchers.is(1)
        );
        final FieldModel fields = blockerTasks.get(0).getFields();
        Assert.assertNotNull(fields);
        Assert.assertNotNull(fields.getAssignee());
        Assert.assertNotNull(fields.getStatus());
        Assert.assertNotNull(fields.getPriority());
    }

    /**
     * Test that service returns comments.
     */
    @Test
    public void testComments() {
        Assert.assertNotNull(this.taskService.getLastCommentByTaskId(EXISTING_COMMENT));
        Assert.assertNull(this.taskService.getLastCommentByTaskId(EMPTY_COMMENT));
        Assert.assertNull(this.taskService.getLastCommentByTaskId(EXCEPTIONAL_COMMENT));
    }

    /**
     * Prepare mocked jira client.
     *
     * @return Mocked jira client
     * @throws JiraException if failed
     */
    private JiraClient prepareClient() throws JiraException {
        final Issue.SearchResult searchResult = Mockito.mock(Issue.SearchResult.class);
        final Issue issue = Mockito.mock(Issue.class);
        this.prepareIssue(issue);
        searchResult.issues = Collections.singletonList(issue);
        final JiraClient jiraClient = Mockito.mock(JiraClient.class, RETURNS_DEEP_STUBS);
        when(jiraClient.searchIssues(any())).thenReturn(searchResult);
        when(jiraClient.getIssue(EMPTY_COMMENT).getComments()).thenReturn(Collections.emptyList());
        when(jiraClient.getIssue(EXISTING_COMMENT).getComments()).thenReturn(Collections.singletonList(Mockito.mock(Comment.class)));
        when(jiraClient.getIssue(EXCEPTIONAL_COMMENT)).thenThrow(new JiraException("Comment doesn't exist"));
        return jiraClient;
    }

    /**
     * Prepare mocked jira issue
     *
     * @param issue Mocked issue
     */
    private void prepareIssue(final Issue issue) {
        final User user = Mockito.mock(User.class);
        this.prepareUserMock(user);

        final Status status = Mockito.mock(Status.class);
        this.prepareStatus(status);

        final Priority priority = Mockito.mock(Priority.class);
        this.preparePriority(priority);

        when(issue.getCreatedDate()).thenReturn(new Date());
        when(issue.getUpdatedDate()).thenReturn(new Date());
        when(issue.getLabels()).thenReturn(Arrays.asList("1", "2", "3"));
        when(issue.getAssignee()).thenReturn(user);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getStatus()).thenReturn(status);
    }

    /**
     * Prepare mocked jira priority.
     *
     * @param priority Mocked priority
     */
    private void preparePriority(final Priority priority) {
        when(priority.getId()).thenReturn("1");
        when(priority.getIconUrl()).thenReturn("example.com");
        when(priority.getName()).thenReturn("Test");
        when(priority.getSelf()).thenReturn("example.com");
    }

    /**
     * Prepare mocked jira status.
     *
     * @param status Mocked status
     */
    private void prepareStatus(final Status status) {
        when(status.getId()).thenReturn("1");
        when(status.getDescription()).thenReturn("Test");
        when(status.getIconUrl()).thenReturn("example.com");
        when(status.getName()).thenReturn("Test");
        when(status.getSelf()).thenReturn("example.com");
    }

    /**
     * Prepare mocked jira user.
     *
     * @param user Mocked user
     */
    private void prepareUserMock(final User user) {
        when(user.getId()).thenReturn("1");
        when(user.getSelf()).thenReturn("example.com");
        when(user.getName()).thenReturn("almas");
        when(user.getEmail()).thenReturn("Lollipop@devadmin.com");
        when(user.getDisplayName()).thenReturn("Lollipop");
    }
}
