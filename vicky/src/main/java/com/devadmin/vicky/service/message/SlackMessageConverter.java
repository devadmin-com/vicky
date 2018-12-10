package com.devadmin.vicky.service.message;

import com.devadmin.vicky.config.VickyProperties;
import com.devadmin.vicky.controller.model.jira.FieldModel;
import com.devadmin.vicky.controller.model.jira.ItemModel;
import com.devadmin.vicky.controller.model.jira.JiraEventModel;
import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.MessageConverter;
import java.util.List;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@link SlackMessageConverter}
 */
@Component
public class SlackMessageConverter implements MessageConverter {

  private final VickyProperties vickyProperties;

  private final JiraClient jiraClient;

  @Autowired
  public SlackMessageConverter(VickyProperties vickyProperties, JiraClient jiraClient) {
    this.vickyProperties = vickyProperties;
    this.jiraClient = jiraClient;
  }

  public SlackMessageEntity convert(JiraEventModel jiraEventModel) throws VickyException {
    SlackMessageEntity message = new SlackMessageEntity();
    FieldModel issueFields = jiraEventModel.getIssue().getFields();
    String cloudUrl = vickyProperties.getJira().getCloudUrl();
    String issueKey = jiraEventModel.getIssue().getKey();
    String issueType = issueFields.getIssueType().getName();
    String issuePriority = issueFields.getPriority().getName();

    message.setIssueType(issueType);
    message.setIssueKey(issueKey);
    message.setIssueUrl(String.format("%s/browse/%s", cloudUrl, issueKey));
    message.setIssueStatus(issueFields.getStatus().getName());
    message.setIssueSummary(issueFields.getSummary());
    if (jiraEventModel.getUser() != null)
      message.setIssueCreator(jiraEventModel.getUser().getName());
    message.setIssueProject(issueFields.getProject().getName());
    message.setIssuePriority(issuePriority);
    message.setIssueTypeIcon(getIssueTypeIcon(issuePriority, issueType));
    message.setIssueLabels(issueFields.getLabels());

    if (issueFields.getAssignee() == null){
      message.setIssueAssignedTo("Unassigned");
    } else {
      message.setIssueAssignedTo(issueFields.getAssignee().getName());
    }

    if (jiraEventModel.getChangeLog() != null) {
      for (ItemModel item : jiraEventModel.getChangeLog().getItems()) {
        if("assignee".equals(item.getField()) && item.getTo() != null){
          message.setIssueAssignedFrom(item.getTo());
        }
      }
    }

    message.setIssueDescription(getIssueDescription(issueFields));

    List<Comment> comments;
    String issueId = jiraEventModel.getIssue().getId();
    try {
      comments = jiraClient.getIssue(issueId).getComments();
    } catch (JiraException e) {
      throw new VickyException("Failed to retrieve issue by issueId: " + issueId, e);
    }

    if (jiraEventModel.getComment() == null) {
      if (comments.size() > 0){
        message.setIssueCommenter(comments.get(comments.size() - 1).getAuthor().getDisplayName());
        message.setIssueComment(comments.get(comments.size() - 1).getBody());
      } else {
//        new IssueCommentTracker(eventDto, message, jiraClient, vickyBot, username);
      }
    } else {
      message.setIssueCommenter(jiraEventModel.getComment().getAuthor().getDisplayName());
      message.setIssueComment(jiraEventModel.getComment().getBody());
    }

    return message;
  }

  private String getIssueDescription(FieldModel issueFields) {
    StringBuilder stb = new StringBuilder();
    String[] descriptionLines = issueFields.getStatus().getDescription().split("\r\n|\r|\n");
    if (descriptionLines.length > 5) {
      for (int index = 0; index < 5; index++) {
        stb.append(descriptionLines[index]);
      }
    } else {
      stb.append(issueFields.getStatus().getDescription());
    }
    return stb.toString();
  }

  /**
   * Get custom issue type name based on jira issue type name and jira issue priority name
   *
   * @param issuePriorityName jira issue priority name
   * @param issueTypeName jira issue type name
   * @return custom issue type name
   */
  private String getIssueTypeIcon(String issuePriorityName, String issueTypeName) {
    if ("Blocker".equals(issuePriorityName)) {
      return ":bangbang:";
    } else {
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
  }
}
