package com.devadmin.vicky.service.jira;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskService;
import com.devadmin.vicky.controller.jira.model.*;
import net.rcarz.jiraclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class JiraTaskServiceImpl implements TaskService {

  private static final Logger logger = LoggerFactory.getLogger(JiraTaskServiceImpl.class);

  private static String BLOCKER_TASKS_JQL = "status != closed AND priority = Blocker";

  private JiraClient jiraClient;

  @Autowired
  public JiraTaskServiceImpl(JiraClient jiraClient) {
    this.jiraClient = jiraClient;
  }

  @Override
  public List<Task> getBlockerTasks() {
    List<Task> tasks = null;
    try {
      Issue.SearchResult searchResult = jiraClient.searchIssues(BLOCKER_TASKS_JQL);
      tasks =
          searchResult.issues.stream()
              .map(JiraTaskServiceImpl::convertIssueToIssueModel)
              .collect(Collectors.toList());
    } catch (JiraException e) {
      e.printStackTrace();
    }
    return tasks;
  }

  @Override
  public Comment getLastCommentByTaskId(String taskId) {
    Comment lastComment = null;
    try {
      List<Comment> comments = jiraClient.getIssue(taskId).getComments();
      if(comments.size() > 0){
        lastComment = comments.get(comments.size()-1);
      }
    } catch (JiraException e) {
      logger.error("There was a problem getting issue from jira", e.getMessage());
    }
    return lastComment;
  }

  /**
   * @param issue
   * @return issueModel which was converted from issue
   */
  public static IssueModel convertIssueToIssueModel(Issue issue) {
    IssueModel issueModel = new IssueModel();

    FieldModel fieldModel = getFieldModel(issue);
    issueModel.setFields(fieldModel);
    issueModel.setId(issue.getId());
    issueModel.setKey(issue.getKey());
    issueModel.setSelf(issue.getSelf());
    return issueModel;
  }

  /**
   * @param issue
   * @return fieldModel mapped from fields of given issue
   */
  private static FieldModel getFieldModel(Issue issue) {
    FieldModel fieldModel = new FieldModel();

    if (issue != null) {
      fieldModel.setAssignee(getAssignee(issue));
      fieldModel.setStatus(getStatus(issue));
      fieldModel.setPriority(getPriority(issue));
      //todo added a maven dependency of rcarz jira client which doesn't have method issue.getCreatedDate() and issue.getUpdatedDate() methods in its 0.5 version
//      fieldModel.setCreatedDate(issue.getCreatedDate().toString());
//      fieldModel.setUpdatedDate(issue.getUpdatedDate().toString());
      fieldModel.setLabels(issue.getLabels().toArray(new String[0]));

    }
    return fieldModel;
  }

  /**
   * @param issue
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
      logger.error("Exception on getting priority", e);
    }
    return priorityModel;
  }

  /**
   * @param issue
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
      logger.error("Exception on getting status", e.getMessage());
    }
    return statusModel;
  }

  /**
   * @param issue
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
      logger.error("Exception on getting assignee", e.getMessage());
    }

    return userModel;
  }
}
