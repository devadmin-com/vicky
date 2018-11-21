package com.devadmin.vicky.service;

import com.devadmin.jira.BasicCredentials;
import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.config.VickyProperties.Jira;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JiraRestService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraRestService.class);

  private JiraClient jiraClient;

  public JiraRestService(Jira jira) throws JiraException {
    BasicCredentials basicCredentials = new BasicCredentials(jira.getUsername(), jira.getPassword());
    this.jiraClient = new JiraClient(jira.getCloudUrl(), basicCredentials);
  }

  public List<Comment> getCommentsByIssueId(String id) throws JiraException {
    return jiraClient.getIssue(id).getComments();
  }
}