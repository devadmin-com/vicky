package com.devadmin.vicky.service;

import com.devadmin.vicky.service.message.SlackMessageEntity;

/**
 * This class contain m
 */
public interface MessageService {

  String createMessage(SlackMessageEntity messageEntity, String messageType);
}
