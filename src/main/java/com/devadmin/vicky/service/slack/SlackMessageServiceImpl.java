/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.service.slack;

import com.devadmin.vicky.config.slack.SlackApiEndpoints;
import com.devadmin.vicky.config.slack.SlackProperties;
import com.devadmin.vicky.model.slack.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

/**
 * Implements a {@link MessageService} with slack as the underlying transport
 */
@Service
@Slf4j
public class SlackMessageServiceImpl implements MessageService {

    private final SlackProperties properties;
    private final SlackApiEndpoints slackApiEndpoints;
    private final RestTemplate restTemplate;
    @Value("${debug.message-service.additional-information:}")
    private String additionalMessageInformation;

    public SlackMessageServiceImpl(SlackProperties properties, SlackApiEndpoints slackApiEndpoints, RestTemplate restTemplate) {
        this.properties = properties;
        this.slackApiEndpoints = slackApiEndpoints;
        this.restTemplate = restTemplate;
    }

    /**
     * @see MessageService#sendChannelMessage(String, String)
     */
    @Override
    public void sendChannelMessage(String channelName, String message) {

        log.info("SlackMessageService sendChannelMessage() method");
        log.info("Trying to send channel message to {}", channelName);
        final ResponseEntity<String> response = restTemplate.postForEntity(
                slackApiEndpoints.getChatPostMessageApi(),
                null,
                String.class,
                properties.getToken().getBot(),
                channelName,
                message + " " + additionalMessageInformation);
        log.info("Response from slack message service is {}", response.getBody());
    }

    /**
     * @see MessageService#sendPrivateMessage(String, String)
     */
    @Override
    public void sendPrivateMessage(String personEmail, String message) {
        // getting the event with HTTP POST request, then getting list of all members in slack
        // let's keep it this way for now (would be better to find a way to send DM by person name)
        // TODO handle pagination problem (we can have next_cursor)

        log.info("slack.send to {} message {}", personEmail, message);

        Event event =
                restTemplate
                        .postForEntity(
                                slackApiEndpoints.getUserListApi(),
                                null,
                                Event.class,
                                properties.getToken().getBot())
                        .getBody();

        // looping through all members and getting the one whom we need to send PM
        Stream.of(event.getMembers())
                .filter(member ->
                        member.getProfile().getEmail() != null && member.getProfile().getEmail().equals(personEmail))
                .findFirst()
                .ifPresent(member -> {
                    log.info("Trying to send private message to {}", personEmail);
                    restTemplate.postForEntity(
                            slackApiEndpoints.getChatPostMessageApi(),
                            null,
                            String.class,
                            properties.getToken().getBot(),
                            member.getId(),
                            message + " " + additionalMessageInformation);
                });
    }
}
