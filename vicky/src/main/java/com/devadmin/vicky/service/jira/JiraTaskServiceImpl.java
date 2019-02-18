package com.devadmin.vicky.service.jira;

import com.devadmin.jira.Issue;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.Priority;
import com.devadmin.jira.Status;
import com.devadmin.jira.User;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskService;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.PriorityModel;
import com.devadmin.vicky.controller.jira.model.StatusModel;
import com.devadmin.vicky.controller.jira.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JiraTaskServiceImpl implements TaskService {

  private static String BLOCKER_TASKS_JQL = "status != closed AND priority = Blocker";

  private JiraClient jiraClient;

  @Autowired
  public JiraTaskServiceImpl(JiraClient jiraClient) {
    this.jiraClient = jiraClient;
  }

  @Override
  public List<IssueModel> getBlockerTasks() {
    List<IssueModel> tasks = null;
    try {
      Issue.SearchResult searchResult = jiraClient.searchIssues(BLOCKER_TASKS_JQL);
      tasks = searchResult.issues.stream().map(JiraTaskServiceImpl::convertIssueToIssueModel)
          .collect(Collectors.toList());
    } catch (JiraException e) {
      e.printStackTrace();
    }
    return tasks;
  }

  public static IssueModel convertIssueToIssueModel(Issue issue) {
    IssueModel issueModel = new IssueModel();
    FieldModel fieldModel = getFieldModel(issue);
    issueModel.setFields(fieldModel);
    return issueModel;
  }

  private static FieldModel getFieldModel(Issue issue) {
    FieldModel fieldModel = new FieldModel();
    fieldModel.setAssignee(getAssignee(issue));
    fieldModel.setStatus(getStatus(issue));
    fieldModel.setPriority(getPriority(issue));
    fieldModel.setCreatedDate(issue.getCreatedDate().toString());
    fieldModel.setCreatedDate(issue.getUpdatedDate().toString());
    fieldModel.setLabels(issue.getLabels().toArray(new String[0]));
    return fieldModel;
  }

  private static PriorityModel getPriority(Issue issue) {
    Priority priority = issue.getPriority();
    PriorityModel priorityModel = new PriorityModel();
    priorityModel.setId(priority.getId());
    priorityModel.setIconUrl(priority.getIconUrl());
    priorityModel.setName(priority.getName());
    priorityModel.setSelf(priority.getSelf());
    return priorityModel;
  }

  private static StatusModel getStatus(Issue issue) {
    Status status = issue.getStatus();
    StatusModel statusModel = new StatusModel();
    statusModel.setId(status.getId());
    statusModel.setDescription(status.getDescription());
    statusModel.setIconUrl(status.getIconUrl());
    statusModel.setName(status.getName());
    statusModel.setSelf(status.getSelf());
    return statusModel;
  }

  private static UserModel getAssignee(Issue issue) {
    User user = issue.getAssignee();
    UserModel userModel = new UserModel();
    userModel.setAccountId(user.getId());
    userModel.setSelf(user.getSelf());
    userModel.setName(user.getName());
    userModel.setEmailAddress(user.getEmail());
    userModel.setDisplayName(user.getDisplayName());
    return userModel;
  }
}
