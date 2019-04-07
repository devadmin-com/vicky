/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.service.slack;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.controller.slack.Event;
import com.devadmin.vicky.controller.slack.SlackApiEndpoints;
import com.devadmin.vicky.controller.slack.config.SlackProperties;
import me.ramswaroop.jbot.core.slack.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

/** Implements a {@link MessageService} with slack as the underlying transport */
@Service
public class SlackMessageServiceImpl implements MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlackMessageServiceImpl.class);
  private static String MESSAGE_TO_USER = "";

  private final SlackProperties properties;

  private final SlackApiEndpoints slackApiEndpoints;

  private final RestTemplate restTemplate;

  public SlackMessageServiceImpl(
      SlackProperties properties, RestTemplate restTemplate, SlackApiEndpoints slackApiEndpoints) {
    this.properties = properties;
    this.restTemplate = restTemplate;
    this.slackApiEndpoints = slackApiEndpoints;
  }

  /** @see MessageService#sendChannelMessage(String, String) */
  @Override
  public void sendChannelMessage(String channelName, String message)
      throws MessageServiceException {

    try {
      restTemplate.postForEntity(
          slackApiEndpoints.getChatPostMessageApi(),
          null,
          String.class,
          properties.getToken().getBot(),
          channelName,
          message);
    } catch (RestClientException e) {
      LOGGER.error("Unable to post to given channel: {}", e);
      throw new MessageServiceException(e.getMessage(), e);
    }
  }

  /** @see MessageService#sendPrivateMessage(String, String) */
  @Override
  public void sendPrivateMessage(String personName, String message) throws MessageServiceException {
    // getting the event with HTTP POST request, then getting list of all members in slack
    // let's keep it this way for now (would be better to find a way to send DM by person name)
    // TODO handle pagination problem (we can have next_cursor)
    Event event =
        restTemplate
            .postForEntity(
                slackApiEndpoints.getUserListApi(),
                null,
                Event.class,
                properties.getToken().getBot())
            .getBody();
    // looping through all members and getting the one whom we need to send PM
    MESSAGE_TO_USER = message;
    if (event != null) {
      User[] slackUsers = event.getMembers();
      Optional<User> user =
          Arrays.stream(slackUsers).filter(p -> p.getName().equals(personName)).findFirst();
      user.ifPresent(this::sendMessageToUser);
    }
  }

  private void sendMessageToUser(User user) {
    try {
      restTemplate.postForEntity(
          slackApiEndpoints.getChatPostMessageApi(),
          null,
          String.class,
          properties.getToken().getBot(),
          user.getId(),
          MESSAGE_TO_USER);
    } catch (RestClientException e) {
      LOGGER.error("Unable to post to given person Id: {}", e);
      // throw new MessageServiceException(e.getMessage(), e);
    } finally {
      MESSAGE_TO_USER = "";
    }
  }
}
