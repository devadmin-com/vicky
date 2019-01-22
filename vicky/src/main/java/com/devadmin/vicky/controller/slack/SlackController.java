package com.devadmin.vicky.controller.slack;

import com.devadmin.slack.bot.AbstractBot;
import com.devadmin.slack.bot.models.Event;
import com.devadmin.slack.common.Controller;
import com.devadmin.slack.common.EventType;
import com.devadmin.slack.common.SlackBot;
import java.util.regex.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

/**
 * Vicky slack bot.
 * TODO: javadoc, what is this class for?
 */
@SlackBot
public class SlackController extends AbstractBot {

  private static final Logger logger = LoggerFactory.getLogger(SlackController.class);

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

  /**
   * You can get your slack bot token next
   * <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
   *
   * @return Slack bot token from application.yml file
   */
  @Override
  public String getSlackToken() {
    return properties.getToken().getBot();
  }

  /**
   * @return slack bot vicky
   */
  @Override
  public AbstractBot getSlackBot() {
    return this;
  }

  public void sendDirectMessageToBot(String message, String username) {
      replyToSlackBot(message, username);
  }

  /**
   * Invoked when the bot receives a direct mention (@botname: message) or a direct message. NOTE: SlackProperties
   * doesn't have any direct way to determine these type of events.
   *
   * @param session websocket session
   * @param event the event which will be happened when the bot receives a direct mention (@botname: message) or a
   * direct message.
   */
  @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
  public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName());
  }

  /**
   * Invoked when bot receives an event of type message with text satisfying the pattern {@code ([a-z ]{2})(\d+)([a-z
   * ]{2})}. For example, messages like "ab12xy" or "ab2bc" etc will invoke this method.
   */
  @Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
  public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
    reply(
        session,
        event,
        "First group: "
            + matcher.group(0)
            + "\n"
            + "Second group: "
            + matcher.group(1)
            + "\n"
            + "Third group: "
            + matcher.group(2)
            + "\n"
            + "Fourth group: "
            + matcher.group(3));
  }

  /**
   * Invoked when an item is pinned in the channel.
   */
  @Controller(events = EventType.PIN_ADDED)
  public void onPinAdded(WebSocketSession session, Event event) {
    reply(
        session, event, "Thanks for the pin! You can find all pinned items under channel details.");
  }

  /**
   * Invoked when bot receives an event of type file shared. NOTE: You can't reply to this event as slack doesn't send a
   * channel id for this event type. You can learn more about <a href="https://api.slack.com/events/file_shared">file_shared</a>
   * event from SlackProperties's Api documentation.
   */
  @Controller(events = EventType.FILE_SHARED)
  public void onFileShared(WebSocketSession session, Event event) {
    logger.info("File shared: {}", event);
  }

  /**
   * Conversation feature of JBot. This method is the starting point of the conversation (as it calls {@link
   * AbstractBot#startConversation(Event, String)} within it. You can chain methods which will be invoked one after the
   * other leading to a conversation. You can chain methods with {@link Controller#next()} by specifying the method name
   * to chain with.
   */
  @Controller(pattern = "(setup meeting)", next = "confirmTiming")
  public void setupMeeting(WebSocketSession session, Event event) {
    startConversation(event, "confirmTiming"); // start conversation
    reply(session, event, "Cool! At what time (ex. 15:30) do you want me to set up the meeting?");
  }

  /**
   * This method will be invoked after {@link SlackController#setupMeeting(WebSocketSession, Event)}.
   */
  @Controller(next = "askTimeForMeeting")
  public void confirmTiming(WebSocketSession session, Event event) {
    reply(
        session,
        event,
        "Your meeting is set at " + event.getText() + ". Would you like to repeat it tomorrow?");
    nextConversation(event); // jump to next question in conversation
  }

  /**
   * This method will be invoked after {@link SlackController#confirmTiming(WebSocketSession, Event)}.
   */
  @Controller(next = "askWhetherToRepeat")
  public void askTimeForMeeting(WebSocketSession session, Event event) {
    if (event.getText().contains("yes")) {
      reply(session, event, "Okay. Would you like me to set a reminder for you?");
      nextConversation(event); // jump to next question in conversation
    } else {
      reply(
          session, event, "No problem. You can always schedule one with 'setup meeting' command.");
      stopConversation(event); // stop conversation only if user says no
    }
  }

  /**
   * This method will be invoked after {@link SlackController#askTimeForMeeting(WebSocketSession, Event)}.
   */
  @Controller
  public void askWhetherToRepeat(WebSocketSession session, Event event) {
    if (event.getText().contains("yes")) {
      reply(session, event, "Great! I will remind you tomorrow before the meeting.");
    } else {
      reply(session, event, "Okay, don't forget to attend the meeting tomorrow :)");
    }
    stopConversation(event); // stop conversation
  }
}
