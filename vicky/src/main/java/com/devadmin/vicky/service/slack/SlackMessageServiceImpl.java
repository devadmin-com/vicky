/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.service.slack;

import com.devadmin.slack.bot.SlackApiEndpoints;
import com.devadmin.slack.bot.models.Event;
import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.slack.bot.models.User;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.controller.slack.config.SlackProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
     * @see MessageService#sendChannelMessage(String, String)
     */
    @Override
    public void sendChannelMessage(String channelName, String message) throws MessageServiceException {
        RichMessage richMessage = new RichMessage(message);
        // getting all channel names from properties,
        // checking if have the channel which should receive the message
        // if yes then sending the message to that channel
        Map<String, String> incomingWebhooks = properties.getWebhook().getIncoming();
        if (incomingWebhooks.containsKey(channelName)) {
            String incomingWebhookUrl = incomingWebhooks.get(channelName);
            try {
                restTemplate.postForEntity(incomingWebhookUrl, richMessage, String.class);
            } catch (RestClientException e) {
                LOGGER.error("Unable to post IncomingWebhookURL given from Slack Properties: {}", e);
                throw new MessageServiceException(e.getMessage(), e);
            }
        }

    }

    /**
     * @see MessageService#sendPrivateMessage(String, String)
     */
    @Override
    public void sendPrivateMessage(String personName, String message) throws MessageServiceException {
        // getting the event with HTTP POST request, then getting list of all members in slack
        Event event = restTemplate
                .postForEntity(slackApiEndpoints.getUserListApi(), null, Event.class, properties.getToken().getBot()).getBody();
        // looping through all members and getting the one whom we need to send PM
        for (User person : event.getMembers()) {
            if (personName.equals(person.getName())) {
                try {
                    restTemplate.postForEntity(slackApiEndpoints.getChatPostMessageApi(), null, String.class,
                            properties.getToken().getBot(), person.getId(), message);
                } catch (RestClientException e) {
                    LOGGER.error("Unable to post to given person Id: {}", e);
                    throw new MessageServiceException(e.getMessage(), e);
                }
            }
        }
    }
}
