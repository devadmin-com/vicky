
package com.devadmin.vicky.listener;

/*
 * This class contains listener methods for different jira events
 *
@Component
public class JiraEventListener {

  private final SlackService slackService;

  private final SlackMessageService messageServiceImpl;

  private final Formatter taskFormatter;

  public JiraEventListener(SlackMessageService messageServiceImpl, Formatter taskFormatter, SlackService slackService) {
    this.taskFormatter = taskFormatter;
    this.messageServiceImpl = messageServiceImpl;
    this.slackService = slackService;
  }

  @EventListener(
      classes = TaskEvent.class,
      condition =
          "#jiraEventModel.eventModel.changeLog != null and " +
              "#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_created') or " +
              "(#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_updated') and " +
              "#jiraEventModel.eventModel.issue.fields.status.name.equals('Resolved 解決済'))"
  )
  public void handleIssueCreatedAndResolvedEvents(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "ISSUE");
    slackService.sendChannelMessage(slackMessage, messageEntity.getIssueProject(), messageEntity.getIssueLabels());
  }

  @EventListener(
      classes = TaskEvent.class,
      condition = "#jiraEventModel.assignee")
  public void handleAssignedIssueEvent(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "ASSIGNEE");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = TaskEvent.class,
      condition = "#jiraEventModel.eventModel.webhookEvent.equals('comment_created')")
  public void handleCommentEvent(GenericEvent<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = TaskEvent.class,
      condition =
          "(#jiraEventModel.eventModel.webhookEvent.equals('comment_created') or "
          + "#jiraEventModel.eventModel.webhookEvent.equals('comment_updated')) and "
              + "#jiraEventModel.eventModel.comment.body matches '\\[.*?\\]'")
  public void handleCommentEventContainingUserReferences(GenericEvent<JiraEventModel> jiraEventModel)
      throws VickyException {
    SlackMessageEntity messageEntity = taskFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }
}*/