package com.devadmin.vicky.service;

/**
 * This class contain methods for sending message to different slack destinations
 */
public interface SlackService {

  void sendChannelMessage(String slackMessage, String projectName, String[] labels);

  void sendPrivateMessage(String slackMessage, String username);
}
