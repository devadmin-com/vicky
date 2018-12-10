//package com.devadmin.vicky.event.listener;
//
//import com.devadmin.vicky.controller.model.jira.JiraEventModel;
//import com.devadmin.vicky.event.GenericEvent;
//import com.devadmin.vicky.exception.VickyException;
//import com.devadmin.vicky.service.impl.MessageServiceImpl;
//import com.devadmin.vicky.service.impl.SlackServiceImpl;
//import com.devadmin.vicky.service.message.SlackMessageConverter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class JiraEventListenerTest {
//
//  @Mock
//  private SlackMessageConverter messageConverter;
//
//  @Mock
//  private MessageServiceImpl messageService;
//
//  @Mock
//  private SlackServiceImpl slackService;
//
//  @InjectMocks
////  private JiraEventListener jiraEventListener = new JiraEventListener(messageService, messageConverter, slackService);
//
//  private JiraEventModel jiraEventModel;
//
//  @Before
//  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
//    ObjectMapper mapper = new ObjectMapper();
//    jiraEventModel = mapper.readValue(new File("path to issue-created.json"), JiraEventModel.class);
//  }
//
//  @Test
//  public void handleIssueCreatedAndResolvedEvents() throws VickyException {
//    GenericEvent<JiraEventModel> genericEvent = new GenericEvent<>(jiraEventModel);
////    jiraEventListener.handleCommentEvent(genericEvent);
//
//  }
//
//  @Test
//  public void handleAssignedIssueEvent() {
//  }
//
//  @Test
//  public void handleCommentEvent() {
//  }
//
//  @Test
//  public void handleCommentEventContainingUserReferences() {
//  }
//}