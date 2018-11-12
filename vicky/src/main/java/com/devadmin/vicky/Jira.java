package com.devadmin.vicky;

import com.devadmin.jira.JiraService;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class Jira {

  private static final Logger LOGGER = LoggerFactory.getLogger(Jira.class);

  @PostConstruct
  public void init() throws JiraException {
    JiraService jiraService = new JiraService("hzq44243@nbzmr.com", "123456654321");
    List<Comment> issueComments = jiraService.getIssueComments();

    issueComments.forEach(comment -> LOGGER.error(comment.getBody()));

  }
}
