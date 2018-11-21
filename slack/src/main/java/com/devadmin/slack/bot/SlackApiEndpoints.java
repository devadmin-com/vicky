package com.devadmin.slack.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackApiEndpoints {

    /**
     * Endpoint for Slack Api
     */
    @Value("${vicky.slack.api-url}")
    private String slackApi;

    /**
     * @return endpoint for RTM.connect()
     */
    public String getRtmConnectApi() {
        return slackApi + "/rtm.connect?token={token}";
    }

    public String getImListApi() {
        return slackApi + "/im.list?token={token}&limit={limit}&next_cursor={cursor}";
    }

    public String getChatPostMessageApi() {
        return slackApi + "/chat.postMessage?token={token}&channel={channel}&text={text}";
    }

    public String lookupUserByEmailApi() {
        return slackApi + "/users.lookupByEmail?token={token}&email={email}";
    }

    public String getConversationsListApi() {
        return slackApi + "/conversations.list?token={token}&type={type}";
    }

    public String getUserListApi() {
        return slackApi + "/users.list?token={token}";
    }

    public String getImOpenApi() {
        return slackApi + "/im.open?token={token}&user={user}";
    }

}
