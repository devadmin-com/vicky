package com.devadmin.vicky.service.jira;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskService;
import com.devadmin.vicky.controller.jira.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rcarz.jiraclient.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class JiraTaskServiceImpl implements TaskService {

    private static final String BLOCKER_TASKS_JQL = "status != closed AND priority = Blocker";

    private final JiraClient jiraClient;

    @Override
    public List<Task> getBlockerTasks() {
        try {
            Issue.SearchResult searchResult = jiraClient.searchIssues(BLOCKER_TASKS_JQL);
            return searchResult.issues.stream()
                    .map(JiraTaskServiceImpl::convertIssueToIssueModel)
                    .collect(Collectors.toList());
        } catch (JiraException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Comment getLastCommentByTaskId(String taskId) {
        Comment lastComment = null;
        try {
            List<Comment> comments = jiraClient.getIssue(taskId).getComments();
            if (!comments.isEmpty()) {
                lastComment = comments.get(comments.size() - 1);
            }
        } catch (JiraException e) {
            log.error("There was a problem getting issue from jira", e.getMessage());
        }
        return lastComment;
    }

    /**
     * @param issue Issue
     * @return issueModel which was converted from issue
     */
    private static IssueModel convertIssueToIssueModel(Issue issue) {
        IssueModel issueModel = new IssueModel();

        FieldModel fieldModel = getFieldModel(issue);
        issueModel.setFields(fieldModel);
        issueModel.setId(issue.getId());
        issueModel.setKey(issue.getKey());
        issueModel.setSelf(issue.getSelf());
        return issueModel;
    }

    /**
     * @param issue Issue
     * @return fieldModel mapped from fields of given issue
     */
    private static FieldModel getFieldModel(Issue issue) {
        FieldModel fieldModel = new FieldModel();

        if (issue != null) {
            fieldModel.setAssignee(getAssignee(issue));
            fieldModel.setStatus(getStatus(issue));
            fieldModel.setPriority(getPriority(issue));
            fieldModel.setCreatedDate(issue.getCreatedDate().toString());
            fieldModel.setUpdatedDate(issue.getUpdatedDate().toString());
            fieldModel.setLabels(issue.getLabels().toArray(new String[0]));

        }
        return fieldModel;
    }

    /**
     * @param issue Issue
     * @return priorityModel mapped from priority of given issue
     */
    private static PriorityModel getPriority(Issue issue) {
        PriorityModel priorityModel = new PriorityModel();

        try {
            Priority priority = issue.getPriority();
            priorityModel.setId(priority.getId());
            priorityModel.setIconUrl(priority.getIconUrl());
            priorityModel.setName(priority.getName());
            priorityModel.setSelf(priority.getSelf());
        } catch (NullPointerException e) {
            log.error("Exception on getting priority", e);
        }
        return priorityModel;
    }

    /**
     * @param issue Issue
     * @return statusModel mapped from status of given issue
     */
    private static StatusModel getStatus(Issue issue) {
        StatusModel statusModel = new StatusModel();
        try {
            Status status = issue.getStatus();
            statusModel.setId(status.getId());
            statusModel.setDescription(status.getDescription());
            statusModel.setIconUrl(status.getIconUrl());
            statusModel.setName(status.getName());
            statusModel.setSelf(status.getSelf());
        } catch (NullPointerException e) {
            log.error("Exception on getting status", e.getMessage());
        }
        return statusModel;
    }

    /**
     * @param issue Issue
     * @return assignee of given issue
     */
    private static UserModel getAssignee(Issue issue) {
        UserModel userModel = new UserModel();
        try {

            User user = issue.getAssignee();
            userModel.setAccountId(user.getId());
            userModel.setSelf(user.getSelf());
            userModel.setName(user.getName());
            userModel.setEmailAddress(user.getEmail());
            userModel.setDisplayName(user.getDisplayName());

        } catch (NullPointerException e) {
            log.error("Exception on getting assignee", e.getMessage());
        }

        return userModel;
    }
}
