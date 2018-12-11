package com.devadmin.vicky.controller.jira;

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
    final TaskEvent event = new TaskEvent(jiraEventModel);
 /*  @todo what is this mystery code trying to do?!
 if (jiraEventModel.getChangeLog() != null){
      if (jiraEventModel.getChangeLog().getItems() != null){
        for (ItemModel itemModel : jiraEventModel.getChangeLog().getItems()) {
          if (itemModel.getField().equals("assignee")) event.setAssignee(true);
        }
      }
    }*/
    applicationEventPublisher.publishEvent(event);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Jira event published");
    }
    return ResponseEntity.ok().build();
  }

}
