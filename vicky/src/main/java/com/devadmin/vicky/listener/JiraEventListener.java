
package com.devadmin.vicky.listener;

/* TODO what is this?!?!?!?!??!?!?

 * This class contains listener methods for different jira events
 *
@Component
public class JiraEventListener {

  private final SlackService slackService;

  private final SlackMessageService messageServiceImpl;

  private final TaskEventFormatter taskEventFormatter;

  public JiraEventListener(SlackMessageService messageServiceImpl, TaskEventFormatter taskEventFormatter, SlackService slackService) {
    this.taskEventFormatter = taskEventFormatter;
    this.messageServiceImpl = messageServiceImpl;
    this.slackService = slackService;
  }

  @EventListener(
      classes = TaskEventModelWrapper.class,
      condition =
          "#jiraEventModel.eventModel.changeLog != null and " +
              "#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_created') or " +
              "(#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_updated') and " +
              "#jiraEventModel.eventModel.issue.fields.status.name.equals('Resolved 解決済'))"
  )
  public void handleIssueCreatedAndResolvedEvents(EventModelWrapper<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskEventFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "ISSUE");
    slackService.sendChannelMessage(slackMessage, messageEntity.getIssueProject(), messageEntity.getIssueLabels());
  }

  @EventListener(
      classes = TaskEventModelWrapper.class,
      condition = "#jiraEventModel.assignee")
  public void handleAssignedIssueEvent(EventModelWrapper<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskEventFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "ASSIGNEE");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = TaskEventModelWrapper.class,
      condition = "#jiraEventModel.eventModel.webhookEvent.equals('comment_created')")
  public void handleCommentEvent(EventModelWrapper<JiraEventModel> jiraEventModel) throws VickyException {
    SlackMessageEntity messageEntity = taskEventFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }

  @EventListener(
      classes = TaskEventModelWrapper.class,
      condition =
          "(#jiraEventModel.eventModel.webhookEvent.equals('comment_created') or "
          + "#jiraEventModel.eventModel.webhookEvent.equals('comment_updated')) and "
              + "#jiraEventModel.eventModel.comment.body matches '\\[.*?\\]'")
  public void handleCommentEventContainingUserReferences(EventModelWrapper<JiraEventModel> jiraEventModel)
      throws VickyException {
    SlackMessageEntity messageEntity = taskEventFormatter.format(jiraEventModel.getEventModel());
    String slackMessage = messageServiceImpl.writeMessage(messageEntity, "COMMENT");
    slackService.sendPrivateMessage(slackMessage, messageEntity.getIssueAssignedTo());
  }
}*/