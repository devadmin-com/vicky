package com.devadmin.vicky;


/**
 * A generic messaging service. Allows writing string messages to a named channel or privately to a person.
 *
 */
public interface MessageService {

  /**
   * Sends a message to the specified channel
   *
   * @param projectName the name (identifier) of the channel to send the message to
   * @param message the message to send
   */
  public void sendChannelMessage(String message, String projectName) throws MessageServiceException;

  /**
   * Sends a direct private message to a person
   *
   * @param personId the name (identifier) of the channel to send the message to
   * @param message the message to send
   */
  public void sendPrivateMessage(String message, String personId) throws MessageServiceException;
}
