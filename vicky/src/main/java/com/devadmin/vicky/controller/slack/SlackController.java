package com.devadmin.vicky.controller.slack;

import com.devadmin.vicky.controller.slack.config.SlackProperties;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.models.Bot;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This controller is responsible for interactions with Slack Bot ex. sending DM to Bot
 * this is the bean we use to have a socket connection with SLACK
 */
@JBot
public class SlackController extends Bot {

    /**
     * Slack properties from application.yml file.
     */
    private final SlackProperties properties;

    /**
     * @param properties from application.yml.
     */
    @Autowired
    public SlackController(SlackProperties properties) {
        this.properties = properties;
    }

}
