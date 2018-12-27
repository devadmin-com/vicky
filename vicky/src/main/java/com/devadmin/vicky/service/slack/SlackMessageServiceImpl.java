package com.devadmin.vicky.service.slack;

import com.devadmin.slack.bot.SlackApiEndpoints;
import com.devadmin.slack.bot.models.Event;
import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.slack.bot.models.User;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.controller.slack.SlackProperties;
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

  private final SlackApiEndpoints slackApiEndpoints;

  private final RestTemplate restTemplate;

  public SlackMessageServiceImpl(SlackProperties properties, RestTemplate restTemplate,
      SlackApiEndpoints slackApiEndpoints) {
    this.properties = properties;
    this.restTemplate = restTemplate;
    this.slackApiEndpoints = slackApiEndpoints;
  }

  /**
   * (non-javadoc)
   *
   * @see MessageService#sendChannelMessage(String, String)
   */
  @Override
  public void sendChannelMessage(String channelName, String message) throws MessageServiceException {
    RichMessage richMessage = new RichMessage(message);
    Map<String, String> incomingWebhooks = properties.getWebhook().getIncoming();
    if (incomingWebhooks.containsKey(channelName)) {
      String incomingWebhookUrl = incomingWebhooks.get(channelName);
      try {
        restTemplate.postForEntity(incomingWebhookUrl, richMessage, String.class);
      } catch (RestClientException e) {
        LOGGER.error("Unable to posting IncomingWebhookURL given from Slack Properties: {}", e);
        throw new MessageServiceException(e.getMessage(), e);
      }
    }

  }

  /**
   * (non-javadoc)
   *
   * @see MessageService#sendPrivateMessage(String, String)
   */
  @Override
  public void sendPrivateMessage(String personName, String message) throws MessageServiceException {
    Event event = restTemplate
        .postForEntity(slackApiEndpoints.getUserListApi(), null, Event.class, properties.getToken().getBot()).getBody();
    if (event != null) {
      for (User person : event.getMembers()) {
        if (personName.equals(person.getName())) {
          try {
            restTemplate.postForEntity(slackApiEndpoints.getChatPostMessageApi(), null, String.class,
                properties.getToken().getBot(), person.getId(), message);
          } catch (RestClientException e) {
            LOGGER.error("Unable posting to given person Id: {}", e);
            throw new MessageServiceException(e.getMessage(), e);
          }
        }
      }
    }
  }
}
