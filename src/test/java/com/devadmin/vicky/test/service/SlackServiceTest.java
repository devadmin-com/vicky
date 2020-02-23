package com.devadmin.vicky.test.service;

import com.devadmin.vicky.config.slack.SlackApiEndpoints;
import com.devadmin.vicky.config.slack.SlackProperties;
import com.devadmin.vicky.model.slack.Event;
import com.devadmin.vicky.service.slack.MessageService;
import com.devadmin.vicky.service.slack.SlackMessageServiceImpl;
import me.ramswaroop.jbot.core.slack.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

/**
 * Test {@link SlackMessageServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class SlackServiceTest {

    /**
     * Email to test.
     */
    private static final String TEST_EMAIL = "Lollipop@devadmin.com";

    /**
     * User id to test.
     */
    private static final String TEST_ID = "1";

    /**
     * MessageService to test.
     */
    private MessageService messageService;

    /**
     * Slack properties with fake data.
     */
    private SlackProperties properties;

    /**
     * Mocked rest template.
     */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Mocked slack endpoints.
     */
    @Mock
    private SlackApiEndpoints endpoints;

    /**
     * Init objects.
     */
    @Before
    public void init() {
        this.prepareProperties();
        this.prepareEndpoints();
        this.prepareRestTemplate();

        this.messageService = new SlackMessageServiceImpl(
                this.properties,
                this.endpoints,
                this.restTemplate,
                ""
        );
    }

    /**
     * Test that service sends channel message using rest template.
     */
    @Test
    public void testChannelMessage() {
        this.messageService.sendChannelMessage("dev", "Good morning");
        verify(this.restTemplate, atLeastOnce())
                .postForEntity(
                        eq(this.endpoints.getChatPostMessageApi()),
                        isNull(),
                        eq(String.class),
                        eq(this.properties.getToken().getBot()),
                        any(),
                        any()
                );
    }

    /**
     * Test private message.
     * Firstly gets list of users using rest template
     * Then sends message to user with TEST_EMAIL using rest template
     */
    @Test
    public void testPrivateMessage() {
        this.messageService.sendPrivateMessage(TEST_EMAIL, "Good morning");
        verify(this.restTemplate, atLeastOnce())
                .postForEntity(
                        eq(this.endpoints.getUserListApi()),
                        isNull(),
                        eq(Event.class),
                        eq(this.properties.getToken().getBot())
                );
        verify(this.restTemplate, atLeastOnce())
                .postForEntity(
                        eq(this.endpoints.getChatPostMessageApi()),
                        isNull(),
                        eq(String.class),
                        eq(this.properties.getToken().getBot()),
                        eq(TEST_ID),
                        any()
                );
    }

    /**
     * Prepare mocked rest template.
     */
    private void prepareRestTemplate() {
        final Event event = this.prepareEvent();
        //send Channel message
        when(
                this.restTemplate.postForEntity(
                        eq(this.endpoints.getChatPostMessageApi()),
                        isNull(),
                        eq(String.class),
                        eq(this.properties.getToken().getBot()),
                        any(),
                        any()
                )
        ).thenReturn(ResponseEntity.ok("Success"));
        //send private message to get users
        when(
                this.restTemplate
                        .postForEntity(
                                eq(this.endpoints.getUserListApi()),
                                isNull(),
                                eq(Event.class),
                                eq(this.properties.getToken().getBot())
                        )
        ).thenReturn(ResponseEntity.ok(event));
        //send private message to one user from list
        when(
                this.restTemplate
                        .postForEntity(
                                eq(this.endpoints.getChatPostMessageApi()),
                                isNull(),
                                eq(String.class),
                                eq(this.properties.getToken().getBot()),
                                eq(TEST_ID),
                                any()
                        )
        ).thenReturn(ResponseEntity.ok("Success"));
    }

    /**
     * Prepare mocked event.
     *
     * @return Mocked event
     */
    private Event prepareEvent() {
        final User user = Mockito.mock(User.class, RETURNS_DEEP_STUBS);
        when(user.getProfile().getEmail()).thenReturn(TEST_EMAIL);
        when(user.getId()).thenReturn(TEST_ID);
        final Event event = Mockito.mock(Event.class, RETURNS_DEEP_STUBS);
        when(event.getMembers()).thenReturn(new User[]{user});
        return event;
    }

    /**
     * Prepare mocked endpoints.
     */
    private void prepareEndpoints() {
        when(this.endpoints.getChatPostMessageApi()).thenReturn("www.example.com");
        when(this.endpoints.getUserListApi()).thenReturn("www.example.com");
    }

    /**
     * Prepare properties with fake data.
     */
    private void prepareProperties() {
        this.properties = new SlackProperties();
        this.properties.setApiUrl("example.com");
        final SlackProperties.Token token = new SlackProperties.Token();
        token.setBot("bot");
        this.properties.setToken(token);
    }
}
