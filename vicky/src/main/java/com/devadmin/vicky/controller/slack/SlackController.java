package com.devadmin.vicky.controller.slack;

import com.devadmin.slack.bot.AbstractBot;
import com.devadmin.slack.common.SlackBot;
import com.devadmin.vicky.controller.slack.config.SlackProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This controller is responsible for interactions with Slack Bot ex. sending DM to Bot
 * this is the bean we use to have a socket connection with SLACK
 */
@SlackBot
public class SlackController extends AbstractBot {

  /** Slack properties from application.yml file. */
  private final SlackProperties properties;

  /** @param properties from application.yml. */
  @Autowired
  public SlackController(SlackProperties properties) {
    this.properties = properties;
  }

  /**
   * You can get your slack bot token next <a href="https://my.slack.com/services/new/bot">creating
   * a new bot</a>.
   *
   * @return Slack bot token from application.yml file
   */
  @Override
  public String getSlackToken() {
    return properties.getToken().getBot();
  }

  /** @return slack bot vicky */
  @Override
  public AbstractBot getSlackBot() {
    return this;
  }
}
