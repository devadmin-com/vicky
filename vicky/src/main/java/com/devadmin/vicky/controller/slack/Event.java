package com.devadmin.vicky.controller.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.ramswaroop.jbot.core.slack.models.Bot;
import me.ramswaroop.jbot.core.slack.models.Channel;
import me.ramswaroop.jbot.core.slack.models.User;

/**
 * Object which helps to send private channel message
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private int id;

    private String type;

    @JsonProperty("channel_id")
    private String channelId;

    @JsonProperty("user_id")
    private String userId;

    private String text;

    @JsonProperty("pinned_to")
    private String[] pinnedTo;

    @JsonProperty("members")
    private User[] members;

    private Channel channel;

    private Channel[] ims;

    private Bot bot;

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setPinnedTo(String[] pinnedTo) {
        this.pinnedTo = pinnedTo;
    }

    public User[] getMembers() {
        return members;
    }

    public void setMembers(User[] members) {
        this.members = members;
    }

    public Channel getChannel() {
        return channel;
    }

    public Channel[] getIms() {
        return ims;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

