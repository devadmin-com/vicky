package com.devadmin.vicky.controller.slack;

import com.devadmin.vicky.controller.slack.config.SlackProperties;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.models.Bot;
import me.ramswaroop.jbot.core.slack.models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * This controller is responsible for interactions with Slack Bot ex. sending DM to Bot
 * this is the bean we use to have a socket connection with SLACK
 */
@JBot
@RestController
@RequestMapping("/commands")
public class CommandsController extends Bot {

    @Value("${statuses.available.emoji}")
    String statusAvailableEmoji;

    @Value("${statuses.available.text}")
    String statusAvailableText;

    @Value("${statuses.available.expiration}")
    Integer statusAvailableExpiration;

    /**
     * Slack properties from application.yml file.
     */
    private final SlackProperties properties;

    /**
     * @param properties from application.yml.
     */
    @Autowired
    public CommandsController(SlackProperties properties) {
        this.properties = properties;
    }

    @PostMapping("/hello")
    public Object setAvailableStatus(@RequestParam(name = "token") String token) {
        Profile profile = new Profile();

        profile.setStatusEmoji(statusAvailableEmoji);
        profile.setStatusText(statusAvailableText);

        // https://slack.com/api/users.profile.set body = {
        //   "profile": {
        //        "status_text": "riding a train",
        //        "status_emoji": ":mountain_railway:",
        //        "status_expiration": 1532627506
        //    }
        // }
    }
}
