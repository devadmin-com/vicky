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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ramswaroop.jbot.core.slack.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Implements a {@link MessageService} with slack as the underlying transport
 */
@Service
@Slf4j
@AllArgsConstructor
public class SlackMessageServiceImpl implements MessageService {

    private final SlackProperties properties;

    private final SlackApiEndpoints slackApiEndpoints;

    private final RestTemplate restTemplate;

    /**
     * @see MessageService#sendChannelMessage(String, String)
     */
    @Override
    public void sendChannelMessage(String channelName, String message)
            throws MessageServiceException {

        log.info("SlackMessageService sendChannelMessage() method");
        try {
            log.info("Trying to send channel message to {}", channelName);
            final ResponseEntity<String> response = restTemplate.postForEntity(
                    slackApiEndpoints.getChatPostMessageApi(),
                    null,
                    String.class,
                    properties.getToken().getBot(),
                    channelName,
                    message);
            log.info("Response from slack message service is {}", response.getBody());
        } catch (RestClientException e) {
            log.error("Unable to post to given channel: {}", e);
            throw new MessageServiceException(e.getMessage(), e);
        }
    }

    /**
     * @see MessageService#sendPrivateMessage(String, String)
     */
    @Override
    public void sendPrivateMessage(String personName, String message) throws MessageServiceException {
        // getting the event with HTTP POST request, then getting list of all members in slack
        // let's keep it this way for now (would be better to find a way to send DM by person name)
        // TODO handle pagination problem (we can have next_cursor)

        log.info("SlackMessageService sendPrivateMessage() method");

        Event event =
                restTemplate
                        .postForEntity(
                                slackApiEndpoints.getUserListApi(),
                                null,
                                Event.class,
                                properties.getToken().getBot())
                        .getBody();
        // looping through all members and getting the one whom we need to send PM
        if (event.getMembers() != null) {
            for (User person : event.getMembers()) {
                if (personName != null && person != null && personName.equals(person.getName())) {
                    try {
                        log.info("Trying to send private message to {}", personName);
                        restTemplate.postForEntity(
                                slackApiEndpoints.getChatPostMessageApi(),
                                null,
                                String.class,
                                properties.getToken().getBot(),
                                person.getId(),
                                message);
                    } catch (RestClientException e) {
                        log.error("Unable to post to given person Id: {}", e);
                        throw new MessageServiceException(e.getMessage(), e);
                    }
                }
            }
        }
    }
}
