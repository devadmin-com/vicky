package com.devadmin.slack.common;

import com.devadmin.slack.bot.AbstractBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class BotWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(BotWebSocketHandler.class);

    private AbstractBot abstractBot;

    public BotWebSocketHandler(AbstractBot abstractBot) {
        this.abstractBot = abstractBot;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        abstractBot.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        abstractBot.handleTextMessage(session, message);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        logger.error("Binary messages are not supported in Slack RTM API");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        abstractBot.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        abstractBot.handleTransportError(session, exception);
    }
}