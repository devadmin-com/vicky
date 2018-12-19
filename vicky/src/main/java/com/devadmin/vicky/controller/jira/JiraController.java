package com.devadmin.vicky.controller.jira;

import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devadmin.vicky.event.*;

/**
 * This class contain methods which receive events from JIRA
 */
@RestController
@RequestMapping("event")
public class JiraController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraController.class);

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public JiraController(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/jira")
  public ResponseEntity jiraEvent(@RequestBody JiraEventModel jiraEventModel) {

    switch(jiraEventModel.getIssueEventTypeName()) {

      case "issue_created" :
        jiraEventModel.setType(TaskEventModelType.CREATED);
        break;

      case "issue_updated" :
        jiraEventModel.setType(TaskEventModelType.UPDATED);
        break;

      case "comment_created" :
      case "comment_updated" :
        jiraEventModel.setType(TaskEventModelType.COMMENT);
        break;
    }

    final TaskEvent event = new TaskEvent(jiraEventModel);
    applicationEventPublisher.publishEvent(event);

    return ResponseEntity.ok().build();

  }

}
