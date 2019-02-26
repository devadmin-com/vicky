package com.devadmin.vicky.controller.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *  This api is used for getting some urls for channel and private messaging
 * */
@Service
public class SlackApiEndpoints {

  /** Endpoint for Slack Api */
  @Value("${slack.api-url}")
  private String slackApi;

  /** @return endpoint for RTM.connect() */
  public String getRtmConnectApi() {
    return slackApi + "/rtm.connect?token={token}";
  }

  public String getImListApi() {
    return slackApi + "/im.list?token={token}&limit={limit}&next_cursor={cursor}";
  }

  public String getChatPostMessageApi() {
    return slackApi + "/chat.postMessage?token={token}&channel={channel}&text={text}";
  }

  public String getUserListApi() {
    return slackApi + "/users.list?token={token}";
  }
}
