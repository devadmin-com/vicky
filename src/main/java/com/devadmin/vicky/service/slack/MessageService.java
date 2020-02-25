/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.service.slack;

import com.devadmin.vicky.MessageServiceException;

/**
 * A generic messaging service. Allows writing string messages to a named channel or privately to a
 * person.
 */
public interface MessageService {

    /**
     * Sends a message to the specified channel
     *
     * @param channelName the name (identifier) of the channel to send the message to.
     * @param message     the message to send
     */
    void sendChannelMessage(String channelName, String message);

    /**
     * Sends a direct private message to a person
     *
     * @param personEmail the name (identifier) of the person to send the message to
     * @param message     the message to send
     */
    void sendPrivateMessage(String personEmail, String message);
}
