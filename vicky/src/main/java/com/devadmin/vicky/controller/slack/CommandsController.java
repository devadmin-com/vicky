package com.devadmin.vicky.controller.slack;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.controller.slack.model.UserModel;
import com.devadmin.vicky.service.slack.*;
import com.devadmin.vicky.controller.slack.config.SlackProperties;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.models.Bot;
import me.ramswaroop.jbot.core.slack.models.Profile;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    MessageService messageService;

    @PostMapping("/bye")
    public void sayByeToEveryone(HttpServletRequest request){
        UserModel userModel = new UserModel();
        userModel.paramsToUserModel(userModel.getParameters(request));
        try {
            messageService.sendChannelMessage("vicky", "test bye message from " + userModel.getUserName() + ". " + userModel.getText());
        } catch (MessageServiceException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/hello")
    public void setAvailableStatus(@RequestParam(name = "token") String token) {
        Profile profile = new Profile();

        profile.setStatusEmoji(statusAvailableEmoji);
        profile.setStatusText(statusAvailableText);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {

            HttpPost request = new HttpPost("https://slack.com/api/users.profile.set");
            StringEntity params = new StringEntity("profile={\"status_text\": "+ profile.getStatusText() +", \"status_emoji\": "+ profile.getStatusEmoji() +" }");

            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            //handle response here...

        }catch (Exception ex) {

            //handle exception here

        } finally {
            //Deprecated
            httpClient.getConnectionManager().shutdown();
        }
        // https://slack.com/api/users.profile.set body = {
        //   "profile": {
        //        "status_text": "riding a train",
        //        "status_emoji": ":mountain_railway:",
        //        "status_expiration": 1532627506
        //    }
        // }

    }
}
