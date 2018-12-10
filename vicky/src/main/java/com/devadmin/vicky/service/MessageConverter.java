package com.devadmin.vicky.service;

import com.devadmin.vicky.controller.model.jira.JiraEventModel;
import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.message.SlackMessageEntity;

/**
 * This class contain method which convert jira model to slack message
 */
public interface MessageConverter {

  SlackMessageEntity convert(JiraEventModel jiraEventModel) throws VickyException;

}
