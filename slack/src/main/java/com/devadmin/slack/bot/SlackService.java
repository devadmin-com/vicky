package com.devadmin.slack.bot;

import com.devadmin.slack.bot.models.Channel;
import com.devadmin.slack.bot.models.Event;
import com.devadmin.slack.bot.models.RTM;
import com.devadmin.slack.bot.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private String webSocketUrl;

    private User currentUser;

    private List<String> imChannelIds = new ArrayList<>();

    private Channel[] channels;

    private final SlackApiEndpoints slackApiEndpoints;

    private final RestTemplate restTemplate;

    @Autowired
    public SlackService(SlackApiEndpoints slackApiEndpoints, RestTemplate restTemplate) {
        this.slackApiEndpoints = slackApiEndpoints;
        this.restTemplate = restTemplate;
    }

    /**
     * Start a RTM connection. Fetch the web socket url to connect to, current user details
     * and list of channel ids where the current user has had conversation.
     *
     * @param slackToken slack token which you get from slack for the integration you create
     */
    public void connectRTM(String slackToken) {
        RTM rtm = restTemplate.getForEntity(slackApiEndpoints.getRtmConnectApi(), RTM.class, slackToken).getBody();
        currentUser = rtm.getSelf();
        webSocketUrl = rtm.getUrl();
        getImChannels(slackToken, 200,"");
    }

    /**
     * Fetch all im channels to determine direct message to the bot.
     *
     * @param slackToken slack token which you get from slack for the integration you create
     * @param limit number of channels to fetch in one call
     * @param nextCursor cursor for the next call
     */
    private void getImChannels(String slackToken, int limit, String nextCursor) {
        try {
            Event event = restTemplate.getForEntity(slackApiEndpoints.getImListApi(), Event.class,
                    slackToken, limit, nextCursor).getBody();
            imChannelIds.addAll(Arrays.stream(event.getIms()).map(Channel::getId).collect(Collectors.toList()));
            if (event.getResponseMetadata() != null &&
                    !StringUtils.isEmpty(event.getResponseMetadata().getNextCursor())) {
                Thread.sleep(5000L); // sleep because its a tier 2 api which allows only 20 calls per minute
                getImChannels(slackToken, limit, event.getResponseMetadata().getNextCursor());
            }
        } catch (Exception e) {
            logger.error("Error fetching im channels for the bot: ", e);
        }
    }

    /**
     * @return user representing the bot.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return list of channel ids where the current user has had conversation.
     */
    public List<String> getImChannelIds() {
        return imChannelIds;
    }

    public void setImChannelIds(List<String> imChannelIds) {
        this.imChannelIds = imChannelIds;
    }

    public boolean addImChannelId(String channelId) {
        return imChannelIds.add(channelId);
    }

    /**
     * @return web socket url to connect to.
     */
    public String getWebSocketUrl() {
        return webSocketUrl;
    }

    public void setWebSocketUrl(String webSocketUrl) {
        this.webSocketUrl = webSocketUrl;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }
}

