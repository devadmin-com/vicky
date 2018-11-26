package com.devadmin.vicky.service;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.webhook.JiraWebhookEvent;
import com.devadmin.jira.webhook.models.Field;
import com.devadmin.jira.webhook.models.Item;
import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.bot.VickyBot;
import com.devadmin.vicky.config.VickyProperties;
import com.devadmin.vicky.config.VickyProperties.Slack.Webhook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *  This class contains methods for processing JIRA events @todo, really? why's it called slackwebhookservice?
 *
 * @todo why do we have a slack webhook service and controller and vickybot? why are they in separate packages? how do they relate?
 */
@Component
public class SlackWebhookService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlackWebhookService.class);

  private final VickyProperties vickyProperties;

  private final JiraRestService jiraRestService;

  private final Webhook webhook;

  private final VickyBot vickyBot;

  @Autowired
  public SlackWebhookService(VickyProperties vickyProperties, JiraRestService jiraRestService, Webhook webhook, VickyBot vickyBot) {
    this.webhook = webhook;
    this.vickyBot = vickyBot;
    this.jiraRestService = jiraRestService;
    this.vickyProperties = vickyProperties;
  }

  /**
   * In this method, we try to understand which type of event we received from Jira server
   *
   * @param webhookEvent received event from Jira server
   * @throws JiraException will happen when Jira client couldn't connect to Jira server
   */
  public void eventProcessing(JiraWebhookEvent webhookEvent) throws JiraException {
    String eventType = getEventType(webhookEvent);
    if ("issue".equals(eventType)) {
      issueEventProcessing(webhookEvent);
    } else if ("comment".equals(eventType)) {
      commentEventProcessing(webhookEvent);
    }
  }

  /**
   * In this method we processing comment event
   *
   * @param webhookEvent received event from Jira server
   */
  private void commentEventProcessing(JiraWebhookEvent webhookEvent) {
    String message;
    String comment;
    String commenterId;

    Field issueFields = webhookEvent.getIssue().getFields();
    String issueTypeName = issueFields.getIssueType().getName();
    String issueKey = webhookEvent.getIssue().getKey();
    String issueUrl = String.format("%s/browse/%s", vickyProperties.getJira().getCloudUrl(), issueKey);
    String issueStatusName = issueFields.getStatus().getName();
    String issueSummary = issueFields.getSummary();
    String assignedTo = issueFields.getAssignee() == null
        ? "Unassigned" : issueFields.getAssignee().getName();

    String issueTypeIcon = getIssueTypeIconBasedOnIssueTypeName(issueTypeName);
    comment = webhookEvent.getComment().getBody();
    commenterId = webhookEvent.getComment().getAuthor().getDisplayName();

    message = String.format(issueTypeIcon + " <%s | %s> %s: %s\n %s ➠ %s",
        issueUrl, issueKey, issueStatusName, issueSummary, commenterId, comment);

    List<String> mentionedUsernameListInTheComment = findMentionedUsernameListInTheComment(comment);
    if (mentionedUsernameListInTheComment.size() > 0) {
      for (String username : mentionedUsernameListInTheComment) {
        String replacedMessage = message.replace("[~" + username + "]", "@" + username);
        vickyBot.sendDirectMessageToBot(replacedMessage, username);
        if (!"Unassigned".equals(assignedTo)) {
          vickyBot.sendDirectMessageToBot(replacedMessage, assignedTo);
        }
      }
    } else {
      if (!"Unassigned".equals(assignedTo)) {
        vickyBot.sendDirectMessageToBot(message, assignedTo);
      }
    }
  }

  /**
   * In this method we processing issue event
   *
   * @param webhookEvent received event from Jira server
   */
  private void issueEventProcessing(JiraWebhookEvent webhookEvent) throws JiraException {
    String message;
    String lastComment;

    Field issueFields = webhookEvent.getIssue().getFields();
    String username = webhookEvent.getUser().getName();
    String issueTypeName = issueFields.getIssueType().getName();
    String issueKey = webhookEvent.getIssue().getKey();
    String issueUrl = String.format("%s/browse/%s", vickyProperties.getJira().getCloudUrl(), issueKey);
    String issueProjectName = issueFields.getProject().getName();
    String issueStatusName = issueFields.getStatus().getName();
    String issueSummary = issueFields.getSummary();
    String issuePriorityName = issueFields.getPriority().getName();
    String assignedTo = issueFields.getAssignee() == null
        ? "Unassigned" : issueFields.getAssignee().getDisplayName();
    String issueTypeIcon = getIssueTypeIconBasedOnIssueTypeNameAndIssuePriorityName(issuePriorityName, issueTypeName);


    List<Comment> comments = jiraRestService.getCommentsByIssueId(webhookEvent.getIssue().getId());
    if (comments.size() > 0) {
      String commenterId = comments.get(comments.size() - 1).getAuthor().getDisplayName();
      String comment = comments.get(comments.size() - 1).getBody();
      lastComment = commenterId + " ➠ " + comment;
    } else {
      lastComment = "This ticket did not comment more than 24 hours";
      message = String.format(":no_entry_sign: <%s | %s> %s: %s @%s\n %s",
          issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, lastComment);
      new IssueCommentTracker(webhookEvent, message, jiraRestService, vickyBot, username);
    }

    message = String.format(issueTypeIcon + " <%s | %s> %s: %s @%s\n %s",
        issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, lastComment);

    if (webhookEvent.getChangeLog() != null) {
      for (Item item : webhookEvent.getChangeLog().getItems()) {
        if("assignee".equals(item.getField()) && item.getTo() != null){
          message = String.format(issueTypeIcon + " <%s | %s> %s: %s\n %s",
              issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, lastComment);
          vickyBot.sendDirectMessageToBot(String.format("%s assigned to you: %s",
              username, message), item.getTo());
        }
      }
    } else {
      invokeSlackWebhook(message, issueProjectName);
    }
  }

  /**
   * Find mentioned usernames in the comment
   *
   * @param comment issue current comment
   * @return list of usernames which mentions on comment
   */
  private List<String> findMentionedUsernameListInTheComment(String comment) {
    List<String> userNames = new ArrayList<>();
    Pattern pattern = Pattern.compile("\\[.*?\\]");
    Matcher matcher = pattern.matcher(comment);
    while (matcher.find()){
      String uname = matcher.group().trim();
      userNames.add(uname.substring(2, uname.length()-1));
    }
    return userNames;
  }

  /**
   * Get custom issue type name based on jira issue type name and jira issue priority name
   *
   * @param issuePriorityName jira issue priority name
   * @param issueTypeName jira issue type name
   * @return custom issue type name
   */
  private String getIssueTypeIconBasedOnIssueTypeNameAndIssuePriorityName(String issuePriorityName, String issueTypeName) {
    if ("Blocker".equals(issuePriorityName)) {
      return ":bangbang:";
    } else {
      return getIssueTypeIconBasedOnIssueTypeName(issueTypeName);
    }
  }

  /**
   * Get custom issue type name based on jira issue type name
   *
   * @param issueTypeName jira issue type name
   * @return custom issue type name
   */
  private String getIssueTypeIconBasedOnIssueTypeName(String issueTypeName) {
    switch (issueTypeName) {
      case "Server サーバー":
        return ":hammer_and_wrench:";
      case "Operations 運営":
        return ":gear:";
      case "Urgent Bug 緊急バグ":
        return ":zap:";
      default:
        return ":rocket:";
    }
  }

  /**
   * This method check which type of event we received from Jira
   * @param jiraWebhookEvent received event from Jira server
   * @return the type of event we received
   */
  private String getEventType(JiraWebhookEvent jiraWebhookEvent){
    String eventType = "Unsupported event type";
    switch (jiraWebhookEvent.getWebhookEvent()){
      case "jira:issue_created":
      case "jira:issue_updated":
      case "jira:issue_deleted":
        eventType = "issue";
        break;
      case "comment_created":
      case "comment_updated":
      case "comment_deleted":
        eventType = "comment";
        break;
      case "worklog_created":
      case "worklog_updated":
      case "worklog_deleted":
        eventType = "worklog";
        break;
    }
    return eventType;
  }

  /**
   * Make a POST call to the incoming webhook url.
   *
   * @param message should be
   * @param issueProjectName The project name which contains Jira issue
   */
  private void invokeSlackWebhook(String message, String issueProjectName) {
    RestTemplate restTemplate = new RestTemplate();
    RichMessage richMessage = new RichMessage(message);
    Map<String, String> incomingWebhooks = webhook.getIncoming();


    // For debugging purpose only
    try {
      LOGGER.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
    } catch (JsonProcessingException e) {
      LOGGER.debug("Error parsing RichMessage: ", e);
    }

    try {
      if (incomingWebhooks.containsKey(issueProjectName)){
        String incomingWebhookUrl = incomingWebhooks.get(issueProjectName);
        restTemplate.postForEntity(incomingWebhookUrl, richMessage, String.class);
      }
    } catch (RestClientException e) {
      LOGGER.error("Error posting to SlackProperties Incoming Webhook: ", e);
    }
  }

}
