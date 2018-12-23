package com.devadmin.vicky.test;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of MessageService for testing.
 *
 * Buffers everything which has been sent to it.
 *
 * Just stores the latest message sent
 */
public class TestMessageService implements MessageService {
    private String lastMessage; // the last message sent through us
    private String lastChannelName; // the id of the last channel sent to
    private String personId; // the id of the last person sent to
    private int channelMessageCount = 0;


    private List<Message> privateMsg = new ArrayList<Message>();
    private List<Message> channelMsg = new ArrayList<Message>();

    @Override
    public void sendChannelMessage(String message, String channelName) throws MessageServiceException {
        this.channelMessageCount++;
        setLastMessage(message);
        channelMsg.add(new Message(channelName, message));
    }

    @Override
    public void sendPrivateMessage(String message, String personId) throws MessageServiceException {
        privateMsg.add(new Message(personId, message));
    }

    /**
     * @return true if any private messages were sent
     */
    boolean wasPMed(){
        return privateMsg.size() > 0;
    };

    /**
     * @return true if any channel messages were sent
     */
    boolean wasChannelMsged() {
        return channelMsg.size() > 0;
    };


    /**
     * @return true if there were any messages sent to this person.
     */
    boolean wasPMed(String id){
       boolean was = false;

        for (Message message : privateMsg ){
           if ( id!=null && id.equals(message.getTo()) ){
               was = true;
           }
       }
       return was;
    }

    boolean wasChannelMsged(String id) {
        boolean was = false;

        for (Message message : channelMsg ){
            if ( id!=null && id.equals(message.getTo()) ){
                was = true;
            }
        }
        return was;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastChannelName() {
        return lastChannelName;
    }

    public void setLastChannelName(String lastChannelName) {
        this.lastChannelName = lastChannelName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<Message> getPrivateMsg() {
        return privateMsg;
    }

    public void setPrivateMsg(List<Message> privateMsg) {
        this.privateMsg = privateMsg;
    }

    public List<Message> getChannelMsg() {
        return channelMsg;
    }

    public void setChannelMsg(List<Message> channelMsg) {
        this.channelMsg = channelMsg;
    }

    public int getChannelMessageCount() {
        return channelMessageCount;
    }

    public void setChannelMessageCount(int channelMessageCount) {
        this.channelMessageCount = channelMessageCount;
    }
}

/**
 * Models one message sent (privately or to a channel)
 */
class Message {

    private final String to;
    private final String message;

    Message(String to, String message) {
        this.to = to;
        this.message = message;
    }

    String getTo() {
        return to;
    }

    String getMessage() {
        return message;
    }
}