package com.devadmin.vicky.serviece;

import com.devadmin.jira.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class JiraService {

  @Value("${jiraUsername}")
  private String jiraUsername;

  @Value("${jiraPassword}")
  private String jiraPassword;

  @Value("${jiraUrl}")
  private String jiraUrl;

  private JiraClient jiraClient;

  @PostConstruct
  public void init() throws JiraException {
    this.jiraClient = new JiraClient(jiraUrl, new BasicCredentials(jiraUsername, jiraPassword));
  }

  public List<Comment> getCommentsByIssueId(String id) throws JiraException {
    return jiraClient.getIssue(id).getComments();
  }
}
