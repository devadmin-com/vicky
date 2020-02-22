package com.devadmin.vicky.test;

import com.devadmin.vicky.model.jira.*;
import com.devadmin.vicky.model.jira.comment.CommentModel;
import com.devadmin.vicky.model.jira.status.StatusModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.IssueTypeModel;

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
        statusModel.setName("test");
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
        JiraEventModel testEventModel = new JiraEventModel();
        testEventModel.setComment(comment);
        testEventModel.setIssue(issueModel);
        testEventModel.setTimeStamp(System.currentTimeMillis());
        return testEventModel;
    }
}
