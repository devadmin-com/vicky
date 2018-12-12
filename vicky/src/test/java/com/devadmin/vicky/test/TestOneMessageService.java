package com.devadmin.vicky.test;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;

/**
 * An very simple implementation of MessageService for testing.
 *
 * Just stores the latest message sent
 */
public class TestOneMessageService implements MessageService {
    String lastMessage; // the last message sent through us
    String lastChannelName; // the id of the last channel sent to
    String lastPersonId; // the id of the last person sent to

    boolean privateMessaged = false;
    boolean channelMessaged = false;

    @Override
    public void sendChannelMessage(String message, String channelName) throws MessageServiceException {
        lastMessage = message;
        lastChannelName =  channelName;
        channelMessaged = true;
        System.out.println("sendChannelMessage("+message+","+channelName+")");
    }

    @Override
    public void sendPrivateMessage(String message, String personId) throws MessageServiceException {
        lastMessage = message;
        lastPersonId = personId;
        privateMessaged = true;
        System.out.println("sendPrivateMessage("+message+","+personId+")");

    }
}
