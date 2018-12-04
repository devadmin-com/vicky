package com.devadmin.vicky.service;

import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.bot.VickyBot;
import com.devadmin.vicky.config.VickyProperties;
import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.dto.jira.FieldDto;
import com.devadmin.vicky.service.dto.jira.ItemDto;
import com.devadmin.vicky.service.dto.jira.JiraEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class JiraService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraService.class);

  private final VickyProperties vickyProperties;

  private JiraClient jiraClient;

  private final VickyBot vickyBot;

  @Autowired
  public JiraService(VickyProperties vickyProperties, JiraClient jiraClient, VickyBot vickyBot) {
    this.vickyBot = vickyBot;
    this.jiraClient = jiraClient;
    this.vickyProperties = vickyProperties;
  }

  /**
   * In this method, we try to understand which type of event we received from Jira server
   *
   * @param jiraEventDto received event from Jira server
   */
  public void eventProcessing(JiraEventDto jiraEventDto) throws VickyException {
    String eventType = getJiraEventType(jiraEventDto);
    if ("issue".equals(eventType)) {
      issueEventProcessing(jiraEventDto);
    } else if ("comment".equals(eventType)) {
      commentEventProcessing(jiraEventDto);
    }
  }

  /**
   * In this method we processing comment event
   *
   * @param jiraEventDto received event from Jira server
   */
  private void commentEventProcessing(JiraEventDto jiraEventDto) {
    String message;
    String comment;
    String commenterId;

    FieldDto issueFields = jiraEventDto.getIssue().getFields();
    String issueTypeName = issueFields.getIssueType().getName();
    String issueKey = jiraEventDto.getIssue().getKey();
    String issueUrl = String.format("%s/browse/%s", vickyProperties.getJira().getCloudUrl(), issueKey);
    String issueStatusName = issueFields.getStatus().getName();
    String issueSummary = issueFields.getSummary();
    String assignedTo = issueFields.getAssignee() == null
        ? "Unassigned" : issueFields.getAssignee().getName();

    String issueTypeIcon = getIssueTypeIconBasedOnIssueTypeName(issueTypeName);
    comment = jiraEventDto.getComment().getBody();
    commenterId = jiraEventDto.getComment().getAuthor().getDisplayName();

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
   * @param jiraEventDto received event from Jira server
   */
  private void issueEventProcessing(JiraEventDto jiraEventDto) throws VickyException{
    String message;
    String lastComment;

    FieldDto issueFields = jiraEventDto.getIssue().getFields();
    String username = jiraEventDto.getUser().getName();
    String issueTypeName = issueFields.getIssueType().getName();
    String issueKey = jiraEventDto.getIssue().getKey();
    String issueUrl = String.format("%s/browse/%s", vickyProperties.getJira().getCloudUrl(), issueKey);
    String issueProjectName = issueFields.getProject().getName();
    String issueStatusName = issueFields.getStatus().getName();
    String issueSummary = issueFields.getSummary();
    String issuePriorityName = issueFields.getPriority().getName();
    String assignedTo = issueFields.getAssignee() == null
        ? "Unassigned" : issueFields.getAssignee().getDisplayName();
    String issueTypeIcon = getIssueTypeIconBasedOnIssueTypeNameAndIssuePriorityName(issuePriorityName, issueTypeName);

    List<Comment> comments;
    String issueId = jiraEventDto.getIssue().getId();
    try {
      comments = jiraClient.getIssue(issueId).getComments();
    } catch (JiraException e) {
      throw new VickyException("Faild to retrive issue by issueId: " + issueId, e);
    }
    if (comments.size() > 0) {
      String commenterId = comments.get(comments.size() - 1).getAuthor().getDisplayName();
      String comment = comments.get(comments.size() - 1).getBody();
      lastComment = commenterId + " ➠ " + comment;
    } else {
      lastComment = "This ticket did not comment more than 24 hours";
      message = String.format(":no_entry_sign: <%s | %s> %s: %s @%s\n %s",
          issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, lastComment);
      new IssueCommentTracker(jiraEventDto, message, jiraClient, vickyBot, username);
    }

    message = String.format(issueTypeIcon + " <%s | %s> %s: %s @%s\n %s",
        issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, lastComment);

    if (jiraEventDto.getChangeLog() != null) {
      for (ItemDto item : jiraEventDto.getChangeLog().getItems()) {
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
   * @param jiraEventDto received event from Jira server
   * @return the type of event we received
   */
  private String getJiraEventType(JiraEventDto jiraEventDto){
    String eventType = "Unsupported event type";
    switch (jiraEventDto.getWebhookEvent()){
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
    Map<String, String> incomingWebhooks = vickyProperties.getSlack().getWebhook().getIncoming();


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
