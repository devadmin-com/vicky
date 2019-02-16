package com.devadmin.vicky.service.jira;

import com.devadmin.jira.Issue;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskService;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.PriorityModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JiraTaskServiceImpl implements TaskService {

  private static String BLOCKER_TASKS_JQL = "status != closed AND priority = Blocker";

  @Autowired private JiraClient jiraClient;

  @Override
  public Collection<Task> getBlockerTasks() {
    List<Task> tasks = null;
    try {
      Issue.SearchResult searchResult = jiraClient.searchIssues(BLOCKER_TASKS_JQL);
      tasks =
          searchResult.issues.stream()
              .map( issue -> convertIssueToIssueModel(issue) )
              .collect(Collectors.toList());
    } catch (JiraException e) {
      e.printStackTrace();
    }
    return tasks;
  }

  public static IssueModel convertIssueToIssueModel(Issue issue) {
    IssueModel issueModel = new IssueModel();
    FieldModel fieldModel = new FieldModel();
    PriorityModel priorityModel = new PriorityModel();

    priorityModel.setName(issue.getPriority().getName());
    priorityModel.setId(issue.getPriority().getId());

    fieldModel.setPriority(priorityModel);
    issueModel.setFields(fieldModel);

    return issueModel;
  }
}
