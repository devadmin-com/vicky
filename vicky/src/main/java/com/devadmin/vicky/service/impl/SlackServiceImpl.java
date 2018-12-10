package com.devadmin.vicky.service.impl;

import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.bot.VickyBot;
import com.devadmin.vicky.config.VickyProperties;
import com.devadmin.vicky.service.SlackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * {@link SlackService}
 */
@Service
public class SlackServiceImpl implements SlackService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlackServiceImpl.class);

  private final VickyProperties vickyProperties;

  private final VickyBot vickyBot;

  public SlackServiceImpl(VickyProperties vickyProperties, VickyBot vickyBot) {
    this.vickyProperties = vickyProperties;
    this.vickyBot = vickyBot;
  }

  /**
   * Make a POST call to the incoming webhook url.
   *
   * @param message should be
   * @param projectName The project name which contains Jira issue
   * @param labels Jira issue labels
   */
  @Override
  public void sendChannelMessage(String message, String projectName, String[] labels) {
    RestTemplate restTemplate = new RestTemplate();
    RichMessage richMessage = new RichMessage(message);
    Map<String, String> incomingWebhooks = vickyProperties.getSlack().getWebhook().getIncoming();

    try {
      LOGGER.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
    } catch (JsonProcessingException e) {
      LOGGER.debug("Error parsing RichMessage: ", e);
    }

    try {
      if (incomingWebhooks.containsKey(projectName)){
        String incomingWebhookUrl = incomingWebhooks.get(projectName);
        restTemplate.postForEntity(incomingWebhookUrl, richMessage, String.class);
      }

      if (labels != null && labels.length > 0){
        for (String label: labels) {
          restTemplate.postForEntity(incomingWebhooks.get(label), richMessage, String.class);
        }
      }
    } catch (RestClientException e) {
      LOGGER.error("Error posting to SlackProperties Incoming Webhook: ", e);
    }
  }

  @Override
  public void sendPrivateMessage(String slackMessage, String username) {
    if (username != null && !username.equals("Unassigned")){
      vickyBot.sendDirectMessageToBot(slackMessage, username);
    }
  }
}
