package com.devadmin.vicky.test;

import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.UserModel;
import com.devadmin.vicky.model.jira.task.TaskType;
import com.devadmin.vicky.model.jira.AuthorModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.FieldModel;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.listener.CommentedTaskListener;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class CommentedTaskListenerTest extends TaskListenerTest {

    @Override
    TaskEventFormatter getTaskEventFormatter() {
        return new SimpleTaskEventFormatter();
    }

    /**
     * test CommentedTaskListener if commenter and assignee is different users
     */
    @Test
    public void testWhenCommenterAndAssigneeIsDifferentUsers() {
        createContext();

        CommentModel comment = new CommentModel();
        comment.setBody("This is a simple comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("serpento");
        comment.setAuthor(authorModel);

        TestTask testTask = new TestTask();
        FieldModel fieldModel = new FieldModel();
        UserModel userModel = new UserModel();
        userModel.setDisplayName("testDisplayName");
        fieldModel.setAssignee(userModel);
        testTask.setFieldModel(fieldModel);
        testTask.setStatus("test status");
        testTask.setType(TaskType.OTHER);
        testTask.setAssignee("testUser");


        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setComment(comment);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
    }

    /**
     * test CommentedTaskListener if commenter and assignee is same users
     */
    @Test
    public void testWhenCommenterAndAssigneeIsSameUsers() {
        createContext();

        CommentModel comment = new CommentModel();
        comment.setBody("This is a simple comment");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setDisplayName("testUser");
        comment.setAuthor(authorModel);

        TestTask testTask = new TestTask();
        FieldModel fieldModel = new FieldModel();
        UserModel userModel = new UserModel();
        userModel.setDisplayName("testUser");
        fieldModel.setAssignee(userModel);
        testTask.setFieldModel(fieldModel);
        testTask.setType(TaskType.OTHER);
        testTask.setAssignee("testUser");

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setComment(comment);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * test CommentedTaskListener if event does not contain comment
     */
    @Test
    public void testWhenCommentModelInEventIsNull() {
        createContext();

        TestTask testTask = new TestTask();
        testTask.setType(TaskType.OTHER);
        testTask.setAssignee("testUser");

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertFalse(testMessageService.wasPMed());
    }

    /**
     * test CommentedTaskListener if event does not contain comment
     */
    @Test
    public void testWhenCommentTextMoreThen256Characters() {
        createContext();

        String expectedMessage =
                ":rocket: <https://devadmin.atlassian.net/browse/TL-000 | TL-000> OTHER: Test Task @testUser\n "
                        + "Serpento ➠ Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been "
                        + "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and "
                        + "scrambled it to make a type specimen book. It has ...";

        CommentModel comment = new CommentModel();
        comment.setBody(
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's "
                        + "standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it "
                        + "to make a type specimen book. It has survived not only five centuries, but also the leap into electronic "
                        + "typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset "
                        + "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus "
                        + "PageMaker including versions of Lorem Ipsum.");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("serpento");
        authorModel.setDisplayName("Serpento");
        comment.setAuthor(authorModel);

        TestTask testTask = new TestTask();
        FieldModel fieldModel = new FieldModel();
        UserModel userModel = new UserModel();
        userModel.setDisplayName("testUser");
        fieldModel.setAssignee(userModel);
        testTask.setFieldModel(fieldModel);
        testTask.setStatus("Test status");
        testTask.setType(TaskType.OTHER);
        testTask.setAssignee("testUser");

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setComment(comment);
        testEventModel.setTask(testTask);

        publish(testEventModel);

        assertFalse(testMessageService.wasChannelMsged());
        assertTrue(testMessageService.wasPMed());
        assertEquals(expectedMessage.substring(0, 10), testMessageService.getPrivateMsg().get(0).getMessage().substring(0, 10));
    }

    // private methods
    private void createContext() {
        CommentedTaskListener listener = new CommentedTaskListener(testMessageService, taskEventFormatter, Collections.singletonList("13"), eventService);
        context.addApplicationListener(listener);
        context.refresh();
    }
}
