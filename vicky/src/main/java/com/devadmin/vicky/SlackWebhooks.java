package com.devadmin.vicky;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.webhook.JiraIssueWebhookEvent;
import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.serviece.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This is a Slack Webhook sample. Webhooks are nothing but POST calls to Slack with data relevant
 * to your users. You can send the data in the POST call in either ways: 1) Send as a JSON string as
 * the payload parameter in a POST request 2) Send as a JSON string as the body of a POST request
 *
 * @author ramswaroop
 * @version 1.0.0, 21/06/2016
 */
@RestController
public class SlackWebhooks {

  private static final Logger logger = LoggerFactory.getLogger(SlackWebhooks.class);

  /**
   * The Url you get while configuring a new incoming webhook on Slack. You can setup a new incoming
   * webhook <a href="https://my.slack.com/services/new/incoming-webhook/">here</a>.
   */
  @Value("${slackIncomingWebhookUrl}")
  private String slackIncomingWebhookUrl;

  @Value("${jiraUrl}")
  private String jiraUrl;

  private final JiraService jiraService;

  @Autowired
  public SlackWebhooks(JiraService jiraService) {
    this.jiraService = jiraService;
  }

  /**
   * Make a POST call to the incoming webhook url.
   *
   * @param message should be
   */
  public void invokeSlackWebhook(String message) {

    RestTemplate restTemplate = new RestTemplate();
    RichMessage richMessage = new RichMessage(message);

    // For debugging purpose only
    try {
      logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
    } catch (JsonProcessingException e) {
      logger.debug("Error parsing RichMessage: ", e);
    }

    try {
      restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage, String.class);
    } catch (RestClientException e) {
      logger.error("Error posting to Slack Incoming Webhook: ", e);
    }
  }

  @PostMapping("/ticket")
  public void incomingIssueEvent(@RequestBody JiraIssueWebhookEvent webhookEvent) throws JiraException {
    String message;
    String comment;
    String commenterId;

    String issueTypeName = webhookEvent.getIssue().getFields().getIssueType().getName();
    String issueKey = webhookEvent.getIssue().getKey();
    String issueUrl = String.format("%s/browse/%s", jiraUrl, issueKey);
    String issueStatusName = webhookEvent.getIssue().getFields().getStatus().getName();
    String issueSummary = webhookEvent.getIssue().getFields().getSummary();
    String issuePriorityName = webhookEvent.getIssue().getFields().getPriority().getName();
    String assignedTo =
        webhookEvent.getIssue().getFields().getAssignee() == null
            ? "Unassigned"
            : webhookEvent.getIssue().getFields().getAssignee().getDisplayName();

    List<Comment> comments = jiraService.getCommentsByIssueId(webhookEvent.getIssue().getId());
    if (comments.size() > 0) {
      commenterId = comments.get(comments.size() - 1).getAuthor().getDisplayName();
      comment = comments.get(comments.size() - 1).getBody();
    } else {
      commenterId = "Vicky";
      comment = "There is no comment yet ;)";
    }

    switch (issueTypeName) {
      case "Server": {
          message = String.format(":hammer_and_wrench: <%s | %s> %s: %s @%s\n %s ➠ %s",
                  issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, commenterId, comment);
          break;
        }
      case "Operations": {
          message =String.format(":gear: <%s | %s> %s: %s @%s\n %s ➠ %s",
                  issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, commenterId, comment);
        }
        break;
      case "Urgent bug":{
          message = String.format(":zap: <%s | %s> %s: %s @%s\n %s ➠ %s",
                  issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, commenterId, comment);
          break;
        }
      default: {
          message = String.format(":rocket: <%s | %s> %s: %s @%s\n %s ➠ %s",
                  issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, commenterId, comment);
        }
    }

    if ("Blocker tickets".equals(issuePriorityName)) {
      message = String.format(":bangbang: <%s | %s> %s: %s @%s\n %s ➠ %s",
              issueUrl, issueKey, issueStatusName, issueSummary, assignedTo, commenterId, comment);
    }

    invokeSlackWebhook(message);
  }
}
