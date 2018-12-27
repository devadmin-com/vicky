package com.devadmin.vicky;

/**
 * A generic messaging service. Allows writing string messages to a named channel or privately to a person.
 */
public interface MessageService {

  /**
   * Sends a message to the specified channel
   *
   * @param channelName the name (identifier) of the channel to send the message to. channel name is the same as jira
   * project name
   * @param message the message to send
   */
  void sendChannelMessage(String channelName, String message) throws MessageServiceException;

  /**
   * Sends a direct private message to a person
   *
   * @param personName the name (identifier) of the private channel to send the message to
   * @param message the message to send
   */
  void sendPrivateMessage(String personName, String message) throws MessageServiceException;
}
