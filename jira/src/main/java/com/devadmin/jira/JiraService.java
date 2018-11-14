package com.devadmin.jira;

import net.rcarz.jiraclient.*;

import java.util.List;

public class JiraService {

  BasicCredentials creds;
  JiraClient jiraClient;

    public JiraService(String userName, String password) {
        this.creds = new BasicCredentials(userName, password);
        this.jiraClient = new JiraClient("https://vikcy-test.atlassian.net", creds);
    }

  public List<Comment> getIssueComments() throws JiraException {
    return jiraClient.getIssue("TP1-1").getComments();
  }
}

