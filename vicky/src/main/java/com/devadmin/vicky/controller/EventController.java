package com.devadmin.vicky.controller;

import com.devadmin.vicky.controller.model.EventModel;
import com.devadmin.vicky.event.GenericEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class contain methods which received events from different services
 */
@RestController
@RequestMapping("event")
public class EventController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public EventController(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/jira")
  public ResponseEntity jiraEvent(@RequestBody EventModel EventModel) {
    final GenericEvent<EventModel> genericEvent = new GenericEvent<>(EventModel);
    applicationEventPublisher.publishEvent(genericEvent);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Jira event published");
    }
    return ResponseEntity.ok().build();
  }

//  @PostMapping("/asana")
//  public ResponseEntity asanaEvent(@RequestBody AsanaEventModel asanaEventModel) {
//    AsanaEvent asanaEvent = new AsanaEvent(this, asanaEventModel);
//    applicationEventPublisher.publishEvent(asanaEventModel);
//    if (log.isDebugEnabled()) {
//      log.debug("Asana event published");
//    }
//    return ResponseEntity.ok().build();
//  }
}
