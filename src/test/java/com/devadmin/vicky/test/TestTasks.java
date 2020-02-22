package com.devadmin.vicky.test;

import com.devadmin.vicky.model.jira.*;
import com.devadmin.vicky.model.jira.changelog.ChangeLogModel;
import com.devadmin.vicky.model.jira.changelog.JiraChangeLogItemModel;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.status.StatusModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.IssueTypeModel;
import com.devadmin.vicky.model.jira.task.TaskEventType;

import java.util.Collections;

/**
 * Create test tasks.
 */
public final class TestTasks {

    public static final String PROJECT = "vicky";

    public static JiraEventModel taskModel(final String creator, final String assignee, final String body) {
        final AuthorModel authorModel = new AuthorModel();
        authorModel.setName(creator);
        authorModel.setDisplayName(creator);
        authorModel.setEmailAddress(creator);

        final CommentModel comment = new CommentModel();
        comment.setBody(body);
        comment.setAuthor(authorModel);


        final IssueTypeModel issueTypeModel = new IssueTypeModel();
        issueTypeModel.setId("13");

        final StatusModel statusModel = new StatusModel();
        statusModel.setName(StatusModel.RESOLVED);
        statusModel.setDescription("Bla bla");

        final UserModel userModel = new UserModel();
        userModel.setName(assignee);
        userModel.setDisplayName(assignee);
        userModel.setEmailAddress(assignee);

        final ProjectModel projectModel = new ProjectModel();
        projectModel.setName(PROJECT);

        final FieldModel fieldModel = new FieldModel();
        fieldModel.setIssueType(issueTypeModel);
        fieldModel.setStatus(statusModel);
        fieldModel.setAssignee(userModel);
        fieldModel.setProject(projectModel);

        final IssueModel issueModel = new IssueModel();
        issueModel.setId("13");
        issueModel.setFields(fieldModel);

        final JiraChangeLogItemModel logItemModel = new JiraChangeLogItemModel();
        logItemModel.setField("assignee");
        logItemModel.setTo(assignee);

        final ChangeLogModel changeLogModel = new ChangeLogModel();
        changeLogModel.setId("13");
        changeLogModel.setItems(Collections.singletonList(logItemModel));

        final JiraEventModel testEventModel = new JiraEventModel();
        testEventModel.setComment(comment);
        testEventModel.setUser(userModel);
        testEventModel.setIssue(issueModel);
        testEventModel.setTimeStamp(System.currentTimeMillis());
        testEventModel.setChangeLog(changeLogModel);
        testEventModel.setType(TaskEventType.UPDATED);

        return testEventModel;
    }
}
