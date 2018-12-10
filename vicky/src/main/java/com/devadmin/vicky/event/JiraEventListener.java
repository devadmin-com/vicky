
package com.devadmin.vicky.event;

import com.devadmin.vicky.controller.model.jira.JiraEventModel;
import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.MessageConverter;
import com.devadmin.vicky.service.SlackService;
import com.devadmin.vicky.service.impl.MessageServiceImpl;
import com.devadmin.vicky.service.message.SlackMessageEntity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * This class contains listener methods for different jira events
 */
@Component
public class JiraEventListener {

  private final SlackService slackService;

  private final MessageServiceImpl messageServiceImpl;

  private final MessageConverter messageConverter;

  public JiraEventListener(MessageServiceImpl messageServiceImpl, MessageConverter messageConverter, SlackService slackService) {
    this.messageConverter = messageConverter;
    this.messageServiceImpl = messageServiceImpl;
    this.slackService = slackService;
  }

  @EventListener(
      classes = GenericEvent.class,
      condition =
          "#jiraEventModel.eventModel.changeLog != null and " +
              "#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_created') or " +
              "(#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_updated') and " +
              "#jiraEventModel.eventModel.issue.fields.status.name.equals('Resolved 解決済'))"
  )
  public void handleIssueCreatedAndResolvedEvents(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = messageConverter.convert(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.createMessage(messageEntity, "ISSUE");
    slackService.sendChannelMessage(slackMessage, messageEntity.getIssueProject(), messageEntity.getIssueLabels());
  }

  @EventListener(
      classes = GenericEvent.class,
      condition = "#jiraEventModel.assignee")
  public void handleAssignedIssueEvent(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = messageConverter.convert(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.createMessage(messageEntity, "ASSIGNEE");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = GenericEvent.class,
      condition = "#jiraEventModel.eventModel.webhookEvent.equals('comment_created')")
  public void handleCommentEvent(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = messageConverter.convert(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.createMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = GenericEvent.class,
      condition =
          "(#jiraEventModel.eventModel.webhookEvent.equals('comment_created') or "
          + "#jiraEventModel.eventModel.webhookEvent.equals('comment_updated')) and "
              + "#jiraEventModel.eventModel.comment.body matches '\\[.*?\\]'")
  public void handleCommentEventContainingUserReferences(GenericEvent<JiraEventModel> jiraEventModel)
      throws VickyException {
    SlackMessageEntity messageEntity = messageConverter.convert(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.createMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }
}