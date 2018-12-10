package com.devadmin.vicky.service.impl;

import com.devadmin.vicky.service.MessageService;
import com.devadmin.vicky.service.message.SlackMessageEntity;
import com.devadmin.vicky.service.message.SlackMessageType;
import org.springframework.stereotype.Service;

/**
 * {@link MessageService}
 */
@Service
public class MessageServiceImpl implements MessageService {

  public String createMessage(SlackMessageEntity messageEntity, String messageType) {
    return SlackMessageType.valueOf(messageType).createMessage(messageEntity);
  }
}
