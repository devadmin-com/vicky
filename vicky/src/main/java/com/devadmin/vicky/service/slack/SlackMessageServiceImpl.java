package com.devadmin.vicky.service.slack;

import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.controller.slack.SlackController;
import com.devadmin.vicky.controller.slack.SlackProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Implements a {@link MessageService} with slack as the underlying transport
 */
@Service
public class SlackMessageServiceImpl implements MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlackMessageServiceImpl.class);

  private final SlackProperties properties;

  private final SlackController slackController;

  public SlackMessageServiceImpl(SlackProperties properties, SlackController slackController) {
    this.properties = properties;
    this.slackController = slackController;
  }

  /**
   * Make a POST call to the incoming webhook url.
   *
   * @param message should be
   * @param channelName The project name which contains Jira issue
   */
  @Override
  public void sendChannelMessage(String message, String channelName) {


    RestTemplate restTemplate = new RestTemplate();
    RichMessage richMessage = new RichMessage(message);
    Map<String, String> incomingWebhooks = properties.getWebhook().getIncoming();

    if (incomingWebhooks.containsKey(channelName)){
      String incomingWebhookUrl = incomingWebhooks.get(channelName);
      try {
        restTemplate.postForEntity(incomingWebhookUrl, richMessage, String.class);
      } catch (RestClientException e) {
        LOGGER.error("Error posting to SlackProperties Incoming Webhook: ", e);
      }
    }

  }

  @Override
  public void sendPrivateMessage(String slackMessage, String username) {
    if (username != null && !username.equals("Unassigned")){
      slackController.sendDirectMessageToBot(slackMessage, username);
    }
  }
}
