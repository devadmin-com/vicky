package com.devadmin.vicky.test;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import org.assertj.core.groups.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An implementation of MessageService for testing.
 *
 * Buffers everything which has been sent to it.
 *
 * Just stores the latest message sent
 */
public class TestMessageService implements MessageService {
    String lastMessage; // the last message sent through us
    String lastChannelName; // the id of the last channel sent to
    String personId; // the id of the last person sent to


    private List<Message> privateMsg = new ArrayList<Message>();
    private List<Message> channelMsg = new ArrayList<Message>();

    @Override
    public void sendChannelMessage(String message, String channelName) throws MessageServiceException {
        channelMsg.add(new Message())
    }

    @Override
    public void sendPrivateMessage(String message, String personId) throws MessageServiceException {
        // @todo
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
        return privateMsg.size() > 0;
    };


    /**
     * @return true if there were any messages sent to this person.
     */
    boolean wasPMed(String id){
       // @todo see if can find id
    };

    boolean wasChannelMsged(String id) {
        // @todo see if can find id
    };
}

/**
 * Models one message sent (privately or to a channel)
 */
public class Message {

    private final String to;
    private final String message;

    public Message(String to, String message) {
        this.left = left;
        this.right = right;
    }


    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message)) return false;
        Message pairo = (Message) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}