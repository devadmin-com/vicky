
package com.devadmin.vicky.listener;

import com.devadmin.slack.bot.models.RichMessage;
import com.devadmin.vicky.*;
import com.devadmin.vicky.controller.slack.SlackProperties;
import com.devadmin.vicky.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * On issue create or resolve send update to project's channel (if one exists)
 *
 * If no channel exists for project nothing is done.
 *
 * Implements Story: TL-99 issue create, resolve -> project channel
 */
@Component
public class ProjectTaskListener extends TaskToMessageListener {

  @Autowired
  public ProjectTaskListener(MessageService messageService) {
    super(messageService);
  }

  @EventListener(
          condition = " 'issue_created'.equals( #event.eventModel.type )"
  )
  public void onApplicationEvent(TaskEvent event) {

    System.out.println(event.toString());

    String message = "This message was sent by supercool Vicky 2.0 from ProjectTaskListener";
    System.out.println("\n event type is =>" + event.getEventModel().getType());

    // channel name is the same as jira project name
    String channelName = event.getTask().getProject();


    try {
      messageService.sendChannelMessage(message, channelName);

    } catch (MessageServiceException e) {
      LOGGER.error(e.getMessage());

    }

  }

}